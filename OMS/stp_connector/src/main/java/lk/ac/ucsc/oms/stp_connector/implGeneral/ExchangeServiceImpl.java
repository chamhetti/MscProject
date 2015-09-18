package lk.ac.ucsc.oms.stp_connector.implGeneral;

import lk.ac.ucsc.oms.common_utility.api.MessageSender;
import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.beans.MarketConnectStatus;
import lk.ac.ucsc.oms.exchanges.api.beans.MarketStatus;
import lk.ac.ucsc.oms.exchanges.api.beans.SubMarket;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.fix.api.FIXFacadeInterface;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;
import lk.ac.ucsc.oms.fixConnection.api.ConnectionStatus;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFacade;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.stp_connector.api.STPConnectorType;
import lk.ac.ucsc.oms.stp_connector.api.bean.STPValidationReply;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPConnectException;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPSenderException;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPValidationException;
import lk.ac.ucsc.oms.stp_connector.implGeneral.bean.AppiaMessage;
import lk.ac.ucsc.oms.stp_connector.implGeneral.bean.STPValidationReplyImpl;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExchangeServiceImpl {
    private static Logger logger = LogManager.getLogger(ExchangeServiceImpl.class);
        private FIXConnectionFacade fixConnectionFacade;
    private FIXFacadeInterface fixFacade;
    private SysLevelParaManager sysLevelParaManager;
    private ExchangeManager exchangeManager;
    private AccountManager accountManager;
    private MessageSender queueSender;
    private static final String APPIA_MESSAGE_QUEUE = "ToAppia";
    private static final String STP_MESSAGE_QUEUE = "ToSTPManager";


    public ExchangeServiceImpl(FIXConnectionFacade fixConnectionFacade,
                               ExchangeManager exchangeManager, FIXFacadeInterface fixFacade, AccountManager accountManager,
                               SysLevelParaManager sysLevelParaManager, MessageSender queueSender) {

        this.fixConnectionFacade = fixConnectionFacade;
        this.exchangeManager = exchangeManager;
        this.fixFacade = fixFacade;
        this.accountManager = accountManager;
        this.sysLevelParaManager = sysLevelParaManager;
        this.queueSender = queueSender;
    }


    public Order sendToExchange(FixOrderInterface.MessageType messageType, Order order, String fixMessage,
                                String sessionQualifier) throws STPSenderException {
        try {
            AppiaMessage appiaMessage = new AppiaMessage(order.getClOrdID(), sessionQualifier, String.valueOf(messageType.getCode()), fixMessage);
            //create appia message map
            Map messageMap = createMapMessage(appiaMessage);
            //send the map message to the appia queue
            queueSender.sendMessage(APPIA_MESSAGE_QUEUE, messageMap);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new STPSenderException("Error sending order to exchange ", e);
        }
        return order;
    }

    public Order isValidExchangeConnection(Order order) throws STPValidationException {
        if (!isMarketOpen(order.getExchange(), order.getMarketCode(), order.getExecBrokerId())) {
            //Market is not open or pre open
            logger.info("Market is not open hence order status is changed to OMS_ACCEPTED");
            order.setStatus(OrderStatus.OMS_ACCEPTED);
            return order;
        }
        try {
            //getting the fix connection id
            String fixConnectionId = getFixConnectionIdByOrder(order);
            FIXConnection fixConnection = fixConnectionFacade.getFixConnection(fixConnectionId);
            //check the status of the fix connection. if is is not connected need to keep the order
            if (fixConnection.getConnectionStatus() != ConnectionStatus.CONNECTED) {
                order.setStatus(OrderStatus.OMS_ACCEPTED);
                return order;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new STPValidationException("Error determining connection validity ", e);
        }
        return order;
    }

    public STPValidationReply isValidFixConnection(Order order) throws STPValidationException {
        STPValidationReply reply = new STPValidationReplyImpl();
        try {

            String fixConId = getFixConnectionIdByOrder(order);

            if (fixConId == null || "".equalsIgnoreCase(fixConId)) {
                return new STPValidationReplyImpl(Status.FAILED,
                        "FIX Connection does not found");
            }
            //getting the fix connect related to this order
            FIXConnection fixConnection = fixConnectionFacade.getFixConnection(fixConId);
            //Check whether fix connection configured properly
            if (fixConnection == null || fixConnection.getFixTags() == null || fixConnection.getFixTags().isEmpty()) {
                return new STPValidationReplyImpl(Status.FAILED,
                        "FIXConstants Connection not configured properly");
            }

            //Market orders does not allow when fix connection is down
            if (order.getType() == OrderType.MARKET && fixConnection.getConnectionStatus() != ConnectionStatus.CONNECTED) {
                return new STPValidationReplyImpl(Status.FAILED,
                        "FIXConstants connection not connected. Market orders does not allow");
            }
            order.setFixConnectionId(fixConId);

            //get the session qualifier from the security exchange.
            String sessionQualifier = null;
            if (fixConnection.getExchangeConnections() != null && fixConnection.getExchangeConnections().containsKey(order.getExchange())) {
                sessionQualifier = fixConnection.getExchangeConnections().get(order.getExchange()).getSessionQualifier();
            }
            //if not found the session qualifier from the exchange connection get it from the fix connection
            if (sessionQualifier == null) {
                sessionQualifier = fixConnection.getSessionQualifier();
            }

        } catch (Exception e) {
            throw new STPValidationException("FIXConstants Connection not configured properly", e);
        }
        reply.setStatus(Status.SUCCESS);
        return reply;
    }


    public STPValidationReply isValidFixStatus(Order order) throws STPConnectException {
        STPValidationReply reply = new STPValidationReplyImpl();
        try {
            FIXConnection fixConnection = fixConnectionFacade.getFixConnection(order.getFixConnectionId());
            if (fixConnection.getConnectionStatus() != ConnectionStatus.CONNECTED) {
                return new STPValidationReplyImpl(Status.FAILED,
                        "FIX connection not connected. Amend not allow");
            }
        } catch (Exception e) {
            throw new STPConnectException("FIXConstants Connection not configured properly", e);
        }
        reply.setStatus(Status.SUCCESS);
        return reply;
    }


    private boolean isMarketOpen(String exchangeCode, String marketCode, String execBrokerId) {
        Exchange exchange = null;

        try {
            exchange = exchangeManager.getExchange(exchangeCode);
        } catch (ExchangeException e) {
            return false;
        }
        SubMarket subMarket = exchange.getSubMarkets().get(marketCode);
        if (subMarket == null) {
            return false;
        }
        MarketStatus marketStatus = exchangeManager.getSubMarketStatus(execBrokerId, exchangeCode, marketCode);
        logger.info("Sub Market Status - {}", marketStatus);

        MarketConnectStatus connectStatus = exchangeManager.getSubMarketConnectStatus(execBrokerId, exchangeCode, marketCode);
        logger.info("Sub Market Connect Status - {}", connectStatus);

        return (marketStatus == MarketStatus.OPEN || marketStatus == MarketStatus.PRE_OPEN) && connectStatus == MarketConnectStatus.CONNECTED;

    }


    private String getFixConnectionIdByOrder(Order order) throws  AccountManagementException, ExchangeException {
        //check whether exchange has default exec broker
        Exchange exchange = exchangeManager.getExchange(order.getExchange());
        return exchange.getExchangeCode();
    }


    public void requestMarketStatus(String fixConnectionId, String exchangeCode) throws STPConnectException {
        try {
            //getting the exchange
            Exchange exchange = exchangeManager.getExchange(exchangeCode);
            if (exchange == null) {
                logger.error("Exchange not configured at OMS Level");
                throw new STPConnectException("Invalid Exchange");
            }
            //get the sub market list of the exchange
            Map<String, SubMarket> subMarketList = exchange.getSubMarkets();
            SubMarket subMarket;
            Date today = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            today = dateFormat.parse(dateFormat.format(today));
            Date lastUpdated;
            boolean result;
            for (Map.Entry<String, SubMarket> entry : subMarketList.entrySet()) {
                String subMarketCode = entry.getKey();
                subMarket = entry.getValue();
                try {
                    if (subMarket.getLastStatusReqDate() != null) {
                        lastUpdated = dateFormat.parse(dateFormat.format(subMarket.getLastStatusReqDate()));
                        //check already request for the date if so no need to send the request again
                        if (today.equals(lastUpdated)) {
                            continue;
                        }
                    } else {
                        logger.info("Market status not requested for this market yet");
                    }
                } catch (Exception e) {
                    logger.error("Last request update not configured", e);
                    throw new STPSenderException(e.toString(), e);
                }

                //send the request status message to exchange
                result = requestMarketStatus(fixConnectionId, exchangeCode, subMarket.getMarketCode());
                if (result) {
                    //update the sub market
                    subMarket.setLastStatusReqDate(today);
                }
                exchange.getSubMarkets().put(subMarketCode, subMarket);
            }
            //save changes to exchange.
            exchangeManager.updateExchange(exchange);

        } catch (Exception e) {
            logger.error("Problem in sending market status request message", e);
            throw new STPConnectException(e.toString(), e);
        }
    }


    private boolean requestMarketStatus(String fixConnectionId, String exchangeCode, String marketCode) throws STPConnectException {
        try {
            FIXConnection fixConnection = fixConnectionFacade.getFixConnection(fixConnectionId);
            FixOrderInterface fixOrder = fixFacade.getEmptyFixOrder();

            fixOrder.setTradSesReqID(String.valueOf(System.currentTimeMillis()));
            fixOrder.setTradingSessionID(marketCode);
            fixOrder.setSenderCompID(fixConnection.getFixTags().get(FIXConstants.SENDER_COMP_ID));
            fixOrder.setTargetCompID(fixConnection.getFixTags().get(FIXConstants.TARGET_COMP_ID));
            fixOrder.setMessageType(FixOrderInterface.MessageType.TRADING_SESSION_STATUS_REQUEST);
            fixOrder.setTradSesMethod("1");
            fixOrder.setSubscriptionRequestType((char) 1);
            fixOrder.setFixVersion("FIX4.4");

            List<Integer> orderedFixTagList;
            try {
                orderedFixTagList = exchangeManager.getExchange(exchangeCode).getOrderedFixTagList();
            } catch (ExchangeException e) {
                throw new STPConnectException("Error obtaining ordered FIX tag list", e);
            }

            String fixMessage = fixFacade.getExchangeFixMessage(fixOrder, fixConnection.getFixTags(), null, orderedFixTagList);
            String sessionQualifier = fixConnection.getSessionQualifier();
            AppiaMessage appiaMessage = new AppiaMessage(marketCode, sessionQualifier, String.valueOf(FixOrderInterface.MessageType.TRADING_SESSION_STATUS_REQUEST.getCode()), fixMessage);

            logger.info("Market Status Request FIXConstants Message -{}", fixMessage);
            Map map = createMapMessage(appiaMessage);
            queueSender.sendMessage(APPIA_MESSAGE_QUEUE, map);
            return true;
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new STPConnectException(e.toString(), e);
        }

    }

      private Map createMapMessage(AppiaMessage appiaMessage) {
        Map<String, String> appiaMessageMap = new HashMap<>();
        appiaMessageMap.put("ClientMsgID", appiaMessage.getClientMsgID());
        appiaMessageMap.put("SessionID", appiaMessage.getSessionID());
        appiaMessageMap.put("Protocol", appiaMessage.getProtocol());
        appiaMessageMap.put("Format", appiaMessage.getFormat());
        appiaMessageMap.put("MessageType", appiaMessage.getMessageType());
        appiaMessageMap.put("MessageData", appiaMessage.getMessageData());

        return appiaMessageMap;
    }
}
