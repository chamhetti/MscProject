package lk.ac.ucsc.oms.cash_trading.implGeneral.facade;

import lk.ac.ucsc.oms.cash_trading.api.CashTradingFailReasons;
import lk.ac.ucsc.oms.cash_trading.api.CashTradingManager;
import lk.ac.ucsc.oms.cash_trading.api.CashTradingReply;
import lk.ac.ucsc.oms.cash_trading.api.exceptions.CashTradingException;
import lk.ac.ucsc.oms.cash_trading.implGeneral.bean.CashTradingReplyBean;
import lk.ac.ucsc.oms.cash_trading.implGeneral.facade.helper.CashTradingUtil;
import lk.ac.ucsc.oms.common_utility.api.formatters.DecimalFormatterUtil;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CashTradingManagerGeneral implements CashTradingManager {
    private static final double DEFAULT_NET_VALUE = 0;
    private static final int MODULE_CODE = 1;
    private static final String QUANTITY = " QTY ";

    private Logger logger = LogManager.getLogger(CashTradingManagerGeneral.class);
    private AccountManager accountManager;
    private CashManager cashManager;
    private CashTradingUtil cashTradingUtil;

    @Override
    public CashTradingReply validateRiskForNewBuyOrder(Order order) throws CashTradingException {
        String errorMsgParameterList;
        try {
            double parentNetSettle = 0;  // is the (order value + commission) multiplied by the settle rate of the parent currency
            Account parentAccount = null;
            if (order.getParentAccountNumber() != null) {   // if the parent account number is not null need to the validations
                parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                double parentBuyingPower = cashTradingUtil.getBuyingPower(parentAccount.getCashAccNumber());
                logger.debug("Parent buying power - {}", parentBuyingPower);
                order.setParentNetSettle(order.getNetSettle());
                if (parentNetSettle <= parentBuyingPower) {
                    logger.info("Customer has enough buying power hence allow to proceed");
                } else {
                    //set the error message parameter list  when the parent buying power is less than the order net settle
                    if (parentBuyingPower < 0) {
                        parentBuyingPower = 0;
                    }
                    errorMsgParameterList = DecimalFormatterUtil.round(parentBuyingPower, 5) + "," + DecimalFormatterUtil.round(parentNetSettle, 5);
                    return new CashTradingReplyBean(false, CashTradingFailReasons.NOT_ENOUGH_PARENT_BUYING_POWER.getCode(),
                            "Not Enough buying power for parent. Buying Power= " + DecimalFormatterUtil.round(parentBuyingPower, 5) + " Net Settle= " + DecimalFormatterUtil.round(parentNetSettle, 5), MODULE_CODE, errorMsgParameterList);
                }
            }

            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            double buyingPower = cashTradingUtil.getBuyingPower(customerAccount.getCashAccNumber());
            logger.debug("Customer Buying Power: " + buyingPower);

            if (order.getNetSettle() <= buyingPower) {
                logger.info("Customer has enough buying power hence allow to proceed");

            } else {
                errorMsgParameterList = DecimalFormatterUtil.round(buyingPower, 5) + "," + DecimalFormatterUtil.round(order.getNetSettle(), 5) + "," + order.getSettleCurrency();
                return new CashTradingReplyBean(false, CashTradingFailReasons.NOT_ENOUGH_BUYING_POWER.getCode(),
                        "Not Enough buying power. Buying Power= " + DecimalFormatterUtil.round(buyingPower, 5) + " Net Settle= " + DecimalFormatterUtil.round(order.getNetSettle(), 5),
                        MODULE_CODE, errorMsgParameterList);

            }
            if (parentAccount != null) {    //if the parent account is not null then update the parent cash block
                cashTradingUtil.addToTheBlockAmount(parentAccount.getCashAccNumber(), -parentNetSettle);
            }
            cashTradingUtil.addToTheBlockAmount(customerAccount.getCashAccNumber(), -order.getNetSettle());
            return new CashTradingReplyBean(true);
        } catch (Exception e) {
            logger.error("Customer Configuration issue", e);
            throw new CashTradingException(e.getMessage(), e);
        }

    }


    @Override
    public CashTradingReply validateRiskForNewSellOrder(Order order) throws CashTradingException {
        CashTradingReply cashTradingReply;
        Account customerAccount;
        String errorMsgParameterList;
        //get the customer cash account
        try {
            customerAccount = accountManager.getAccount(order.getSecurityAccount());
            if (customerAccount == null) {
                throw new CashTradingException("Account not configured properly");
            }

            double netSettle = order.getNetValue() * order.getIssueSettleRate();
            double buyingPower = cashTradingUtil.getBuyingPower(customerAccount.getCashAccNumber());
            if (buyingPower + netSettle < DEFAULT_NET_VALUE) {// if the validation failed then the order should be reject
                errorMsgParameterList = DecimalFormatterUtil.round(buyingPower, 5) + "," + DecimalFormatterUtil.round(netSettle, 5);
                return new CashTradingReplyBean(false, CashTradingFailReasons.NOT_ENOUGH_BUYING_POWER.getCode(),
                        "Not enough funds for the customer account", MODULE_CODE, errorMsgParameterList);
            }
            order.setNetSettle(netSettle);
            cashTradingReply = new CashTradingReplyBean(true);

        } catch (Exception e) {
            logger.error("Customer configuration issue or system error", e);
            throw new CashTradingException(e.getMessage(), e);
        }
        return cashTradingReply;
    }


    @Override
    public CashTradingReply validateRiskForAmendOrder(Order order, double diff, double parentDiff) throws CashTradingException {
        String errorMsgParameterList;
        try {
            Account parentAccount = null;

            //in the case of sub account need to check the parent buying power
            if (order.getParentAccountNumber() != null) { // if the parent account number is not null need to the validations
                parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                //calculate the parent buying power
                double parentBuyingPower = cashTradingUtil.getBuyingPower(parentAccount.getCashAccNumber());
                CashAccount parentCashAccount = cashManager.getCashAccount(parentAccount.getCashAccNumber());


                logger.debug("Parent Buying Power -{}, Parent Diff -{}", parentBuyingPower, parentDiff);
                if (parentDiff <= parentBuyingPower) {
                   logger.info("Customer has enough buying power. Hence allow to proceed");
                } else {
                    if (parentBuyingPower < 0) {
                        parentBuyingPower = 0;
                    }
                    //set the error message parameter list  when the parent buying power is exceeded
                    errorMsgParameterList = DecimalFormatterUtil.round(parentBuyingPower, 5) + "," + DecimalFormatterUtil.round(parentDiff, 5);
                    return new CashTradingReplyBean(false, CashTradingFailReasons.NOT_ENOUGH_PARENT_BUYING_POWER.getCode(),
                            "Not Enough buying power for parent. Buying Power= " + DecimalFormatterUtil.round(parentBuyingPower, 5) + " Net Settle Diff= " + DecimalFormatterUtil.round(parentDiff, 5), MODULE_CODE, errorMsgParameterList);
                }
            }

            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            double buyingPower = cashTradingUtil.getBuyingPower(customerAccount.getCashAccNumber());
            logger.debug("Customer Buying power - {}, Diff - {}", buyingPower, diff);


            if (diff <= buyingPower) {
                logger.info("Customer has enough buying power. Hence allow to proceed");

            } else {
                errorMsgParameterList = DecimalFormatterUtil.round(buyingPower, 5) + "," + DecimalFormatterUtil.round(diff, 5);
                return new CashTradingReplyBean(false, CashTradingFailReasons.NOT_ENOUGH_BUYING_POWER.getCode(),
                        "Not Enough buying power. Buying Power= " + DecimalFormatterUtil.round(buyingPower, 5) + " Net Settle Diff= " + DecimalFormatterUtil.round(diff, 5),
                        MODULE_CODE, errorMsgParameterList);
            }
            if (parentAccount != null) {//if the parent account is not null then update the parent cash block
                cashTradingUtil.addToTheBlockAmount(parentAccount.getCashAccNumber(), -parentDiff);

            }
            cashTradingUtil.addToTheBlockAmount(customerAccount.getCashAccNumber(), -diff);
            return new CashTradingReplyBean(true);
        } catch (Exception e) {
            logger.error("customer configuration issue or system error", e);
            throw new CashTradingException(e.getMessage(), e);
        }

    }


    @Override
    public CashTradingReply recordOrderExpiry(Order order) throws CashTradingException {
        Account parentAccount = null;
        try {
            if (order.getParentAccountNumber() != null) { //if the customer has parent account then do the validations in the parent account
                parentAccount = accountManager.getAccount(order.getParentAccountNumber());

            }
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            if (order.getCumQty() > 0) {
                if (parentAccount != null) {
                    cashTradingUtil.addToTheBlockAmount(parentAccount.getCashAccNumber(), order.getParentBlockAmount());

                }
                cashTradingUtil.addToTheBlockAmount(customerAccount.getCashAccNumber(), order.getBlockAmount());
            }
            return new CashTradingReplyBean(true);
        } catch (Exception e) {
            logger.error("customer configuration issue or system error - {}", e);
            throw new CashTradingException(e.getMessage(), e);
        }
    }

    @Override
    public CashTradingReply recordOrderReplacement(Order order, Order oldOrder) throws CashTradingException {
        Account parentAccount = null;
        try {
            if (order.getParentAccountNumber() != null) {
                parentAccount = accountManager.getAccount(order.getParentAccountNumber());
            }
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            if (order.getParentAccountNumber() != null) {
                if (parentAccount != null) {
                    cashTradingUtil.addToTheBlockAmount(parentAccount.getCashAccNumber(), oldOrder.getParentBlockAmount() - order.getParentBlockAmount());
                }

            }
            cashTradingUtil.addToTheBlockAmount(customerAccount.getCashAccNumber(), oldOrder.getBlockAmount() - order.getBlockAmount());

            return new CashTradingReplyBean(true);
        } catch (Exception e) {
            logger.error("customer configuration issue or system error", e);
            throw new CashTradingException(e.getMessage(), e);
        }
    }

    @Override
    public CashTradingReply recordBuyOrderExecution(Order order, double newNetSettle, double newParentNetSettle) throws CashTradingException {
        try {
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());
            String code = "STLBUY";   // For the buy order use this code in the cash log
            String narration = order.getExecID() + " : PARTIAL/FULL BUY EXECUTION " + order.getSymbol() + QUANTITY + order.getLastShares() + " @" + order.getLastPx();
            cashTradingUtil.updateCashBalanceOfCustomerAndParent(customerAccount.getCashAccNumber(), order, code, narration);
            order.setTempBlockAmount(order.getBlockAmount());
            order.setTempParentBlockAmount(order.getParentBlockAmount());
            order.setBlockAmount(0);
            order.setParentBlockAmount(0);

            if ((order.getQuantity() - order.getCumQty()) > 0) {
                if (order.getParentAccountNumber() != null) {
                    Account parentAccount = accountManager.getAccount(order.getParentAccountNumber());
                    cashTradingUtil.addToTheBlockAmount(parentAccount.getCashAccNumber(), -newParentNetSettle);
                    order.setParentBlockAmount(newParentNetSettle);
                }
                cashTradingUtil.addToTheBlockAmount(customerAccount.getCashAccNumber(), -newNetSettle);
                order.setBlockAmount(newNetSettle);

            }
            return new CashTradingReplyBean(true);
        } catch (Exception e) {
            logger.error("customer configuration issue or system error", e);
            throw new CashTradingException(e.getMessage(), e);
        }

    }

    @Override
    public CashTradingReply recordSellOrderExecution(Order order) throws CashTradingException {
        try {
            String code = "STLSEL";  // For the sell order use this code in the cash log
            String narration;    // is the narration to be used the cash log
            narration = order.getExecID() + " : PARTIAL/FULL SELL EXECUTION " + order.getSymbol() + QUANTITY + order.getLastShares() + " @" + order.getLastPx();
            Account customerAccount = accountManager.getAccount(order.getSecurityAccount());

            //adjust the customer and parent cash accounts and cash log accordingly
            cashTradingUtil.updateCashBalanceOfCustomerAndParent(customerAccount.getCashAccNumber(), order, code, narration);
                       return new CashTradingReplyBean(true);
        } catch (Exception e) {
            logger.error("customer configuration issue or system error", e);
            throw new CashTradingException(e.getMessage(), e);
        }
    }
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
    }

    public void setCashTradingUtil(CashTradingUtil cashTradingUtil) {
        this.cashTradingUtil = cashTradingUtil;
    }

}
