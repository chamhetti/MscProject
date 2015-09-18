package lk.ac.ucsc.oms.trade_controller.normalOrders.helper;


import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.customer.api.*;
import lk.ac.ucsc.oms.customer.api.beans.CustomerValidationReply;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.exchanges.api.ExchangeFactory;
import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.exchanges.api.ExchangeValidator;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.beans.SubMarket;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;

import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.beans.OrderValidationReply;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.stp_connector.api.STPConnector;
import lk.ac.ucsc.oms.stp_connector.api.bean.STPValidationReply;
import lk.ac.ucsc.oms.symbol.api.SymbolFacadeFactory;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.SymbolValidator;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolValidationReply;


import lk.ac.ucsc.oms.trade_controller.helper.ExchangeCodes;
import lk.ac.ucsc.oms.trade_controller.helper.ModuleDINames;
import lk.ac.ucsc.oms.trade_controller.helper.ValidationReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class OrderValidatorMFS implements OrderValidator {
    private static Logger logger = LogManager.getLogger(OrderValidatorMFS.class);
    private OrderManager orderManagementFacadeInterface;
    private CustomerValidator customerValidator;
    private CustomerManager customerManager;
    private AccountManager accountManager;
    private ExchangeValidator exchangeValidator;
    private ExchangeManager exchangeManager;
    private SymbolValidator symbolValidator;
    private SymbolManager symbolManager;
    private STPConnector stpConnector;

    public OrderValidatorMFS(ApplicationContext ctx) {
        orderManagementFacadeInterface = ctx.getBean(ModuleDINames.ORDER_FACTORY_DI_NAME, OrderManagementFactory.class).getOrderManagementFacadeInterface();
        customerValidator = ctx.getBean(ModuleDINames.CUSTOMER_FACTORY_DI_NAME, CustomerFactory.class).getCustomerValidator();
        customerManager = ctx.getBean(ModuleDINames.CUSTOMER_FACTORY_DI_NAME, CustomerFactory.class).getCustomerManager();
        accountManager = ctx.getBean(ModuleDINames.CUSTOMER_FACTORY_DI_NAME, CustomerFactory.class).getAccountManager();
        exchangeValidator = ctx.getBean(ModuleDINames.EXCHANGE_FACTORY_DI_NAME, ExchangeFactory.class).getExchangeValidator();
        exchangeManager = ctx.getBean(ModuleDINames.EXCHANGE_FACTORY_DI_NAME, ExchangeFactory.class).getExchangeManager();
        symbolManager = ctx.getBean(ModuleDINames.SYMBOL_FACADE_FACTORY_DI_NAME, SymbolFacadeFactory.class).getSymbolManager();
        symbolValidator = ctx.getBean(ModuleDINames.SYMBOL_FACADE_FACTORY_DI_NAME, SymbolFacadeFactory.class).getSymbolValidator();
        stpConnector = ctx.getBean(ModuleDINames.STP_CONNECTOR_DI_NAME, STPConnector.class);
    }

    @Override
    public Order validateNewOrder(Order order) {
        ValidationReply reply;
        //set dealer name for the DT channel

        //Check the validity of the customer and the account
        reply = validateCustomer(order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }
        //check the validity of the symbol of the order
        reply = validateSymbol(order.getSymbol(), order.getExchange(), order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }

        //validate the order request with the exchange properties
        reply = validateExchange(order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }
        //validate the parent account in the case of sub account
        reply = validateParentAccount(order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }

        reply = validateSymbolRestrictions(order.getSymbol(), order.getExchange(), order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }
        //check and assign routing account number of the order
        assignRoutingAccount(order);
        if (order.getStatus() == OrderStatus.REJECTED) {
            return order;
        }

        //check the client channel and assign the dealer id of the order
        //validate the basic parameter of the new order request to check the validity
        reply = validateBasicOrderParam(order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }

        //assign exec broker
        assignExecBrokerID(order);

        //validate fix connection exec broker. Mainly whether fix connection ready for sending this order to exchange
        reply = validateFIXConnection(order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }
        //validate margin entitlement
        reply = validateMarginEntitlementEnabled(order);
        if (reply.getStatus() == Status.FAILED) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, reply);
            return order;
        }

        return order;
    }

    public OrderPropertyPopulator getOrderPropertyPopulator() {
        return OrderPropertyPopulator.getInstance();
    }

    @Override
    public Order validateAmendOrder(Order amendOrder, Order oldOrder) {
        OrderValidationReply ordReply;
        ValidationReply reply;
        //set dealer name for the DT channel

        //Validate with old order whether it is exist and it is at the status that can be amended.
        try {
            ordReply = orderManagementFacadeInterface.isValidAmendOrder(amendOrder, oldOrder);
            //if validation fail no need to continue
            if (ordReply.getStatus() == Status.FAILED) {
                amendOrder.setStatus(OrderStatus.REJECTED);
                reply = new ValidationReply(Status.FAILED, ordReply.getRejectReasonText(),
                        ordReply.getErrorMsgParameters());
                getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
                return amendOrder;
            }
        } catch (InvalidOrderException e) {
            amendOrder.setStatus(OrderStatus.REJECTED);
            reply = new ValidationReply(Status.FAILED, "Invalid Amend Order");
            getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
            return amendOrder;
        }


        //validate the symbol specific properties
        reply = validateSymbolForAmend(amendOrder);
        if (reply.getStatus() == Status.FAILED) {
            amendOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
            return amendOrder;
        }
        //validate the order request with the exchange properties
        reply = validateExchange(amendOrder);
        if (reply.getStatus() == Status.FAILED) {
            amendOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
            return amendOrder;
        }
        //validate Amend specific exchange properties.
        reply = validateExchangeAmendSpecific(amendOrder);
        if (reply.getStatus() == Status.FAILED) {
            amendOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
            return amendOrder;
        }
        //validate the basic parameter of the new order request to check the validity
        reply = validateBasicOrderParam(amendOrder);
        if (reply.getStatus() == Status.FAILED) {
            amendOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
            return amendOrder;
        }

        //validate the fix connectivity properties
        //orders at the processing status handle inside our oms hence need to have fix connection
        if (oldOrder.getStatus() != OrderStatus.PROCESSING) {
            reply = validateFIXStatus(amendOrder);
            if (reply.getStatus() == Status.FAILED) {
                amendOrder.setStatus(OrderStatus.REJECTED);
                getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
                return amendOrder;
            }
        }
        reply = validateMarginEntitlementEnabled(amendOrder);

        if (reply.getStatus() == Status.FAILED) {
            amendOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(amendOrder, reply);
            return amendOrder;
        }
        return amendOrder;
    }


    @Override
    public Order validateCancelOrder(Order cancelOrder, Order oldOrder) {
        OrderValidationReply ordReply;
        ValidationReply reply;
        //Validate with old order whether it is exist and at a status than can be cancelled.
        try {
            ordReply = orderManagementFacadeInterface.isValidCancelOrder(cancelOrder, oldOrder);
        } catch (InvalidOrderException e) {
            cancelOrder.setStatus(OrderStatus.REJECTED);
            reply = new ValidationReply(Status.FAILED, "Invalid Cancel Order");
            getOrderPropertyPopulator().populateRejectOrderProperties(cancelOrder, reply);
            return cancelOrder;
        }
        if (ordReply.getStatus() == Status.FAILED) {
            cancelOrder.setStatus(OrderStatus.REJECTED);
            reply = new ValidationReply(Status.FAILED, ordReply.getRejectReasonText());
            getOrderPropertyPopulator().populateRejectOrderProperties(cancelOrder, reply);
            return cancelOrder;
        }
        //validate cancel specific exchange properties.
        if (oldOrder.getStatus() == OrderStatus.SUSPENDED || oldOrder.getStatus() == OrderStatus.PENDING_NEW ||
                oldOrder.getStatus() == OrderStatus.NEW || oldOrder.getStatus() == OrderStatus.REPLACED ||
                oldOrder.getStatus() == OrderStatus.PARTIALLY_FILLED) {
            reply = validateExchangeCancelSpecific(cancelOrder);
            if (reply.getStatus() == Status.FAILED) {
                cancelOrder.setStatus(OrderStatus.REJECTED);
                getOrderPropertyPopulator().populateRejectOrderProperties(cancelOrder, reply);
                return cancelOrder;
            }
        }

        //set required properties for the cancel order from the symbol bean
        reply = validateSymbolForCancel(cancelOrder);
        if (reply.getStatus() == Status.FAILED) {
            cancelOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(cancelOrder, reply);
            return cancelOrder;
        }

        //validate fix connection exec broker. Mainly whether fix connection ready for sending this order to exchange
        reply = validateFIXConnection(cancelOrder);
        if (reply.getStatus() == Status.FAILED) {
            cancelOrder.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(cancelOrder, reply);
            return cancelOrder;
        }
        return cancelOrder;
    }

    @Override
    public Order validateBasicOrderProperties(Order order) {
        if (order.getSymbol() == null) {
            logger.error("symbol is invalid. symbol=" + order.getSymbol());
            order.setStatus(OrderStatus.REJECTED);
            return order;
        }
        if (order.getSide() == null) {
            logger.error("side is invalid. side=" + order.getSide());
            order.setStatus(OrderStatus.REJECTED);
            return order;
        }
        if (order.getQuantity() <= 0) {
            logger.error("Qty is invalid. qty=" + order.getQuantity());
            order.setStatus(OrderStatus.REJECTED);
            return order;
        }
        if (order.getType() == null) {
            logger.error("type is invalid. type=" + order.getType());
            order.setStatus(OrderStatus.REJECTED);
            return order;
        }
        if (order.getSecurityAccount() == null) {
            logger.error("securityAccount is invalid. securityAcc=" + order.getSecurityAccount());
            order.setStatus(OrderStatus.REJECTED);
            return order;
        }
        return order;
    }

    private ValidationReply validateCustomer(Order order) {
        logger.info("validating the customer with customer number -{} and exchange -{}", order.getSecurityAccount(), order.getExchange());

        try {
            //validate the account whether customer has valid account, approved etc.
            CustomerValidationReply customerValidationReply = customerValidator.hasValidAccount(order.getSecurityAccount());
            if (customerValidationReply.getValidationResult() == CustomerValidationReply.CustomerValidationResult.FAILED) {
                //Customer does not have the valid account Hence order will not proceed
                return new ValidationReply(Status.FAILED, "Account not found or not active");
            }
            //get the customer account
            Account account = accountManager.getAccount(order.getSecurityAccount());
            //get the customer
            Customer customer = customerManager.getCustomerByCustomerNumber(account.getCustomerNumber());
            //set the customer specific values to the order bean
            order.setInstitutionCode(customer.getInstitutionCode());
            //set the institution id to order

            order.setCustomerNumber(account.getCustomerNumber());
            //check customer has valid trading account and its trading enable or not
            customerValidationReply = customerValidator.hasValidExchangeAccount(order.getSecurityAccount(), order.getExchange());
            if (customerValidationReply.getValidationResult() == CustomerValidationReply.CustomerValidationResult.FAILED) {
                //Customer does not have the valid account Hence order will not proceed
                return new ValidationReply(Status.FAILED, "Exchange account not found or trading disable");
            }
            //Assign customer exchange account exec broker id to order if exist a one
            if (order.getExecBrokerId() == null || order.getExecBrokerId().isEmpty()) {
                //should have a exchange account
                ExchangeAccount exchangeAccount = account.getExchangeAccountsList().get(order.getExchange());
                //set the exec broker if customer exchange account has a assigned exec broker for this exchange.
                if (exchangeAccount.getExecBrokerCode() != null && !exchangeAccount.getExecBrokerCode().isEmpty()) {
                    order.setExecBrokerId(exchangeAccount.getExecBrokerCode());
                }
            }
        } catch (Exception e) {
            logger.error("Problem in loading the account", e);
            return new ValidationReply(Status.FAILED, "Customer Validation Issue - " + e.getMessage());
        }
        return new ValidationReply(Status.SUCCESS);
    }


    private ValidationReply validateSymbol(String symbol, String exchange, Order order) {
        logger.info("Validating the symbol -{} and exchange -{}", symbol, exchange);
        try {
            //security type of the symbol does not come with symbol hence try find out it
            if (order.getSecurityType() == null || ("").equals(order.getSecurityType())) {
                BaseSymbol.SecurityType securityType = null;
                try {
                    securityType = symbolManager.getSecurityType(order.getSymbol(), order.getExchange());
                } catch (OMSException e) {
                    logger.warn("Symbol not found in the OMS");
                }
                if (securityType == null) {
                    return new ValidationReply(Status.FAILED, "Symbol not found - Order request contain not enough/invalid data");
                    //mfs specific validations for OPRA

                }
                //assign the security type to the order
                order.setSecurityType(securityType.getCode());
            }
            //check whether symbol exist approved and trading enable one.
            SymbolValidationReply symbolValidationReply = symbolValidator.isValidSymbol(order.getSymbol(), order.getExchange(), BaseSymbol.SecurityType.getEnum(order.getSecurityType()));
            if (symbolValidationReply.getValidationResult() == SymbolValidationReply.SymbolValidationResult.FAILED) {
                return new ValidationReply(Status.FAILED, "Invalid/Trading Disable Symbol");
            }
            //getting the symbol from symbol module. At the movement client does not send the security type of the
            //symbol. Hence need to find out the symbol in following manner.
            BaseSymbol symbolBean = symbolManager.getSymbol(symbol, exchange, BaseSymbol.SecurityType.getEnum(order.getSecurityType()));
            //set the security type of the order
            order.setMarketCode(symbolBean.getMarketCode());
            if (order.getInstrumentType() == null || ("-1").equals(order.getInstrumentType())) {
                order.setInstrumentType(String.valueOf(symbolBean.getInstrumentType()));
            }
            order.setCurrency(symbolBean.getCurrency());
            //set reuter code and isin code of the symbol
            order.setReuterCode(symbolBean.getReutercode());
            order.setIsinCode(symbolBean.getIsinCode());
            //set the symbol price ratio as the price factor of the order
            order.setPriceFactor(symbolBean.getPriceRatio());

            //check whether order qty satisfy the allowed quantities for this symbol
            symbolValidationReply = symbolValidator.isOrderQtyAllowForSymbol(symbol, exchange, BaseSymbol.SecurityType.getEnum(order.getSecurityType()), order.getQuantity());
            if (symbolValidationReply.getValidationResult() == SymbolValidationReply.SymbolValidationResult.FAILED) {
                return new ValidationReply(Status.FAILED, "Order Qty less than the minimum required order qty for this symbol");
            }
            //online not enable symbols are allow to trade only using AT and DT
            if (order.getChannel() != ClientChannel.AT && order.getChannel() != ClientChannel.DT
                    && !symbolValidator.isOnlineAllowed(symbol, exchange, BaseSymbol.SecurityType.getEnum(order.getSecurityType()))) {
                return new ValidationReply(Status.FAILED, "Orders from direct client are not accept for this symbol. Allow only from AT or DT");
            }

        } catch (Exception e) {
            logger.error("Problem in loading the symbol", e);
            return new ValidationReply(Status.FAILED, "Symbol not found - Problem in loading the symbol -" + e.getMessage());
        }

        return new ValidationReply(Status.SUCCESS);
    }

    private ValidationReply validateSymbolRestrictions(String symbol, String exchange, Order order) {
        try {
            if (order.getSide() == OrderSide.BUY) {
                // check whether symbol has restricted for the customer
                if (customerValidator.isRestrictedSymbolForCustomer(order.getSecurityAccount(), order.getSymbol())) {
                    return new ValidationReply(Status.FAILED, "Symbol has restricted for this customer");
                }
                //check symbol has restricted for institution
                if (symbolValidator.isRestrictedForInstitution(symbol, exchange, BaseSymbol.SecurityType.getEnum(order.getSecurityType()), order.getInstitutionId())) {
                    return new ValidationReply(Status.FAILED, "Symbol has restricted for this institution");
                }
                //check the symbol restriction base on customer nationality
                Customer customer = customerManager.getCustomerByCustomerNumber(order.getCustomerNumber());
                SymbolValidationReply symbolValidationReply = symbolValidator.isNationalityRestrictedSymbol(symbol, exchange, BaseSymbol.SecurityType.getEnum(order.getSecurityType()),
                        customer.getPersonalProfile().getNationality(), false);
                if (symbolValidationReply.getValidationResult() == SymbolValidationReply.SymbolValidationResult.FAILED) {
                    return new ValidationReply(Status.FAILED, "Symbol not allow for this customer");
                }
                boolean shariaEnable = false;
                //in the case of sub account check parent sharia enable
                if (customer.getParentCusNumber() != null) {
                    shariaEnable = customerValidator.isShariaCompliantAccount(order.getParentAccountNumber(), order.getExchange());
                }
                //check cleint level sharia enable
                if (!shariaEnable) {
                    shariaEnable = customerValidator.isShariaCompliantAccount(order.getSecurityAccount(), order.getExchange());
                }
                //check sharia compliant or not
                if (shariaEnable) {
                    //customer is sharia compliant hence need to check whether symbol is shari or not
                    if (!symbolValidator.isShariaCompliant(symbol, exchange, BaseSymbol.SecurityType.getEnum(order.getSecurityType()), order.getInstitutionId())) {
                        return new ValidationReply(Status.FAILED, "Symbol is not sharia enable one");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Problem in loading the symbol", e);
            return new ValidationReply(Status.FAILED, "Symbol not found - Problem in loading the symbol -" + e.getMessage());
        }

        return new ValidationReply(Status.SUCCESS);

    }


    private ValidationReply validateSymbolForAmend(Order amendOrder) {
        try {
            //check the order qty against symbol allowed min qty
            SymbolValidationReply symbolValidationReply = symbolValidator.isOrderQtyAllowForSymbol(amendOrder.getSymbol(), amendOrder.getExchange(),
                    BaseSymbol.SecurityType.getEnum(amendOrder.getSecurityType()), amendOrder.getQuantity());
            if (symbolValidationReply.getValidationResult() == SymbolValidationReply.SymbolValidationResult.FAILED) {
                return new ValidationReply(Status.FAILED, "Order Qty less than the minimum required order qty for this symbol");
            }
            //getting the symbol from symbol module. At the movement client does not send the security type of the
            //symbol. Hence need to find out the symbol in following manner.
            BaseSymbol symbolBean = symbolManager.getSymbol(amendOrder.getSymbol(), amendOrder.getExchange(),
                    BaseSymbol.SecurityType.getEnum(amendOrder.getSecurityType()));
            //trade enable or not for the symbol
            if (!symbolBean.isTradeEnable()) {
                return new ValidationReply(Status.FAILED, "Trade disable for the symbol");
            }
            //set reuter code and isin code to the amend order
            amendOrder.setReuterCode(symbolBean.getReutercode());
            amendOrder.setIsinCode(symbolBean.getIsinCode());

        } catch (Exception e) {
            logger.error("Problem in loading the symbol", e);
            return new ValidationReply(Status.FAILED, "Symbol not found - Problem in loading the symbol -" + e.getMessage());
        }

        return new ValidationReply(Status.SUCCESS);
    }


    private ValidationReply validateExchange(Order order) {
        try {
            String errorMsgParameterList = null;

            //check exchange exist and it is an approved one
            if (!exchangeValidator.isValidExchange(order.getExchange())) {
                return new ValidationReply(Status.FAILED,
                        "Exchange not found");
            }
            Exchange exchange = exchangeManager.getExchange(order.getExchange());
            //check the sub market exist or not
            SubMarket subMarket = exchange.getSubMarkets().get(order.getMarketCode());
            if (subMarket == null) {
                return new ValidationReply(Status.FAILED,
                        "SubMarket does not configure properly");
            }
            // check market order allow or not
            if (!exchangeValidator.isMarketOrderAllow(order.getExchange(), subMarket, order.getChannel(), order.getType(), order.getExecBrokerId())) {
                return new ValidationReply(Status.FAILED,
                        "Market order are allow only when the market open");
            }


            //check whether order request tif supported by exchange
            if (!exchangeValidator.isAllowTif(subMarket, OrderTIF.getEnum(order.getTimeInForce()))) {
                errorMsgParameterList = order.getExchange() + ",";
                return new ValidationReply(Status.FAILED,
                        "Invalid TIF for the exchange -" + order.getExchange(), errorMsgParameterList);
            }
            //check whether this order type allow by the exchange
            if (!exchangeValidator.isAllowOrderType(subMarket, OrderType.getEnum(order.getType().getCode()))) {
                errorMsgParameterList = order.getExchange() + ",";
                return new ValidationReply(Status.FAILED,
                        "Invalid order type for the exchange -" + order.getExchange(), errorMsgParameterList);
            }
            //check whether order side allow by exchange
            if (!exchangeValidator.isAllowSide(subMarket, OrderSide.getEnum(order.getSide().getCode()))) {
                errorMsgParameterList = order.getExchange() + ",";
                return new ValidationReply(Status.FAILED,
                        "Order side not supported by exchange -" + order.getExchange(), errorMsgParameterList);
            }
            if (!exchangeValidator.isOrderAllow(order.getExchange(), order.getMarketCode(), order.getExecBrokerId())) {
                errorMsgParameterList = order.getExchange() + ",";
                return new ValidationReply(Status.FAILED,
                        "Orders are not allow at Market close time -" + order.getExchange(), errorMsgParameterList);
            }


        } catch (Exception e) {
            logger.error("Problem in loading the exchange" + e.getMessage(), e);
            return new ValidationReply(Status.FAILED,
                    "Exchange not found or not configured properly -" + e.getMessage());
        }
        return new ValidationReply(Status.SUCCESS);
    }

    private ValidationReply validateExchangeAmendSpecific(Order amendOrder) {
        String errorMsgParameterList = null;
        try {
            //order for PSE allow to amend only for dealers
            if (amendOrder.getExchange().equalsIgnoreCase(ExchangeCodes.PSE) && amendOrder.getChannel()
                    != ClientChannel.AT && amendOrder.getChannel() != ClientChannel.DT) {
                errorMsgParameterList = amendOrder.getExchange() + ",";
                return new ValidationReply(Status.FAILED,
                        "Orders for PSE not allow to amend only using AT and DT", errorMsgParameterList);
            }
            //Amend allow during the market open and pre open. That condition checking by the following method.
            if (!exchangeValidator.isAmendAllow(amendOrder.getExchange(), amendOrder.getMarketCode(), amendOrder.getExecBrokerId())) {
                errorMsgParameterList = amendOrder.getExchange() + ",";
                return new ValidationReply(Status.FAILED, "Not allow to amend for this exchange at this movement", errorMsgParameterList);
            }

        } catch (Exception e) {
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new ValidationReply(Status.FAILED, "Problem in loading exchange", errorMsgParameterList);
        }
        return new ValidationReply(Status.SUCCESS);
    }


    private ValidationReply validateExchangeCancelSpecific(Order cancelOrder) {
        ValidationReply reply = new ValidationReply(Status.SUCCESS);
        try {
            //amend is not allow during the pre open suspended time. it validate the following
            if (!exchangeValidator.isCancelAllow(cancelOrder.getExchange(), cancelOrder.getMarketCode(), cancelOrder.getExecBrokerId())) {
                return new ValidationReply(Status.FAILED, "Cancel not allowed. Please cancel the order when market is preopen/open");
            }
        } catch (Exception e) {
            return new ValidationReply(Status.FAILED, "Exchange specific validation failed -" + e.getMessage());
        }
        return reply;
    }


    private ValidationReply validateBasicOrderParam(Order order) {
        OrderValidationReply ordReply;

        try {
            ordReply = orderManagementFacadeInterface.isValidOrderParameters(order);
        } catch (InvalidOrderException e) {
            return new ValidationReply(Status.FAILED, "Invalid Order -" + e.getMessage());
        }
        if (ordReply.getStatus() == Status.FAILED) {
            return new ValidationReply(ordReply.getStatus(), ordReply.getRejectReasonText());
        }
        return new ValidationReply(Status.SUCCESS);
    }


    private ValidationReply validateFIXConnection(Order order) {
        STPValidationReply stpReply;
        ValidationReply validationReply;
        try {
            stpReply = stpConnector.isValidFixConnection(order);
            validationReply = new ValidationReply(stpReply.getStatus(), stpReply.getRejectReason(), stpReply.getRejectReasonText());
        } catch (Exception e) {
            logger.error(e.toString(), e);
            validationReply = new ValidationReply(Status.FAILED, "Invalid Fix Connection -" + e.getMessage());
        }
        return validationReply;
    }


    private ValidationReply validateFIXStatus(Order order) {
        STPValidationReply stpReply;
        ValidationReply validationReply;
        try {
            //validate fix connection exec broker. Mainly whether fix connection ready for sending this order to exchange
            if (order.getFixConnectionId() == null || ("").equalsIgnoreCase(order.getFixConnectionId())) {
                stpConnector.isValidFixConnection(order);
            }
            stpReply = stpConnector.isValidFixStatus(order);
            validationReply = new ValidationReply(stpReply.getStatus(), stpReply.getRejectReason(), stpReply.getRejectReasonText());
        } catch (Exception e) {
            logger.error(e.toString(), e);
            validationReply = new ValidationReply(Status.FAILED, "Invalid Fix Connection -" + e.getMessage());
        }
        return validationReply;
    }

    private ValidationReply validateParentAccount(Order order) {
        try {
            Customer customer = customerManager.getCustomerByCustomerNumber(order.getCustomerNumber());

            if (customer.getParentCusNumber() != null && !("").equals(customer.getParentCusNumber()) && !("-1").equals(customer.getParentCusNumber())) {
                //customer has the parent account
                String parentAccNumber;
                if (order.getParentAccountNumber() == null || order.getParentAccountNumber().isEmpty()) {
                    //This is MFS specific logic to send mustric day orders through DBFS to avoid margin limit exceed
                    parentAccNumber = accountManager.getParentAccountNumber(order.getCustomerNumber(), order.getExchange(),
                            order.getExecBrokerId(), order.getCurrency());
                    if (parentAccNumber == null) {
                        return new ValidationReply(Status.FAILED, "Parent Account not found");
                    }
                    if (("#").equals(parentAccNumber)) {
                        return new ValidationReply(Status.FAILED, "Customer has multiple parent accounts");
                    }
                } else {
                    parentAccNumber = order.getParentAccountNumber();
                }
                CustomerValidationReply customerValidationReply = customerValidator.hasValidAccount(parentAccNumber);
                if (customerValidationReply.getValidationResult() == CustomerValidationReply.CustomerValidationResult.FAILED) {
                    //Customer does not have the valid account Hence order will not proceed
                    return new ValidationReply(Status.FAILED,
                            "Parent Account not found or not active");
                }
                Account parentAccount = accountManager.getAccount(parentAccNumber);
                //check whether customer has valid cash account
                customerValidationReply = customerValidator.hasValidCashAccount(parentAccount.getCashAccNumber());
                if (customerValidationReply.getValidationResult() == CustomerValidationReply.CustomerValidationResult.FAILED) {
                    //Customer does not have the valid account Hence order will not proceed
                    return new ValidationReply(Status.FAILED,
                            "Cash account not found or not active");
                }
                order.setParentAccountNumber(parentAccNumber);

            }
        } catch (OMSException e) {
            return new ValidationReply(Status.FAILED,
                    "Parent Account not found or not active -" + e.getMessage());
        }
        return new ValidationReply(Status.SUCCESS);
    }

    private ValidationReply validateMarginEntitlementEnabled(Order order) {
        try {
            //Getting the customer account
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());

        } catch (AccountManagementException e) {
            logger.error("Account is not found: ", e);
        }
        return new ValidationReply(Status.SUCCESS);
    }


    private void assignRoutingAccount(Order order) {
        try {
            Account account = accountManager.getAccount(order.getSecurityAccount());
            ExchangeAccount exchangeAccount = account.getExchangeAccountsList().get(order.getExchange());
            //check whether account is fully disclose or omini bus
            if (exchangeAccount.getAccountType() == 0) {
                order.setRoutingAccount(exchangeAccount.getExchangeAccNumber());
            } else {
                //Need to set the exchange account number of the parent account
                Account parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                order.setRoutingAccount(parentAccount.getExchangeAccountsList().get(order.getExchange()).getExchangeAccNumber());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            order.setStatus(OrderStatus.REJECTED);
            order.setText("Exchange account number not set correctly -" + e.getMessage());
        }
    }


    private ValidationReply validateSymbolForCancel(Order cancelOrder) {
        try {

            //getting the symbol from symbol module. At the movement client does not send the security type of the
            //symbol. Hence need to find out the symbol in following manner.
            BaseSymbol symbolBean = symbolManager.getSymbol(cancelOrder.getSymbol(), cancelOrder.getExchange(),
                    BaseSymbol.SecurityType.getEnum(cancelOrder.getSecurityType()));
            //trade enable or not for the symbol
            if (!symbolBean.isTradeEnable()) {
                return new ValidationReply(Status.FAILED,
                        "Trade disable for the symbol");
            }
            //set reuter code and isin code to the amend order
            cancelOrder.setReuterCode(symbolBean.getReutercode());
            cancelOrder.setIsinCode(symbolBean.getIsinCode());
            //if the order is for KSE and the session used is SHARQ, need to set the symbol exchange code
            if (cancelOrder.getExchange().equalsIgnoreCase(ExchangeCodes.KSE)) {
                cancelOrder.setExchangeSymbol(symbolBean.getExchangeSymbol());
            }
        } catch (Exception e) {
            logger.error("Problem in loading the symbol", e);
            return new ValidationReply(Status.FAILED,
                    "Symbol not found - Problem in loading the symbol -" + e.getMessage());
        }

        return new ValidationReply(Status.SUCCESS);
    }


    private void assignExecBrokerID(Order order) {
        try {
            String execBrokerID = null;
            int accountType = -1;
            if (order.getExecBrokerId() == null || ("").equals(order.getExecBrokerId()) || ("-1").equals(order.getExecBrokerId())) {
                Customer customer = customerManager.getCustomerByCustomerNumber(order.getCustomerNumber());
                Account account = accountManager.getAccount(order.getSecurityAccount());
                ExchangeAccount customerExchangeAccount = account.getExchangeAccountsList().get(order.getExchange());
                if (customerExchangeAccount != null) {
                    accountType = customerExchangeAccount.getAccountType();
                }
                //if this is a sub account and not a fully disclosed account take the parent account and get the exec broker
                //assign to it
                if (customer.getParentCusNumber() != null && !("").equals(customer.getParentCusNumber()) && !("-1").equals(customer.getParentCusNumber()) && accountType > 0) {
                    Account parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                    ExchangeAccount parentExchangeAccount = parentAccount.getExchangeAccountsList().get(order.getExchange());

                    if (parentExchangeAccount != null) {
                        execBrokerID = parentExchangeAccount.getExecBrokerCode();
                    } else {
                        logger.debug("Exchange account is not found for the order exchange - {}", order.getExchange());
                    }
                    if (execBrokerID != null && !execBrokerID.isEmpty() && !("-1").equals(execBrokerID)) {
                        //exchange account has assigned exec broker
                        order.setExecBrokerId(execBrokerID);
                    }
                } else {
                    if (customerExchangeAccount != null) {
                        execBrokerID = customerExchangeAccount.getExecBrokerCode();
                    }
                    if (execBrokerID != null && !execBrokerID.isEmpty() && !("-1").equals(execBrokerID)) {
                        //exchange account has assigned exec broker
                        order.setExecBrokerId(execBrokerID);
                    }
                }
                //if the exec broker id is null then it has to be load from the customer institution

            }
        } catch (Exception e) {
            logger.info("Error in assigning exec broker id for the customer - {}, Error - {}", order.getCustomerNumber(), e.getMessage());
        }
    }
}
