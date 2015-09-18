package lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper;

import lk.ac.ucsc.oms.cash_trading.api.CashTradingManager;
import lk.ac.ucsc.oms.cash_trading.api.CashTradingReply;
import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.HoldingManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingKey;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingRecord;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingKeyBean;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskFailReasons;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagementReply;
import lk.ac.ucsc.oms.rms_equity.api.exceptions.EquityRmsException;
import lk.ac.ucsc.oms.rms_equity.implGeneral.bean.EquityRiskManagementReplyBean;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util.CommonUtil;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util.EquityRmsUtil;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util.SellRiskManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;


public class SellRiskManager {
    private static final double DEFAULT_QUANTITY = 0; // default order quantity
    private static final double DEFAULT_NET_VALUE = 0; // default order net value
    private static final int MODULE_CODE = 4;
    private static final String ERROR_NO_HOLDING_RECORD_FOUND = "No Holding record found for the Security Account No: ";
    private static final String ERROR_LOCATING_HOLDING_RECORD = "Error in Locating Holding Record";
    private static final String DAY_ORDER = "Order is a day order";
    private static final String SELL_PENDING_UPDATE = "Update sell pending";
    private static Logger logger = LogManager.getLogger(SellRiskManager.class);
    private AccountManager accountManager;
    private HoldingManager holdingManager;
    private CustomerManager customerManager;
    private EquityRmsUtil equityRmsUtil;
    private CommonUtil commonUtil;
    private CashTradingManager cashTradingManager;
    private SellRiskManagerUtil sellRiskManagerUtil;
    private OrderManager orderManager;
    private CashManager cashManager;


    public EquityRiskManagementReply processNewOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the Sell order -{}", order);
        EquityRiskManagementReply reply;
        String errMsgParameterList = null;
        int lotSize = 1;
        try {
            //Getting the customer account
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            //Getting the cash account
            CashAccount customerCashAccount = cashManager.getCashAccount(customerAccount.getCashAccNumber());
            //getting the price block for the order. If it is a market order need to get a approximate price for risk management. If it is
            //a limit order take the customer setting price as price block
            double priceBlk = equityRmsUtil.getPriceBlock(order.getInstitutionCode(), order.getChannel(), order.getType(), order.getPrice(), order.getSymbol(), order.getExchange());
            logger.info("Price block for the order - {}", priceBlk);
            order.setPriceBlock(priceBlk);
            //setting the settle currency
            order.setSettleCurrency(customerCashAccount.getCurrency());
            //calculate the order value
            double orderValue = order.getQuantity() * priceBlk;
            logger.info("Order Value -{}", orderValue);
            order.setOrderValue(orderValue);

            //getting the commission for the order
            ExchangeAccount exchangeAccount = customerAccount.getExchangeAccountsList().get(order.getExchange());
               double orderCommission =10;


            order.setCommission(orderCommission);
            logger.info("Commission for the order -{}", orderCommission);
            double netValue = orderValue - orderCommission;
            logger.info("net value of the order -{}", netValue);
            order.setNetValue(netValue);

            // check negative net order value is allowed for the customer

            //negative net value operation is allowed in the institution
            if (netValue < DEFAULT_NET_VALUE) {
                //order net value is negative hence need to check the customer buying power
                //get the order issue settle rate
                double issueSettleRate = 1;
                order.setIssueSettleRate(issueSettleRate);

                //validate the risk for new sell order
                CashTradingReply cashTradingReply = cashTradingManager.validateRiskForNewSellOrder(order);
                if (!cashTradingReply.isSuccess()) {
                    order.setText("Not enough funds for the customer account");
                    return new EquityRiskManagementReplyBean(false, cashTradingReply.getRejectCode(), cashTradingReply.getRejectReason(),
                            cashTradingReply.getModuleCode(), cashTradingReply.getErrMsgParameterList());
                }
            }

            //start the stock validations for sell order below
            HoldingKey holdingKey = null;
            HoldingRecord holdingRecord;
            logger.info("Updating sell pending for new sell order");
            try {
                //create holding key
                holdingKey = holdingManager.getEmptyHoldingKey(customerAccount.getCustomerNumber(), order.getExchange(), order.getSymbol(), customerAccount.getAccountNumber());
                //get holding record from the holding key
                holdingRecord = holdingManager.getHoldingRecord(holdingKey);
            } catch (Exception e) {
                //check whether this is a custodian account in that case no need to check the stock at customer level.
                //custodian customer can sell the stock from any broker
                if (accountManager.isCustodianAccount(customerAccount.getAccountNumber(), order.getExchange())) {
                    holdingRecord = holdingManager.getEmptyHoldingRecord(holdingKey);
                } else {
                    logger.error(ERROR_NO_HOLDING_RECORD_FOUND + customerAccount.getAccountNumber());
                    return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.HOLDING_RECORD_NOT_FOUND.getCode(),
                            ERROR_LOCATING_HOLDING_RECORD, MODULE_CODE, errMsgParameterList);
                }
            }
            //validate for square off orders
            if (order.getType() == OrderType.SQUARE_OFF && order.isDayOrder()) {
                logger.info("Order type is Square Off and order is day order");
                double netDayQty = holdingRecord.getNetDayHolding() - order.getQuantity();
                logger.debug("Net Day Qty - {}", netDayQty);
                if (netDayQty < DEFAULT_QUANTITY) {
                    return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.ORDER_QTY_EXCEED_NET_DAY_HOLDING.getCode(),
                            "Order quantity exceeds the net day holdings", MODULE_CODE, errMsgParameterList);
                }
            }
            boolean checkStocks = true;
            //if trading account is fully custody no need to do the stock validation before sending the order to exchange
            if (accountManager.isCustodianAccount(customerAccount.getAccountNumber(), order.getExchange())) {
                checkStocks = false;
            }

            if (holdingRecord == null && checkStocks) {
                //set the error message parameter list when holding record is not found
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.HOLDING_RECORD_NOT_FOUND.getCode(),
                        ERROR_LOCATING_HOLDING_RECORD, MODULE_CODE, errMsgParameterList);
            } else {
                double availableQty;
                if (holdingRecord != null) {
                    //validation for the day order
                    if (order.isDayOrder()) {
                        logger.info(DAY_ORDER);
                        //for day orders need to check customer has enough day holdings to sell
                        if (!sellRiskManagerUtil.isDayHoldingsAvailable(order, holdingRecord) && checkStocks) {
                            //set the error message parameter list when holding record is not found
                            errMsgParameterList = holdingRecord.getNetHolding() + "," + holdingRecord.getPendingHolding() + "," +
                                    holdingRecord.getSellPending() + "," + holdingRecord.getNetDayHolding() + "," + holdingRecord.getPledgeQty();
                            return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.NOT_ENOUGH_STOCKS.getCode(),
                                    "Insufficient stocks!(Owned=" + holdingRecord.getNetHolding() + " Unsettled=" + holdingRecord.getPendingHolding() +
                                            " Sell Pending=" + holdingRecord.getSellPending() + " Day Margin Holdings=" + holdingRecord.getNetDayHolding() + " Pledged=" + holdingRecord.getPledgeQty() + ")", MODULE_CODE, errMsgParameterList);
                        }

                    } else {
                        boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
                        //get the available quantity to sell
                        availableQty = sellRiskManagerUtil.getAvailableQuantityForSell(order.getQuantity(), order.getExchange(), order.getInstitutionCode(), holdingRecord, checkStocks, isFullyDisclose);
                        logger.debug("Available Qty to sell - {}", availableQty);
                        //if the available quantity is less than the order quantity send fail message
                        if (availableQty < order.getQuantity() && checkStocks) { // not enough stocks to sell
                            //set the error message parameter list when holding record is not found
                            errMsgParameterList = holdingRecord.getNetHolding() + "," + holdingRecord.getPendingHolding() + "," +
                                    holdingRecord.getSellPending() + "," + holdingRecord.getNetDayHolding() + "," + holdingRecord.getPledgeQty();
                            return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.NOT_ENOUGH_STOCKS.getCode(),
                                    "Insufficient stocks!(Owned=" + holdingRecord.getNetHolding() + " Unsettled=" + holdingRecord.getPendingHolding() +
                                            " Sell Pending=" + holdingRecord.getSellPending() + " Day Margin Holdings=" + holdingRecord.getNetDayHolding() + " Pledged=" + holdingRecord.getPledgeQty() + " )", MODULE_CODE, errMsgParameterList);
                        }

                    }

                    logger.info(SELL_PENDING_UPDATE);
                    boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
                    //update the sell pending and other stuffs
                    equityRmsUtil.updateSellPending(order.getQuantity(), order, isFullyDisclose, customerAccount);
                    order.setStatus(OrderStatus.VALIDATED);

                } else {
                    logger.error("Holding record not exist and can't be create");
                    throw new EquityRmsException("Holding record not exist");
                }
            }


        } catch (Exception e) {
            logger.error("problem in managing risk for new sell order", e);
            throw new EquityRmsException("problem in managing risk for new sell order", e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }


    public EquityRiskManagementReply processAmendOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the Sell order -{}", order);
        EquityRiskManagementReply reply;
        String errMsgParameterList = null;
        double totalCommission = 0.0;
        int lotSize = 1;
        double origOrderQty;
        try {
            //Getting the customer account
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            //Getting the cash account
            CashAccount customerCashAccount = cashManager.getCashAccount(customerAccount.getCashAccNumber());
            //get the old order from the database
            Order oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());
            origOrderQty = order.getQuantity();
            // set the order qty to new order qty - old order cum qty
            if (order.getChannel() != null) {
                order.setQuantity(order.getQuantity() - oldOrder.getCumQty());
            }

            //getting the price block for the order. If it is a market order need to get a approximate price for risk management. If it is
            //a limit order take the customer setting price as price block
            double priceBlk = equityRmsUtil.getPriceBlock(order.getInstitutionCode(), order.getChannel(), order.getType(), order.getPrice(), order.getSymbol(), order.getExchange());
            logger.info("Price block for the order - {}", priceBlk);
            //setting the settle currency
            order.setSettleCurrency(customerCashAccount.getCurrency());
            double orderValue = order.getQuantity() * priceBlk;
            logger.info("Order Value -{}", orderValue);
            order.setOrderValue(orderValue);

            //getting the commission for the order
            ExchangeAccount exchangeAccount = customerAccount.getExchangeAccountsList().get(order.getExchange());
            totalCommission=10;
            order.setCommission(totalCommission);
            double netValue = orderValue - totalCommission;
            logger.info("net value of the order -{}", netValue);
            order.setNetValue(netValue);


            //start the stock validations for sell order below
            HoldingKey holdingKey;
            HoldingRecord holdingRecord;

            try {
                //create holding key
                holdingKey = holdingManager.getEmptyHoldingKey(customerAccount.getCustomerNumber(), order.getExchange(), order.getSymbol(), customerAccount.getAccountNumber());
                //get holding record from the holding key
                holdingRecord = holdingManager.getHoldingRecord(holdingKey);
            } catch (Exception e) {
                logger.error(ERROR_NO_HOLDING_RECORD_FOUND + customerAccount.getAccountNumber());
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.HOLDING_RECORD_NOT_FOUND.getCode(),
                        ERROR_LOCATING_HOLDING_RECORD, MODULE_CODE, errMsgParameterList);
            }

            boolean checkStocks = true;
            //if trading account is fully custody no need to do the stock validation before sending the order to exchange
            if (accountManager.isCustodianAccount(customerAccount.getAccountNumber(), order.getExchange())) {
                checkStocks = false;
            }

            if (holdingRecord == null && checkStocks) {
                //set the error message parameter list when holding record is not found
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.HOLDING_RECORD_NOT_FOUND.getCode(),
                        ERROR_LOCATING_HOLDING_RECORD, MODULE_CODE, errMsgParameterList);
            } else {
                double availableQty;
                if (holdingRecord != null) {
                    boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
                    //get the available quantity to sell
                    availableQty = sellRiskManagerUtil.getAvailableQuantityForSell(order.getQuantity(), order.getExchange(), order.getInstitutionCode(), holdingRecord, checkStocks, isFullyDisclose);
                    logger.debug("Available Qty to Sell -{}", availableQty);

                    //order quantity difference in the amend order
                    //ex: amend order quantity = 1000, old order quantity = 500, old order filled quantity =100
                    // then diff = 1000 - (500 - 100) = 600
                    double diff = order.getQuantity() - (oldOrder.getQuantity() - oldOrder.getCumQty());
                    logger.info("Quantity diff >> " + diff + "-- availableQty: " + availableQty);

                    //validation for the day order
                    if (order.isDayOrder()) {
                        logger.info(DAY_ORDER);
                        //if it is a day order calculate the available quantity
                        availableQty = sellRiskManagerUtil.getAvailableQtyForDay(holdingRecord);
                        logger.debug("Available Qty to sell - {}", availableQty);
                        if (availableQty < diff && checkStocks) {
                            //set the error message parameters when the available qty is less than 0
                            errMsgParameterList = holdingRecord.getNetHolding() + "," + holdingRecord.getPendingHolding() + "," +
                                    holdingRecord.getSellPending() + "," + holdingRecord.getNetDayHolding() + "," + holdingRecord.getPledgeQty();
                            return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.NOT_ENOUGH_STOCKS.getCode(),
                                    "Insufficient stocks!(Owned=" + holdingRecord.getNetHolding() + " Unsettled=" + holdingRecord.getPendingHolding() +
                                            " Sell Pending=" + holdingRecord.getSellPending() + " Day Margin Holdings=" + holdingRecord.getNetDayHolding() + " Pledged=" + holdingRecord.getPledgeQty() + ")", MODULE_CODE, errMsgParameterList);
                        }
                    }

                    //if the available quantity is less than the order quantity send fail message
                    if (availableQty < diff && checkStocks) { // not enough stocks to sell
                        //set the error message parameters when the available qty is less than 0
                        errMsgParameterList = holdingRecord.getNetHolding() + "," + holdingRecord.getPendingHolding() + "," +
                                holdingRecord.getSellPending() + "," + holdingRecord.getNetDayHolding() + "," + holdingRecord.getPledgeQty();
                        return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.NOT_ENOUGH_STOCKS.getCode(),
                                "Insufficient stocks!(Owned=" + holdingRecord.getNetHolding() + " Unsettled=" + holdingRecord.getPendingHolding() +
                                        " Sell Pending=" + holdingRecord.getSellPending() + " Day Margin Holdings=" + holdingRecord.getNetDayHolding() + " Pledged=" + holdingRecord.getPledgeQty() + ")", MODULE_CODE, errMsgParameterList);
                    } else if (diff > 0) {
                        logger.info(SELL_PENDING_UPDATE);
                        //update the sell pending and other stuffs
                        //if the amend order quantity is grater than old order quantity then need to increase the sell pending
                        equityRmsUtil.updateSellPending(diff, order, isFullyDisclose, customerAccount);
                        order.setStatus(OrderStatus.VALIDATED);
                    } else if (diff <= 0) {
                        order.setStatus(OrderStatus.VALIDATED);
                    }
                }
            }
            order.setQuantity(origOrderQty);
            orderValue = order.getQuantity() * priceBlk;
            order.setCommission(10);
            order.setOrderValue(orderValue);
            //set the required fields taken from the original order
            order.setCumQty(oldOrder.getCumQty());
            order.setAvgPrice(oldOrder.getAvgPrice());
            order.setLastPx(oldOrder.getLastPx());
            order.setLastShares(oldOrder.getLastShares());
            order.setCumExchangeCommission(oldOrder.getCumExchangeCommission());
            order.setCumThirdPartyCommission(oldOrder.getCumThirdPartyCommission());
            order.setNetSettle(oldOrder.getNetSettle());
            order.setParentNetSettle(oldOrder.getParentNetSettle());
            order.setCumOrderValue(oldOrder.getCumOrderValue());
            order.setCumNetValue(oldOrder.getCumNetValue());
            order.setCumNetSettle(oldOrder.getCumNetSettle());
            order.setCumParentCommission(oldOrder.getCumParentCommission());
            order.setCumCommission(oldOrder.getCumCommission());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }

    /**
     * Method contain all the logic related to equity sell order expire and sell order cancel execution
     * This will take the leaves quantity from the current order and remove the sell pending using that value
     *
     * @param order the order bean
     * @return the EquityRiskManagementReply
     * @see EquityRiskManagementReply
     */
    public EquityRiskManagementReply processExpireOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the Sell order Expiration -{}", order);
        EquityRiskManagementReply reply;
        try {
            //Getting the customer account
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());

            double cumQty = order.getCumQty();
            logger.debug("Order expire request. Cumulative Qty - {}", cumQty);
            if (cumQty < 0) {
                //filled quantity cannot be negative
                cumQty = 0;
            }
            logger.info("Update the sell pending");
            boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
            //remove the sell pending
            equityRmsUtil.updateSellPending(-(order.getQuantity() - cumQty), order, isFullyDisclose, customerAccount);

            //can't have values for bellow field when order got expired
            order.setLeavesQty(0);
            order.setBlockAmount(0);
            order.setMarginBlock(0);


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }

        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }

    /**
     * Method contain all the logic related to equity sell order replacement
     * When customer has put a amend order request there will be order replacement message comes to OMS from the exchange.
     * OMS will call this method to do the risk management for the replaced order.
     * <p/>
     * This will remove the sell pending if the current order quantity is less than the old order quantity
     *
     * @param order the order bean
     * @return the EquityRiskManagementReply
     * @see EquityRiskManagementReply
     */
    public EquityRiskManagementReply processReplaceOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the Sell Replaced -{}", order);
        EquityRiskManagementReply reply;

        try {
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            Order oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());
            //set the settlement type

            logger.debug("Order Qty -{}, Old Order Qty -{}", order.getQuantity(), oldOrder.getQuantity());
            //calculate the order quantity difference
            double diff = oldOrder.getQuantity() - order.getQuantity();
            //if old order quantity is greater reduce the qty
            if (diff > 0) {
                boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
                //update the sell pending
                logger.info(SELL_PENDING_UPDATE);
                equityRmsUtil.updateSellPending(-diff, order, isFullyDisclose, customerAccount);
            }
            //populate the order properties from the old order
            sellRiskManagerUtil.populateOrderBean(order, oldOrder);


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }


    public EquityRiskManagementReply processExecuteOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the Sell Execution -{}", order);
        EquityRiskManagementReply reply;

        double dPreOrderValue = 0;
        double preCommission = 0;
        double curParentCommission;
        double parentIssueSettleRate = 0;
        double preParentCommission = 0;
        double instituteDayLimit;
        double curCommission;
        double curExchangeCommission;
        double curThirdPartyCommission;
        double curBrokerCommission;
        double dCurNetValue;
        double dCurNetSettle;
        double dCurParentNetValue;
        double dCurParentNetSettle;
        HoldingKey parentHoldingKey = null;
        HoldingRecord parentHoldingRecord;
        double profitLossParent = 0;
        double profitLoss;
        int lotSize = 1;
        try {
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            //Getting the cash account
            CashAccount customerCashAccount = cashManager.getCashAccount(customerAccount.getCashAccNumber());

            Account parentAccount = null;
            CashAccount parentCashAccount = null;
            //get the exchange account list
            ExchangeAccount exchangeAccount = customerAccount.getExchangeAccountsList().get(order.getExchange());
            //if the account is sub then need to update the parent account as well
            if (order.getParentAccountNumber() != null) {
                //get the required details from the parent account
                parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                Customer parent = customerManager.getCustomerByCustomerNumber(parentAccount.getCustomerNumber());
                parentCashAccount = cashManager.getCashAccount(parentAccount.getCashAccNumber());


                parentIssueSettleRate =1;
                logger.debug("Parent issue settle rate - {}", parentIssueSettleRate);
                try {
                    //create holding key
                    parentHoldingKey = holdingManager.getEmptyHoldingKey(parentAccount.getCustomerNumber(), order.getExchange(), order.getSymbol(), parentAccount.getAccountNumber());
                    //get holding record from the holding key
                    parentHoldingRecord = holdingManager.getHoldingRecord(parentHoldingKey);
                } catch (Exception e) {
                    logger.error(ERROR_NO_HOLDING_RECORD_FOUND + parentAccount.getAccountNumber());
                    //to support fully custody accounts
                    parentHoldingRecord = holdingManager.getEmptyHoldingRecord(parentHoldingKey);
                }
                //calculate the profit loss value for the parent

                profitLossParent = (order.getLastPx() - parentHoldingRecord.getAvgCost()) * order.getLastShares() * parentIssueSettleRate;
                logger.debug("Parent profit loss -{}", profitLossParent);
                preParentCommission = order.getCumParentCommission();// is the cumulative parent commission up to this execution
                logger.debug("Pre parent Commission -{}", preParentCommission);
            }
            double issueSettleRate = 1;
            logger.debug("Customer issue settle rate - {}", issueSettleRate);
            order.setIssueSettleRate(issueSettleRate);
            //get holding record from the holding key
            HoldingRecord holdingRecord = holdingManager.getHoldingRecord(new HoldingKeyBean(customerAccount.getCustomerNumber(), order.getExchange(), order.getSymbol(), customerAccount.getAccountNumber()));
            //calculate the profit loss for the customer
            profitLoss = (order.getLastPx() - holdingRecord.getAvgCost()) * order.getLastShares() * issueSettleRate;
            logger.debug("Customer profit loss -{}", profitLoss);
            order.setProfitLoss(profitLoss);
            order.setParentProfitLoss(profitLossParent);

            double preExchangeCommission = 0;
            double preThirdPartyCommission = 0;
            double preBrokerCommission = 0;
            //Calculate new transaction values
            if (order.getCumQty() != 0) {  //for partially filled orders
                logger.info("Order Cum Qty is not equal to zero");
                dPreOrderValue = order.getCumOrderValue();    // cumulative order value
                preCommission = order.getCumCommission();     // cumulative commission
                preExchangeCommission = order.getCumExchangeCommission();  // cumulative exchange commission
                preThirdPartyCommission = order.getCumThirdPartyCommission(); //cumulative third party commission
                preBrokerCommission = order.getCumBrokerCommission(); //cumulative broker commission
                logger.debug("dPreOrderValue -{}, preCommission -{}, preExchangeCommission-{}, preThirdPartyCommission -{}, preBrokerCommission -{}",
                        dPreOrderValue, preCommission, preExchangeCommission, preThirdPartyCommission, preBrokerCommission);
            }
            //dCurOrderValue is cumulative order value + order value of the current execution
            double dCurOrderValue = dPreOrderValue + (order.getLastPx() * order.getLastShares());
            logger.debug("Current order value - {}", dCurOrderValue);
            order.setOrderValue(dCurOrderValue);
            order.setCumOrderValue(dCurOrderValue);
            curCommission = 10;
            curParentCommission = 10;
            curExchangeCommission = 5;

           // curCommission = commission.getCommission();
            curThirdPartyCommission = 2;
            //curParentCommission = commission.getParentCommission();
            curBrokerCommission = 5;

            order.setCommission(curCommission);
            order.setCumCommission(curCommission);

            dCurNetValue = dCurOrderValue - curCommission;  // net value os the order value - commission
            logger.debug("Current net value -{}", dCurNetValue);
            order.setNetValue(dCurNetValue);
            order.setCumNetValue(dCurNetValue);

            order.setTransactionFee(0); //need to set the transaction fee correctly
            dCurNetSettle = dCurNetValue * issueSettleRate; //net settle value up to this execution
            logger.debug("Current net settle -{}", dCurNetSettle);
            order.setNetSettle(dCurNetSettle);
            order.setCumNetSettle(dCurNetSettle);

            dCurParentNetValue = dCurOrderValue - curParentCommission;
            logger.debug("Current Parent net value - {}", dCurParentNetValue);
            double orderValueDiff = order.getLastPx() * order.getLastShares();  // only for this execution
            double commissionDiff = curCommission - preCommission; // commission only for this execution
            double netValueDiff = orderValueDiff - commissionDiff;  //net value only for this execution
            double netSettleDiff = netValueDiff * issueSettleRate;  //only for this execution
            double parentNetValueDiff = orderValueDiff - (curParentCommission - preParentCommission);   //only for this execution
            double parentNetSettleDiff = parentNetValueDiff * parentIssueSettleRate;    //parent net settle diff only for this execution
            double exchangeCommissionDiff = curExchangeCommission - preExchangeCommission;  //exchange commission diff only for this execution
            double thirdPartyCommissionDiff = curThirdPartyCommission - preThirdPartyCommission;  //third party commission diff only for this execution
            double brokerCommissionDiff = curBrokerCommission - preBrokerCommission;  //broker commission diff only for this execution
            logger.debug("orderValueDiff -{}, commissionDiff -{}, netValueDiff -{}, netSettleDiff -{}, parentNetValueDiff -{}, parentNetSettleDiff -{}," +
                    "exchangeCommissionDiff -{}, thirdPartyCommissionDiff -{}, brokerCommissionDiff -{}", orderValueDiff, commissionDiff, netValueDiff,
                    netSettleDiff, parentNetValueDiff, parentNetSettleDiff, exchangeCommissionDiff, thirdPartyCommissionDiff, brokerCommissionDiff);
            //set the required properties to the order
            order.setNetSettleDiff(netSettleDiff);
            order.setNetValueDiff(netValueDiff);
            order.setCommissionDiff(commissionDiff);
            order.setParentNetSettleDiff(parentNetSettleDiff);
            order.setParentNetValueDiff(parentNetValueDiff);
            order.setExchangeCommDiff(exchangeCommissionDiff);
            order.setThirdPartyCommDiff(thirdPartyCommissionDiff);
            order.setBrokerCommDiff(brokerCommissionDiff);
            order.setOrderValueDiff(orderValueDiff);
            order.setParentCommDiff(curParentCommission - preParentCommission);

            //for sub account calculate the parent net settle
            if (order.getParentAccountNumber() != null) {
                dCurParentNetSettle = dCurParentNetValue * parentIssueSettleRate;
                logger.debug("Current Parent Net settle -{}", dCurParentNetSettle);
                order.setParentNetSettle(dCurParentNetSettle);
            }
            //calculate spread for the customer account

            String narration = order.getExecID() + ": PARTIAL/FULL SELL EXECUTION " + order.getSymbol() + " Quantity " + order.getLastShares() + " @" + order.getLastPx();
            //if the account is a sub one. need to update the parent account as well
            if (order.getParentAccountNumber() != null && parentAccount != null) {


                //update the holding parent holding record
                equityRmsUtil.updateHolding(parentAccount.getCustomerNumber(), parentAccount.getAccountNumber(), order, false, dCurNetValue, dCurNetSettle, -order.getLastShares(), true, narration, false);
            }
            //set required parameters that is required to be add the database
            order.setCumParentCommission(curParentCommission);

                order.setCumBrokerCommission(curBrokerCommission);
                order.setCumThirdPartyCommission(curThirdPartyCommission);

            order.setNetValue(order.getCumNetValue());
            order.setNetSettle(order.getCumNetSettle());
            order.setOrderValue(order.getCumOrderValue());

            //update the holding for the customer
            boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
            equityRmsUtil.updateHolding(customerAccount.getCustomerNumber(), customerAccount.getAccountNumber(), order, false, dCurNetValue, dCurNetSettle, -order.getLastShares(), false, narration, isFullyDisclose);

            //remove sell pending since order is filled
            equityRmsUtil.updateSellPending(-order.getLastShares(), order, isFullyDisclose, customerAccount);



            CashTradingReply riskManageReply = cashTradingManager.recordSellOrderExecution(order);



        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }


    public EquityRiskManagementReply processChangeReject(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the Sell Change Reject -{}", order);
        EquityRiskManagementReply reply;
        try {
            //Getting the customer account
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            //get the original order and expire it
            Order oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());

            logger.info("Order Qty - {}, Old Order Qty -{}", order.getQuantity(), oldOrder.getQuantity());
            logger.info("order.getClOrdID - {} order.getOrigClOrdID() -{}", order.getClOrdID(), order.getOrigClOrdID());
            double diff = order.getQuantity() - oldOrder.getQuantity();
            //if old order quantity is greater reduce the qty
            if (diff > 0) {
                boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
                //remove sell pending if order quantity is greater than the old order quantity
                equityRmsUtil.updateSellPending(-diff, order, isFullyDisclose, customerAccount);
            }


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }



    /**
     * Inject the Cash Manager
     *
     * @param cashManager is the cash manager
     */
    public void setCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
    }

    /**
     * Inject the Order Manager
     *
     * @param orderManager is the order manager
     */
    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    /**
     * Inject the Sell Order Helper Bean
     *
     * @param sellRiskManagerUtil is the sell risk manager utility class
     */
    public void setSellRiskManagerUtil(SellRiskManagerUtil sellRiskManagerUtil) {
        this.sellRiskManagerUtil = sellRiskManagerUtil;
    }

    /**
     * Inject the Cash Trading Manager
     *
     * @param cashTradingManager is the cash trading manager
     */
    public void setCashTradingManager(CashTradingManager cashTradingManager) {
        this.cashTradingManager = cashTradingManager;
    }

    /**
     * Inject the Utility class contains the equity risk management helper methods
     *
     * @param equityRmsUtil is the equity rms utility class
     */
    public void setEquityRmsUtil(EquityRmsUtil equityRmsUtil) {
        this.equityRmsUtil = equityRmsUtil;
    }

    /**
     * Inject the Customer Manager
     *
     * @param customerManager is the customer manager
     */
    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    /**
     * Inject the Common Utility Class
     *
     * @param commonUtil is the common utility class
     */
    public void setCommonUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    /**
     * Inject the Account Manager
     *
     * @param accountManager is the account manager
     */
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * Inject the Holding Manager
     *
     * @param holdingManager is the holding manager
     */
    public void setHoldingManager(HoldingManager holdingManager) {
        this.holdingManager = holdingManager;
    }


}
