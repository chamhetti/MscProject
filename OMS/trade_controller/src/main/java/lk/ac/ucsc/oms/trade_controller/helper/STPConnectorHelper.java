package lk.ac.ucsc.oms.trade_controller.helper;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.common_utility.api.formatters.DateFormatterUtil;
import lk.ac.ucsc.oms.common_utility.api.formatters.DecimalFormatterUtil;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.beans.OrderRoutingInfo;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.impl.facade.FIXFacade;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFacade;
import lk.ac.ucsc.oms.fixConnection.api.beans.ExchangeConnection;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.stp_connector.api.STPConnector;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPSenderException;
import lk.ac.ucsc.oms.symbol.api.SymbolFacadeFactory;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.SymbolPriceManager;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolPriceData;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.*;


public class STPConnectorHelper {
    private static Logger logger = LogManager.getLogger(STPConnectorHelper.class);
    private ExchangeManager exchangeManager;
    private FIXConnectionFacade fixConnectionFacade;

    private STPConnector stpConnector;
    private FIXFacade fixFacade;
    private AccountManager accountManager;
    private SymbolPriceManager symbolPriceManager;

    private SysLevelParaManager sysLevelParaManager;
    private SymbolManager symbolManager;

    public static final int CONST50 = 50;
    private static final int MARKET = 1;    //constant for market order type
    private static final int LIMIT = 2;     //constant for LIMIT order type

    private static final String ALL_OR_NON = "G";

    public STPConnectorHelper(ApplicationContext ctx) {
        exchangeManager = ctx.getBean(ModuleDINames.EXCHANGE_MANAGER_DI_NAME, ExchangeManager.class);
        fixConnectionFacade = ctx.getBean(ModuleDINames.FIX_CONNECTION_FACADE_DI_NAME, FIXConnectionFacade.class);
        stpConnector = ctx.getBean(ModuleDINames.STP_CONNECTOR_DI_NAME, STPConnector.class);
        fixFacade = ctx.getBean(ModuleDINames.FIX_FACADE_DI_NAME, FIXFacade.class);
        accountManager = ctx.getBean(ModuleDINames.ACCOUNT_MANAGER_DI_NAME, AccountManager.class);
        symbolPriceManager = ctx.getBean(ModuleDINames.SYMBOL_PRICE_MANAGER_DI_NAME, SymbolPriceManager.class);
        sysLevelParaManager = ctx.getBean(ModuleDINames.SYS_PARA_MANAGER_DI_NAME, SysLevelParaManager.class);
        SymbolFacadeFactory symbolFacadeFactory = ctx.getBean(ModuleDINames.SYMBOL_FACADE_FACTORY_DI_NAME, SymbolFacadeFactory.class);
        symbolManager = symbolFacadeFactory.getSymbolManager();
    }


    public Order sendQueueMessage(FixOrderInterface.MessageType messageType, Order order) {
        try {
            //check the MARKET status before proceeding
            stpConnector.isValidExchangeConnection(order);
            // if MARKET is close set the order status to oms accepted and return
            if (order.getStatus() == OrderStatus.OMS_ACCEPTED) {
                return order;
            }
                   //get the exchange bean
            Exchange exchange = exchangeManager.getExchange(order.getExchange());
            //getting the fix connection id
            String fixConnectionId = getFixConnectionID(order, exchange);
            //get the exchange specific fix tags
            Map<Integer, String> customizedFixTagMap = getCustomizedFixTags(order, fixConnectionId);
            //get the fix connection for the order routing
            FIXConnection fixConnection = fixConnectionFacade.getFixConnection(fixConnectionId);

            //get the session qualifier from the security exchange.
            String sessionQualifier = null;
            if (fixConnection != null && fixConnection.getExchangeConnections() != null && fixConnection.getExchangeConnections().containsKey(order.getExchange())) {
                sessionQualifier = fixConnection.getExchangeConnections().get(order.getExchange()).getSessionQualifier();
            }
            //if not found the session qualifier from the exchange connection get it from the fix connection
            if (sessionQualifier == null && fixConnection != null) {
                sessionQualifier = fixConnection.getSessionQualifier();
            }

            //Create the fix message
            String fixMessage = getFixMessage(order, customizedFixTagMap, messageType, fixConnectionId, sessionQualifier);
            logger.info("FIX Message created in the Front Office - {}", fixMessage);

            //send the created fix message to the exchange
            stpConnector.sendQueueMessage(messageType, order, fixMessage, sessionQualifier);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return order;
    }


    private OrderType getOrdTypeForExchange(OrderType ordType, String exchange, String marketCode) {
        switch (ordType.getCode()) {
            case "a":   // Day Market
                return OrderType.MARKET;
            case "b":    // Day Limit
                return OrderType.LIMIT;
            case "c":    // Square Off
                return getOrderTypeForSquareOffOrders(exchange, marketCode);
            default:
                return ordType;
        }
    }

    private OrderType getOrderTypeForSquareOffOrders(String exchangeCode, String marketCode) {

        try {
            OrderRoutingInfo orderRoutingInfo = exchangeManager.getExchange(exchangeCode).getSubMarkets().get(marketCode).getOrderRoutingInfo();
            if (orderRoutingInfo == null) {
                return OrderType.MARKET;
            }
            switch (orderRoutingInfo.getSquareOffOrderType()) {
                case MARKET:
                    return OrderType.MARKET;
                case LIMIT:
                    return OrderType.LIMIT;
                default:
                    return OrderType.MARKET;
            }
        } catch (ExchangeException e) {
            logger.error("Error in finding the Order Type - ", e.toString());
        }
        return null;
    }

    private String getFixConnectionID(Order order, Exchange exchange)  {
        return exchange.getExchangeCode();
    }

    private String getFixMessage(Order order, Map<Integer, String> customizedFixTags, FixOrderInterface.MessageType
            messageType, String fixConnectionID, String sessionQualifier)
            throws STPSenderException {
        String fixMessage = null;
        try {
            //get empty fix order and set the message type
            FixOrderInterface fixOrder = fixFacade.getEmptyFixOrder();
            fixOrder.setMessageType(messageType);
            //check customized fix tags contain the security id source define for the fix connection
            //then need to add that value to the security id source of the order\
            if (customizedFixTags.containsKey(FIXConstants.SECURITY_ID_SOURCE)) {
                order.setSecurityIDSource(customizedFixTags.get(FIXConstants.SECURITY_ID_SOURCE));
            }
            //populate the fix order using the order bean object
            populateFixOrder(fixOrder, order, fixConnectionID);

            Exchange exchange = exchangeManager.getExchange(order.getExchange());
            //get ordered fix tag list for the given exchange fix connection
            List<Integer> orderedFixTagList = fixConnectionFacade.getOrderedFixTagList(fixConnectionID, order.getExchange());
            //get the exchange specific replace fix tags
            Map<Integer, Integer> replaceTags = getExchangeSpecificReplaceTags(messageType, fixConnectionID, exchange.getExchangeCode());

            //exchanges specific special changes before create original fix message
            postProcessFixOrder(fixOrder, order, sessionQualifier, customizedFixTags, replaceTags);

            //get the general fix message
            fixMessage = fixFacade.getExchangeFixMessage(fixOrder, customizedFixTags, replaceTags, orderedFixTagList);

        } catch (Exception e) {
            logger.error("Problem in generating the fix message ", e);
            throw new STPSenderException(e.toString(), e);
        }
        return fixMessage;
    }


    private void postProcessFixOrder(FixOrderInterface fixOrder, Order order, String sessionQualifier, Map<Integer, String> customFields, Map<Integer, Integer> replaceFields) {
         /*
         security id source related validation for BLOOM
         */
        if (fixOrder.getSecurityIDSource() != null && ("4").equalsIgnoreCase(fixOrder.getSecurityIDSource()) && ("BLOOM1").equalsIgnoreCase(sessionQualifier)) {
            fixOrder.setIsinCode(order.getSymbol());
        } else if (fixOrder.getSecurityIDSource() != null && ("5").equalsIgnoreCase(fixOrder.getSecurityIDSource()) && ("BLOOM1").equalsIgnoreCase(sessionQualifier)) {
            fixOrder.setReuterCode(order.getSymbol());
        } else {
            fixOrder.setIsinCode(order.getIsinCode());
            fixOrder.setReuterCode(order.getReuterCode());
        }

        /*
        this is BSE specific validation
         */
        if ((ExchangeCodes.BSE).equalsIgnoreCase(order.getExchange()) && order.getQuantity() > 0 && order.getMinQty() > 0 && order.getQuantity() == order.getMinQty()) {
            fixOrder.setExecInst(ALL_OR_NON);
        }

        // for BAJOUT need to send the order number for orig clord id
        if ("BAJOUT".equalsIgnoreCase(sessionQualifier)) {
            if (fixOrder.getMessageType().equals(FixOrderInterface.MessageType.ORDER_CANCEL_REPLACE) || fixOrder.getMessageType().equals(FixOrderInterface.MessageType.ORDER_CANCEL_REQUEST)) {
                fixOrder.setOrigClordID(order.getOrderNo());
            }
        }
        //settlement type set only for CASE exchange
        if (!"CASE".equalsIgnoreCase(order.getExchange())) {
            fixOrder.setSettlementType(0);
        }

        /*
        tif type specific validations. according to the tif type expire date (tag 126) or expire time (tag 432) is set to null
        when general fix message is create it checks the expireDate & expireTime before set tag values
         */
        if ("DFM".equalsIgnoreCase(order.getExchange())) {
            if (order.getTimeInForce() == OrderTIF.DAY.getCode()) {
                fixOrder.setExpireTime(null);
            } else if (order.getTimeInForce() == OrderTIF.GTD.getCode() || order.getTimeInForce() == OrderTIF.FOK.getCode()) {
                fixOrder.setExpireDate(null);
            } else {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
        } else if ("BSE".equalsIgnoreCase(order.getExchange())) {
            //below changes are for BSE_XTREAM
            //tag 1138 is set as a custom filed
            double forexRate = 1;
            if (fixOrder.getMaxFloor() != 0) {
                customFields.put(1138, Double.toString(fixOrder.getMaxFloor()));
            }
            //tag 48 value is symbol
            customFields.put(48, fixOrder.getSymbol());
            // no need to send tag 55 for this case
            replaceFields.put(55, -1);

            BaseSymbol symbolBean = null;
            SymbolPriceData symbolPriceData = null;
            double marketPrice = 0.0;
            String securitySubType = "REGULAR";
            try {
                symbolBean = symbolManager.getSymbol(fixOrder.getSymbol(), fixOrder.getSecurityExchange(),
                        BaseSymbol.SecurityType.getEnum(fixOrder.getSecurityType()));
                //calculate the forex rate
                symbolPriceData = symbolPriceManager.getPriceDataForSymbol(order.getSymbol(), order.getExchange());
                //calculate market price
                marketPrice = symbolPriceData.getLastTradePrice();
                if (marketPrice <= 0) {
                    marketPrice = symbolPriceData.getMax();
                }
                double ordValue = fixOrder.getOrderQty() * marketPrice;
                String ipoMarketCode = sysLevelParaManager.getSysLevelParameter(SystemParameter.BSE_IPO_MKT_CODE).getParaValue();
                String spMarketCode = sysLevelParaManager.getSysLevelParameter(SystemParameter.BSE_SP_MKT_CODE).getParaValue();
                String minOrdValue = sysLevelParaManager.getSysLevelParameter(SystemParameter.BSE_SPMKT_MIN_ORDVAL).getParaValue();
                double minOrderValue = 0.0;
                if (minOrdValue != null) {
                    minOrderValue = Double.parseDouble(minOrdValue);
                }
                if (symbolBean.getIpo() == 1) {
                    securitySubType = ipoMarketCode;
                } else if (ordValue * forexRate >= minOrderValue) {
                    securitySubType = spMarketCode;
                }
            } catch (Exception e) {
                logger.error("Error in locating the symbol bean - {}", e);
            }
            //tag 762 value is symbol
            customFields.put(762, securitySubType);

            if (order.getTimeInForce() == OrderTIF.DAY.getCode()) {
                fixOrder.setExpireTime(null);
            } else if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
                fixOrder.setExpireDate(null);
            } else if (order.getTimeInForce() == OrderTIF.FOK.getCode()) {
                fixOrder.setExpireTime(null);
            } else {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
        } else if ("ADSM".equalsIgnoreCase(order.getExchange())) {
            //tag 22 value is set to 99 for ADSM_XTREAM
            if (fixOrder.getSecurityIDSource() != null && "99".equalsIgnoreCase(fixOrder.getSecurityIDSource())) {
                //tag 1138 is set as a custom filed
                if (fixOrder.getMaxFloor() != 0) {
                    customFields.put(1138, Double.toString(fixOrder.getMaxFloor()));
                }
                //tag 48 value is symbol
                customFields.put(48, fixOrder.getSymbol());
                // no need to send tag 55 for this case
                replaceFields.put(55, -1);
                String securitySubType = "EQTY";
                BaseSymbol symbolBean = null;
                try {
                    symbolBean = symbolManager.getSymbol(fixOrder.getSymbol(), fixOrder.getSecurityExchange(),
                            BaseSymbol.SecurityType.getEnum(fixOrder.getSecurityType()));
                } catch (Exception e) {
                    logger.error("Error in locating the symbol bean - {}", e);
                }
                if (symbolBean != null) {
                    if (symbolBean.getSecuritySubType() != null && !("").equalsIgnoreCase(symbolBean.getSecuritySubType())) {
                        securitySubType = symbolBean.getSecuritySubType();
                    }
                }
                //tag 762 value is symbol
                customFields.put(762, securitySubType);
            }

            if (order.getTimeInForce() == OrderTIF.DAY.getCode()) {
                fixOrder.setExpireTime(null);
            } else if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
                fixOrder.setExpireDate(null);
            } else if (order.getTimeInForce() == OrderTIF.FOK.getCode()) {
                fixOrder.setExpireTime(null);
            } else {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
        } else if ("TDWL".equalsIgnoreCase(order.getExchange())) {
            if ("BAJOUT".equalsIgnoreCase(sessionQualifier)) {
                if (order.getTimeInForce() == OrderTIF.GTD.getCode() || order.getTimeInForce() == OrderTIF.FOK.getCode()) {
                    fixOrder.setExpireTime(null);
                } else {
                    fixOrder.setExpireTime(null);
                    fixOrder.setExpireDate(null);
                }
            } else {
                setFixOrderExpireTime(fixOrder, order, sessionQualifier);
            }
        } else if ("DGCX".equalsIgnoreCase(order.getExchange())) {
            if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
                fixOrder.setExpireDate(null);
            } else {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
        } else if ("DIFX".equalsIgnoreCase(order.getExchange())) {
            if ("ROROUT4".equalsIgnoreCase(sessionQualifier)) {
                if (order.getTimeInForce() == OrderTIF.GTD.getCode() || order.getTimeInForce() == OrderTIF.FOK.getCode()) {
                    fixOrder.setExpireTime(null);
                } else {
                    fixOrder.setExpireTime(null);
                    fixOrder.setExpireDate(null);
                }
                if (fixOrder.getMessageType().equals(FixOrderInterface.MessageType.ORDER_CANCEL_REQUEST)) {
                    //tag 125 value is "F"
                    customFields.put(125, FixOrderInterface.MessageType.ORDER_CANCEL_REQUEST.getCode());
                }
            } else {
                if (order.getTimeInForce() == OrderTIF.DAY.getCode()) {
                    fixOrder.setExpireTime(null);
                } else if (order.getTimeInForce() == OrderTIF.GTD.getCode() || order.getTimeInForce() == OrderTIF.FOK.getCode()) {
                    fixOrder.setExpireDate(null);
                } else {
                    fixOrder.setExpireTime(null);
                    fixOrder.setExpireDate(null);
                }
            }
        } else if ("FORX".equalsIgnoreCase(order.getExchange())) {
            fixOrder.setExpireTime(null);
            fixOrder.setExpireDate(null);
        } else if ("DBFO".equalsIgnoreCase(sessionQualifier) || "PATS".equalsIgnoreCase(sessionQualifier)) {
            if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
                fixOrder.setExpireTime(null);
            } else {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
        } else if ("CHIX".equalsIgnoreCase(order.getExchange()) || "SHARQ".equalsIgnoreCase(sessionQualifier)) {
            if (order.getTimeInForce() != OrderTIF.GTD.getCode()) {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
        } else if ("PINN1".equalsIgnoreCase(sessionQualifier)) {
            if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
                fixOrder.setExpireDate(null);
            } else {
                fixOrder.setExpireTime(null);
                fixOrder.setExpireDate(null);
            }
            if (!fixOrder.getMessageType().equals(FixOrderInterface.MessageType.ORDER_CANCEL_REQUEST) && !fixOrder.getMessageType().equals(FixOrderInterface.MessageType.ORDER_CANCEL_REPLACE)) {
                //tag 440 value is portfolio number same as tag 1
                customFields.put(440, fixOrder.getPortfolioNo());
            }
        } else if (fixOrder.getSide() == '6') {
            fixOrder.setSide('1');
            customFields.put(77, "C");
        } else {
            setFixOrderExpireTime(fixOrder, order, sessionQualifier);
        }
    }

    private void setFixOrderExpireTime(FixOrderInterface fixOrder, Order order, String sessionQualifier) {
        if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
            if ("DSM".equalsIgnoreCase(order.getExchange()) || "BLOOM1".equalsIgnoreCase(sessionQualifier)) {
                fixOrder.setExpireDate(null);
            }
        } else {
            fixOrder.setExpireTime(null);
            fixOrder.setExpireDate(null);
        }
    }


    private Map<Integer, Integer> getExchangeSpecificReplaceTags(FixOrderInterface.MessageType messageType,
                                                                 String fixConnectionID, String exchangeCode) throws FIXConnectionException {
        Map<Integer, Integer> replaceTags = new HashMap<>();
        Map<Integer, Integer> cancelReplaceTags = new HashMap<>();
        //get the tags to be replaced for the cancel request
        FIXConnection fixConnection = fixConnectionFacade.getFixConnection(fixConnectionID);
        //get the fix tags to be replaced for the given fix connection general to all the exchanges in that fix connection
        replaceTags.putAll(fixConnection.getReplaceFixTags());
        //get the fix tags to be replaced for the given exchange for given fix connection
        replaceTags.putAll(fixConnection.getExchangeReplaceTags(exchangeCode));
        if (messageType == FixOrderInterface.MessageType.ORDER_CANCEL_REQUEST) {
            cancelReplaceTags = fixConnection.getCancelRequestReplaceTags(exchangeCode);
        } else if (messageType == FixOrderInterface.MessageType.ORDER_CANCEL_REPLACE) {
            //get the tags to be replaced for the amend request
            cancelReplaceTags = fixConnection.getAmendRequestReplaceTags(exchangeCode);
        }
        replaceTags.putAll(cancelReplaceTags);

        return replaceTags;
    }


    private void populateFixOrder(FixOrderInterface fixOrder, Order order, String fixConnectionID) throws STPSenderException {
        fixOrder.setClordID(order.getClOrdID());
        String externalRefNo = null;
        //getting the customer exchange account number
        try {
            Account account = accountManager.getAccount(order.getSecurityAccount());
            ExchangeAccount exchangeAccount = account.getExchangeAccountsList().get(order.getExchange());
            externalRefNo = exchangeAccount.getExternalRefNo();
            //check whether account is fully disclose or ominibus
            if (exchangeAccount.getAccountType() == 0) {
                fixOrder.setPortfolioNo(exchangeAccount.getExchangeAccNumber());
            } else {
                //Need to set the exchange account number of the parent account
                Account parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                fixOrder.setPortfolioNo(parentAccount.getExchangeAccountsList().get(order.getExchange()).getExchangeAccNumber());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new STPSenderException(e.toString(), e);
        }


        try {
            ExchangeConnection exchangeConnection = fixConnectionFacade.getFixConnection(fixConnectionID).getExchangeConnections().
                    get(order.getExchange());
            if (exchangeConnection != null) {
                PropertyEnable propertyEnable = exchangeConnection.getMasterAccountRoutingEnabled();
                String masterAccountNumber = exchangeConnection.getMasterAccountNumber();
                String exchangeAccountNumber = fixOrder.getPortfolioNo();
                //if the external reference routing account number is not null need to consider its value instead of the master account nuumber
                if (externalRefNo != null) {
                    fixOrder.setPortfolioNo(externalRefNo + Delimeter.VL.getCode() + exchangeAccountNumber +
                            Delimeter.VL.getCode() + order.getRoutingAccRef());
                } else if (propertyEnable == PropertyEnable.YES && masterAccountNumber != null) {
                    fixOrder.setPortfolioNo(masterAccountNumber + Delimeter.VL.getCode() + exchangeAccountNumber +
                            Delimeter.VL.getCode() + order.getRoutingAccRef());
                } else {
                    fixOrder.setPortfolioNo(exchangeAccountNumber);
                }
                logger.info("Portfolio number - {}", fixOrder.getPortfolioNo());
            } else {
                logger.error("Exchange connection not configured for the given exchange - {}", order.getExchange());
            }
        } catch (FIXConnectionException e) {
            logger.error(e.getMessage(), e);
            throw new STPSenderException(e.toString(), e);
        }
        //for the options symbols need to set the symbol in specific format
        if (order.getSecurityType().equalsIgnoreCase(BaseSymbol.SecurityType.OPTION.getCode())) {
            fixOrder.setSymbol(getOPRABaseSymbol(order.getSymbol()));
        } else {
            fixOrder.setSymbol(order.getSymbol());
        }
        fixOrder.setSide(order.getSide().getCode().toCharArray()[0]);
        fixOrder.setOrderQty(order.getQuantity());
        fixOrder.setPrice(order.getPrice());
        fixOrder.setOrigClordID(order.getOrigClOrdID());
        fixOrder.setTransactTime(DateFormatterUtil.formatDateToString(new Date(), DateFormatterUtil.DATE_FORMAT_YYYY_MM_DD));
        fixOrder.setMinQty((int) order.getMinQty());
        fixOrder.setMaxFloor((int) order.getMaxFloor());
        fixOrder.setOrdType(getOrdTypeForExchange(order.getType(), order.getExchange(), order.getMarketCode()).getCode().charAt(0));
        fixOrder.setTimeInForce(order.getTimeInForce());
        fixOrder.setExpireTime(order.getExpireTime());
        fixOrder.setExpireDate(order.getExpireTime());
        fixOrder.setSecurityExchange(order.getExchange());
        fixOrder.setTradingSessionID(order.getMarketCode());
        fixOrder.setOrderID(order.getOrdID());
        fixOrder.setOrdStatus((order.getStatus().getCode()).charAt(0));
        fixOrder.setLeavesQty(order.getLeavesQty());
        fixOrder.setReuterCode(order.getReuterCode());
        fixOrder.setSecurityIDSource(order.getSecurityIDSource());
        fixOrder.setCurrency(order.getCurrency());
        fixOrder.setSecurityType(order.getSecurityType());
        fixOrder.setExchangeSymbol(order.getExchangeSymbol());
        fixOrder.setOrderNo(order.getOrderNo());
        fixOrder.setPriceFactor(order.getPriceFactor());
        if (order.getPriceFactor() > 0) {
            fixOrder.setPrice(DecimalFormatterUtil.round(order.getPrice() * order.getPriceFactor(), 5));
        }
        fixOrder.setStopPx(order.getStopPx());
    }


    private Map<Integer, String> getCustomizedFixTags(Order order, String fixConnectionId) throws ExchangeException,
            FIXConnectionException {
        Map<Integer, String> exchangeSpecFixTags = new HashMap<>();
        //take the value for tag-50    - dealer exchange code that is the code give for dealer by exchange
        String tag50 = getExchangeCode(order.getUserId(), order.getExchange(), order.getChannel().getCode());
        // get the fix connection specific fix tags
        Map<Integer, String> fixConSpecFixTags = fixConnectionFacade.getFIXConnectionData(fixConnectionId, order.getExchange());
        //Creating the special fix tag map. Priority is in the order of - execution broker - FIXConstants Connection - exchange
        if (fixConSpecFixTags != null) {
            exchangeSpecFixTags.putAll(fixConSpecFixTags);
        }
        //add custom defined tags
        if (tag50 != null && ("").equals(tag50)) {
            exchangeSpecFixTags.put(CONST50, tag50);
        }
        return exchangeSpecFixTags;
    }


    private String getExchangeCode(String userName, String exchange, int channel) {
        String exchangeCode = null;
        //Order has put by a dealer hence need to check dealer has a exchange id

        //if user does not have exchange code need to get the channel specific exchange code
        try {
            return exchangeManager.getChannelsExchangeCode(exchange, channel);
        } catch (Exception e) {
            logger.error("Problem in getting channel exchange code ", e);
           return exchangeCode;
        }
    }

    private String getOPRABaseSymbol(String symbolCode) {
        StringTokenizer st = new StringTokenizer(symbolCode.trim(), "\\");
        return st.nextToken();
    }
}
