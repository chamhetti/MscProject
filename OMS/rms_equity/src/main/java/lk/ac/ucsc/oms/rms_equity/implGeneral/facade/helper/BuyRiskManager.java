package lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper;

import lk.ac.ucsc.oms.cash_trading.api.CashTradingManager;
import lk.ac.ucsc.oms.cash_trading.api.CashTradingReply;
import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskFailReasons;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagementReply;
import lk.ac.ucsc.oms.rms_equity.api.exceptions.EquityRmsException;
import lk.ac.ucsc.oms.rms_equity.implGeneral.bean.EquityRiskManagementReplyBean;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util.CommonUtil;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util.EquityRmsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BuyRiskManager {
    private static Logger logger = LogManager.getLogger(BuyRiskManager.class);

    private static final int MODULE_CODE = 3;
        private CashTradingManager cashTradingManager;
    private OrderManager orderManager;
    private EquityRmsUtil equityRmsUtil;
    private AccountManager accountManager;
    private CashManager cashManager;
    private static final String CURRENCY_RATES_NOT_DEFINED = "Currency rates has not define correctly. Issue Currency, Settle Currency = ";
    private static final String INVALID_PRICE_BLOCK = "Invalid price block";


    /**
     * Default Constructor of the BuyRiskManager
     */
    public BuyRiskManager() {
    }


    public EquityRiskManagementReply processNewOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the order -{}", order);
        EquityRiskManagementReply reply;
        String errorMsgParameterList = null;

        //Getting the customer account
        Account customerAccount;
        try {
            customerAccount = accountManager.getAccount(order.getSecurityAccount());

            //Getting the cash account
            CashAccount customerCashAccount = cashManager.getCashAccount(customerAccount.getCashAccNumber());
            //getting the price block for the order. If it is a market order need to get a approximate price for risk management. If it is
            //a limit order take the customer setting price as price block
            double priceBlk = equityRmsUtil.getPriceBlock(order.getInstitutionCode(), order.getChannel(), order.getType(), order.getPrice(), order.getSymbol(), order.getExchange());
            logger.debug("Price block for the order - {}", priceBlk);
            //invalid price block hence order will not process further
            if (priceBlk <= 0) {
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.INVALID_PRICE_BLOCK.getCode(), INVALID_PRICE_BLOCK, MODULE_CODE, errorMsgParameterList);
            }
            order.setPriceBlock(priceBlk);
            //setting the settle currency
            order.setSettleCurrency(customerCashAccount.getCurrency());
            double orderValue = order.getQuantity() * priceBlk;

            logger.debug("Order Value - {}", orderValue);
            order.setOrderValue(orderValue);

            double totalCommission = 10;

            //if the total commission <0 then reject the order
            if (totalCommission < 0) {
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.COMMISSION_NOT_DEFINED.getCode(), "Commission Not Defined", MODULE_CODE, errorMsgParameterList);
            }
            //set the total commission of the order this include all the commission taken for this order
            order.setCommission(totalCommission);
            logger.debug("Commission for the order -{}", totalCommission);
            //calculate order net value that that order value plus all the commission related to this order
            double netValue = orderValue + totalCommission;

            logger.debug("Net Value of the order -{}", netValue);
            //issue settle rate calculated based on the symbol currency and the settle currency of the cash account
            double issueSettleRate = 1;
            if (issueSettleRate <= 0) {
                errorMsgParameterList = order.getCurrency() + "," + order.getSettleCurrency();
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.CURRENCY_RATE_NOT_DEFINED.getCode(),
                        CURRENCY_RATES_NOT_DEFINED + order.getCurrency() + "," + order.getSettleCurrency(), MODULE_CODE, errorMsgParameterList);
            }
            //set the issue settle rate to order
            order.setIssueSettleRate(issueSettleRate);
            //calculate the net settle of the order. net order value from customer cash account currency
            double netSettle = netValue * issueSettleRate;

            logger.debug("net settle -{}", netSettle);
            order.setNetSettle(netSettle);
            order.setNetValue(netValue);
            order.setBlockAmount(netSettle);

            logger.debug("Start validating cash, margin and day margin of the customer");
            //check whether customer has enough buying power for this buy order. If so block the cash accordingly

                    //customers is not a margin enable one. Hence need to process as a one who doing the trading only using cash and trading limit
                    CashTradingReply riskManageReply = cashTradingManager.validateRiskForNewBuyOrder(order);
                    if (!riskManageReply.isSuccess()) {
                        order.setStatus(OrderStatus.REJECTED);
                        order.setText(riskManageReply.getRejectReason());
                        return new EquityRiskManagementReplyBean(false, riskManageReply.getRejectCode(), riskManageReply.getRejectReason(),
                                riskManageReply.getModuleCode(), riskManageReply.getErrMsgParameterList());
                    }

            logger.debug("End validating cash, margin and day margin of the customer");

            //keep the order at OMS side until approve or reject by a authorized person in the case of risk management fail
            if (order.getStatus() == OrderStatus.WAITING_FOR_APPROVAL) {
                logger.debug("Order is wait for approval by dealer to send to market");
                reply = new EquityRiskManagementReplyBean(true);
                return reply;
            }

            //Customer has enough buying power for this order.
            //update the customer buy pending
            logger.info("Updating the buy pending for the order: " + order.getQuantity());
            equityRmsUtil.updateBuyPending(order.getCustomerNumber(), order.getExchange(), order.getSymbol(), order.getSecurityAccount(), order.getQuantity(), order.getInstrumentType(), customerAccount.getInstituteCode());


        } catch (Exception e) {
            throw new EquityRmsException(e.getMessage(), e);
        }

        reply = new EquityRiskManagementReplyBean(true);
        return reply;

    }

    /**
     * Method contain all the logic related to equity buy order amend
     * This will check customer has sufficient buying power to when amend the order
     * Order value difference validate with the available buying power. (diff = order net Settle - old Order Total Blk)
     * If the customer has a parent account, order net settle will be blocked and buy pending
     * will be updated in the parent account as well
     *
     * @param order the order bean
     * @return the EquityRiskManagementReply
     * @see EquityRiskManagementReply
     */
    public EquityRiskManagementReply processAmendOrder(Order order) throws EquityRmsException {
        logger.info("Doing the risk management for the order -{}", order);
        EquityRiskManagementReply reply;
        String errorMsgParameterList = null;
        try {
            //Getting the customer account
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            //Getting the cash account
            CashAccount customerCashAccount = cashManager.getCashAccount(customerAccount.getCashAccNumber());
            //getting the price block for the order. If it is a market order need to get a approximate price for risk management. If it is
            //a limit order take the customer setting price as price block
            double priceBlk = equityRmsUtil.getPriceBlock(order.getInstitutionCode(), order.getChannel(), order.getType(), order.getPrice(), order.getSymbol(), order.getExchange());
            logger.info("Price block for the order - {}", priceBlk);
            //invalid price block hence order will not process further
            if (priceBlk <= 0) {
                return new EquityRiskManagementReplyBean(false, EquityRiskFailReasons.INVALID_PRICE_BLOCK.getCode(), INVALID_PRICE_BLOCK, MODULE_CODE, errorMsgParameterList);
            }
            order.setPriceBlock(priceBlk);
            //setting the settle currency
            order.setSettleCurrency(customerCashAccount.getCurrency());
            //get the old order using the original clord id
            Order oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());
            //remain quantity is the amend order quantity - old order filled quantity
            double remainQty = order.getQuantity() - oldOrder.getCumQty();
            //order value is calculated based on the remaining quantity
            double orderValue = remainQty * priceBlk;
            logger.info("Order Value - {}", orderValue);
            //getting the commission for the order
            double totalCommission = 10;


            logger.info("Commission for the order -{}", totalCommission);
            order.setCommission(totalCommission);
            //order net value is order value + commission
            double netValue = orderValue + totalCommission;
            logger.info("Net Value of the Order -{}", netValue);
            //calculate the net settle of the order
            //issue currency is the symbol currency and settle currency is the currency of the customer cash account
            double issueSettleRate = 1 ;
            order.setIssueSettleRate(issueSettleRate);
            //order net settle is the order net value multiplied by issue settle rate
            double netSettle = netValue * issueSettleRate;
            logger.info("Order net settle -{}", netSettle);
            order.setNetSettle(netSettle);
            //old order total block  = old order block amount + old order margin block
            double oldOrderTotalBlk = oldOrder.getBlockAmount() + oldOrder.getMarginBlock();
            logger.info("Old Order Total Block Amount -{} ", oldOrderTotalBlk);
            //set the block amount of the amend order taken from the old order
            order.setBlockAmount(oldOrder.getBlockAmount());
            order.setParentBlockAmount(oldOrder.getParentBlockAmount());
            order.setMarginBlock(oldOrder.getMarginBlock());
            //parent order information
            double parentIssueSettleRate = 1;

            //order net settle greater than the old order total block
            if (netSettle > oldOrderTotalBlk) {
                //difference = amend order value - old order total block
                //so this is the amount that need to settle in the amend order
                //ex: net settle = 1000, old order block = 200
                //amend order value difference  = 800
                double diff = netSettle - oldOrderTotalBlk;
                //parent diff is the order net value * parent settle rate - old order parent block amount
                double parentDiff = netValue * parentIssueSettleRate - oldOrder.getParentBlockAmount();
                order.setParentNetSettle(netValue * parentIssueSettleRate);
                logger.info("Order Value Difference - {}. Parent Value Difference - {}", diff, parentDiff);
                //check whether customer has enough buying power for this buy order. If so block the cash accordingly

                //customers is not a margin enable one. Hence need to process as a one who doing the trading only using cash and trading limit
                CashTradingReply riskManageReply = cashTradingManager.validateRiskForAmendOrder(order, diff, parentDiff);
                if (!riskManageReply.isSuccess()) {
                    order.setStatus(OrderStatus.REJECTED);
                    order.setText(riskManageReply.getRejectReason());
                    return new EquityRiskManagementReplyBean(false, riskManageReply.getRejectCode(), riskManageReply.getRejectReason(), riskManageReply.getModuleCode(), riskManageReply.getErrMsgParameterList());
                }


            } else {
                // old order margin block > 0 so have to process the order for setting new cash and margin block for the order

                    order.setBlockAmount(netSettle);

                if (order.getParentAccountNumber() != null) {
                    order.setParentBlockAmount(netValue * parentIssueSettleRate);
                    order.setParentNetSettle(netValue * parentIssueSettleRate);
                }
            }
            //Customer has enough buying power for this order.
            //update the customer buy pending
            if (order.getQuantity() > oldOrder.getQuantity()) {
                double qtyDiff = order.getQuantity() - oldOrder.getQuantity();
                logger.info("Updating Buy pending. Order Quantity difference in the amend order - {}", qtyDiff);
                //update the customer buy pending
                equityRmsUtil.updateBuyPending(order.getCustomerNumber(), order.getExchange(), order.getSymbol(), order.getSecurityAccount(), qtyDiff, order.getInstrumentType(), customerAccount.getInstituteCode());
            }
            //update order info for total order
            //update the Amend order information
            orderValue = order.getQuantity() * priceBlk;
            //need to calculate commission for amend order
            order.setCommission(10);
            netValue = orderValue + 10;   // order value + commission for the amend order
            netSettle = netValue * issueSettleRate;  //net value in customer cash account currency
            order.setOrderValue(orderValue);
            order.setNetSettle(netSettle);
            order.setNetValue(netValue);
            order.setBlockAmount(netSettle);
            order.setCumOrderValue(oldOrder.getCumOrderValue());
            order.setCumNetValue(oldOrder.getCumNetValue());


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;

    }


    public EquityRiskManagementReply processExpireOrder(Order order) throws EquityRmsException {
        EquityRiskManagementReply reply;
        try {
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());

                    cashTradingManager.recordOrderExpiry(order);

            //need to remove the buy pending value for order expiry
            double removeQty;
            if (order.getCumQty() > 0) {   //if the order is partially filled
                //remove quantity = order quantity - filled quantity
                removeQty = order.getQuantity() - order.getCumQty();
            } else { //remove the total order quantity
                removeQty = order.getQuantity();
            }

            //remove the customer buy pending
            equityRmsUtil.updateBuyPending(order.getCustomerNumber(), order.getExchange(), order.getSymbol(), order.getSecurityAccount(), -removeQty, order.getInstrumentType(), customerAccount.getInstituteCode());
            //Can't have values for bellow when the order got expired
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


    public EquityRiskManagementReply processReplaceOrder(Order order) throws EquityRmsException {
        EquityRiskManagementReply reply;
        try {
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            Order oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());
            double currentBlockAmount = order.getBlockAmount() + order.getMarginBlock();
            double oldBlockAmount = oldOrder.getBlockAmount() + oldOrder.getMarginBlock();
            logger.info("Order Replace request. Current Block Amount- {}, Old Block Amount -{}", currentBlockAmount, oldBlockAmount);


            cashTradingManager.recordOrderReplacement(order, oldOrder);

            logger.info("Order Replace Request. Order Quantity - {}. Old Order Qty -{}", order.getQuantity(), oldOrder.getQuantity());
            //remove the buy pending
            if (order.getQuantity() < oldOrder.getQuantity()) {
                double removeQty = oldOrder.getQuantity() - order.getQuantity();
                //update the customer buy pending
                equityRmsUtil.updateBuyPending(order.getCustomerNumber(), order.getExchange(), order.getSymbol(), order.getSecurityAccount(), -removeQty, order.getInstrumentType(), customerAccount.getInstituteCode());


            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }
        reply = new EquityRiskManagementReplyBean(true);
        return reply;
    }



    public EquityRiskManagementReply processExecuteOrder(Order order) throws EquityRmsException {
        logger.info("Doing Risk Management for the Buy Execution");
        EquityRiskManagementReply reply;
        try {
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            double curNetValue;
            double curNetSettle;
            double curParentNetSettle = 0.0;
            double netValueDiff;
            double netSettleDiff;
            double parentNetSettleDiff = 0;
            //calculate the order value for this execution
            double orderValueDiff = order.getLastPx() * order.getLastShares();
            logger.info(" orderValueDiff -{}", orderValueDiff);
            //get the total order value with current execution
            double curOrderValue = order.getCumOrderValue() + orderValueDiff;
            logger.info("curOrderValue -{}", curOrderValue);
            order.setOrderValue(curOrderValue);
            //get the exchange account list

            double curCommission = 10;

            double parentNetValueDiff = 0;
            //current net value of the order that is net value for total executed quantity
            curNetValue = curOrderValue + curCommission;
            //get the customer cash account
            //get the issue settle rate base of customer cash account currency
            double issueSettleRate = 1;
            //calculate the net settle. that is order net value in customer cash account currency
            curNetSettle = curNetValue * issueSettleRate;
            //net value only for this execution
            netValueDiff = curNetValue - order.getCumNetValue();
            //net settle only for this execution
            netSettleDiff = curNetSettle - order.getCumNetSettle();
            logger.info("curNetValue -{} curNetSettle-{} netValueDiff-{} netSettleDiff-{}", curNetValue, curNetSettle, netValueDiff, netSettleDiff);
            //calculate spread for the customer account

            double issueSettleRateParent = 1;
            double curParentCommission =10;
            //if the account is a sub one. need to update the parent account as well
            if (order.getParentAccountNumber() != null) {

                             order.setIssueSettleRate(issueSettleRate);
                logger.debug("issueSettleRateParent -{}", issueSettleRateParent);
                //current parent net value of the order
                parentNetValueDiff = (curOrderValue - order.getCumOrderValue() +  - order.getCumParentCommission());
                //net value in parent cash account currency
                parentNetSettleDiff = parentNetValueDiff * issueSettleRateParent;
                //current net settle of the parent
                curParentNetSettle = (curOrderValue + curParentCommission) * issueSettleRateParent;

                logger.info("parentNetValueDiff - {} parentNetSettleDiff- {}, curParentNetSettle - {}", parentNetValueDiff, parentNetSettleDiff, curParentNetSettle);

            }
            //commission for this execution
            double commissionDiff = curCommission - order.getCumCommission();
            //exchange commission for this execution
            double exchangeCommDiff = 5;

            double thirdPartyComDiff =2;
            //broker commission for this execution
            double brokerCommDiff = 5;
            //parent broker commission for this execution
            double parentComDiff = curParentCommission - order.getCumParentCommission();
            logger.info("commissionDiff -{} exchangeCommDiff-{} thirdPartyComDiff-{} brokerCommDiff-{} parentComDiff-{}", commissionDiff, exchangeCommDiff, thirdPartyComDiff, brokerCommDiff, parentComDiff);
            //setting the values in order object to update them to various accounts.
            order.setOrderValueDiff(-orderValueDiff);
            order.setNetSettleDiff(-netSettleDiff);
            order.setNetValueDiff(-netValueDiff);
            order.setNetSettle(netSettleDiff);
            order.setParentNetValueDiff(-parentNetValueDiff);
            order.setParentNetSettleDiff(-parentNetSettleDiff);
            order.setCommissionDiff(commissionDiff);
            order.setExchangeCommDiff(exchangeCommDiff);
            order.setThirdPartyCommDiff(thirdPartyComDiff);
            order.setBrokerCommDiff(brokerCommDiff);
            order.setParentCommDiff(parentComDiff);
            order.setIssueSettleRate(issueSettleRate);
            //doing the block for remaining qty
            double leaveQty = order.getQuantity() - order.getCumQty();
            logger.debug("leaveQty -{}", leaveQty);
            double newNetSettle = 0;
            double newParentNetSettle = 0;
            double priceBlk = order.getPriceBlock();
            if (leaveQty > 0) {
                //order is not totally filled hence need to do the block for remaining qty
                priceBlk = equityRmsUtil.getPriceBlock(order.getInstitutionCode(), order.getChannel(), order.getType(), order.getPrice(), order.getSymbol(), order.getExchange());
                double newOrderValue = priceBlk * leaveQty;
                logger.info("newOrderValue -{} price block -{}", newOrderValue, priceBlk);
                //calculate commission for remaining

                double newCommission = 10;
                double newNetValue = newOrderValue + newCommission;
                newNetSettle = newNetValue * issueSettleRate;
                newParentNetSettle = newNetValue * issueSettleRateParent;
                logger.debug("newNetValue -{} newNetSettle-{} newParentNetSettle-{}", newNetValue, newNetSettle, newParentNetSettle);
            }


            CashTradingReply riskManageReply = cashTradingManager.recordBuyOrderExecution(order, newNetSettle, newParentNetSettle);

            //set the values to order to save to DB
            order.setCumNetSettle(curNetSettle);
            order.setCumOrderValue(curOrderValue);
            order.setCumCommission(curCommission);
            order.setCommission(curCommission);
            order.setCumNetValue(curNetValue);
            order.setCumParentCommission(curParentCommission);
            order.setCumExchangeCommission(5);
            order.setParentNetSettle(curParentNetSettle);
                order.setCumBrokerCommission(brokerCommDiff);
                order.setCumThirdPartyCommission(thirdPartyComDiff);

            order.setNetValue(order.getCumNetValue());
            order.setNetSettle(order.getCumNetSettle());
            order.setOrderValue(order.getCumOrderValue());
            order.setPriceBlock(priceBlk);

            String narration = order.getExecID() + ": PARTIAL/FULL BUY EXECUTION " + order.getSymbol() + " Quantity " + order.getLastShares() + " @" + order.getLastPx();
            //update the holdings of the sub account
            if (order.getParentAccountNumber() != null) {
                logger.info("updating holding of the parent");
                //Need to update the parent buy pending. At the movement it is assume that customer has only one parent. But
                //this OMS has design to support any number of ancestors.
                Account parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                equityRmsUtil.updateHolding(parentAccount.getCustomerNumber(), order.getParentAccountNumber(), order, false, orderValueDiff, netSettleDiff, order.getLastShares(), true, narration, false);
            }
            boolean isFullyDisclose = accountManager.isFullyDiscloseAccount(customerAccount.getAccountNumber(), order.getExchange());
            logger.info("updating holding of the customer");
            //remove the buy pending
            equityRmsUtil.updateBuyPending(order.getCustomerNumber(), order.getExchange(), order.getSymbol(), order.getSecurityAccount(), -order.getLastShares(), order.getInstrumentType(), customerAccount.getInstituteCode());
            //update the holding
            equityRmsUtil.updateHolding(order.getCustomerNumber(), order.getSecurityAccount(), order, false, orderValueDiff, netSettleDiff, order.getLastShares(), false, narration, isFullyDisclose);

            //we have to adjust block amount if there is amend order request for the original order
            if (order.getIntermediateStatus() == OrderStatus.SEND_TO_OMS_AMEND) {
                logger.debug("Order already has send for amend. Updating the amend order accordingly");
                equityRmsUtil.updateAmendOrderDetails(order);
            }
            //values are reset to original values instead of negative value
            order.setOrderValueDiff(orderValueDiff);
            order.setNetSettleDiff(netSettleDiff);
            order.setNetValueDiff(netValueDiff);
            order.setParentNetValueDiff(parentNetValueDiff);
            order.setParentNetSettleDiff(parentNetSettleDiff);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }

        reply = new EquityRiskManagementReplyBean(true);
        return reply;
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
     * Inject the Cash Manager
     *
     * @param cashManager is the cash manager
     */
    public void setCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
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
     * Inject the Cash Trading Manager
     *
     * @param cashTradingManager is the cash trading manager
     */
    public void setCashTradingManager(CashTradingManager cashTradingManager) {
        this.cashTradingManager = cashTradingManager;
    }


    /**
     * Inject the Order Manager
     *
     * @param orderManager is the order management facade
     */
    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }


}
