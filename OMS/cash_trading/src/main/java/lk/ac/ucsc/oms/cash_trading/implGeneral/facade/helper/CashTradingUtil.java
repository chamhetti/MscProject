package lk.ac.ucsc.oms.cash_trading.implGeneral.facade.helper;

import lk.ac.ucsc.oms.cash_trading.api.exceptions.CashTradingException;

import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.formatters.DecimalFormatterUtil;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.beans.cash.BlockAmount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CashTradingUtil {
    private static final int ROUND_CONST = 5; // this is the round off value use to round the cash balance value before save to the cache
    private Logger logger = LogManager.getLogger(CashTradingUtil.class);
    private CashManager cashManager;


    public double getBuyingPower(String cashAccountNumber) throws CashTradingException {
        try {
            return cashManager.getBuyingPower(cashAccountNumber);
        } catch (CashManagementException e) {
            logger.error("problem in calculating buying power", e);
            throw new CashTradingException(e.getMessage(), e);
        }
    }

    public void addToTheBlockAmount(String cashAccountNumber, double amount) throws CashTradingException {
        logger.info("Cash account number = {} Updating the cash block with value = {}", cashAccountNumber, amount);
        try {
            BlockAmount blockAmount = cashManager.getEmptyBlockAmountBean();
            blockAmount.setCashBlock(amount);
            cashManager.blockCashOrMargin(cashAccountNumber, blockAmount);

        } catch (CashManagementException e) {
            logger.error("problem in calculating buying power", e);
            throw new CashTradingException(e.getMessage(), e);
        }
    }
    public void updateCashBalanceOfCustomerAndParent(String cashAccountNumber, final Order order, String cashLogCode, String narration) throws CashTradingException {
        try {
            CashAccount cashAccount = cashManager.getCashAccountForUpdate(cashAccountNumber);

            double balance = DecimalFormatterUtil.round(cashAccount.getBalance() + order.getNetSettleDiff(), ROUND_CONST);
            logger.debug("New Cash Balance - {}", balance);
            cashAccount.setBalance(balance);
            cashAccount.setBlockAmt(cashAccount.getBlockAmt() + order.getBlockAmount());
            if (cashAccount.getBlockAmt() > 0) {
                //according to convention cash block amount cannot be positive so we have to set the value explicitly to 0
                cashAccount.setBlockAmt(0);
            }
            //pending settle is updated for sell order executions only
            if (order.getSide() == OrderSide.SELL) {
                cashAccount.setPendingSettle(cashAccount.getPendingSettle() + DecimalFormatterUtil.round(order.getNetSettleDiff(), ROUND_CONST));
            }
            //update the customer cash account
            cashManager.updateCashAccount(cashAccount);
            //update the cash log of the customer
            cashManager.addCashLog(populateAndGetCustomerCashLog(order, cashAccount, cashLogCode, narration));
        } catch (Exception e) {
            logger.error("Cash Balance update issue", e);
            throw new CashTradingException(e.getMessage(), e);
        }
    }


    private CashLog populateAndGetCustomerCashLog(final Order order, CashAccount cashAccount, String cashLogCode, String narration) {
        CashLog cashLog = cashManager.getEmptyCashLog();
        cashLog.setAccountNumber(order.getSecurityAccount());
        cashLog.setAmount(order.getOrderValueDiff());
        cashLog.setAmountInTransCurrency(order.getNetValueDiff());
        cashLog.setAmtInSettleCurrency(order.getNetSettleDiff());
        cashLog.setCashAccId(cashAccount.getCashAccId());
        cashLog.setCashLogCode(cashLogCode);
        cashLog.setCommission(order.getCommissionDiff());
        cashLog.setCustomerNumber(order.getCustomerNumber());
        cashLog.setExchange(order.getExchange());
        cashLog.setExchangeCommission(order.getExchangeCommDiff());
        cashLog.setExecBrokerId(order.getExecBrokerId());
        cashLog.setInstitutionId(order.getInstitutionId());
        cashLog.setIssueSettleRate(order.getIssueSettleRate());
        cashLog.setLastPrice(order.getPrice());
        cashLog.setLastShare(order.getLastShares());
        cashLog.setNarration(narration);
        cashLog.setOrderNumber(order.getOrderNo());
        cashLog.setSettleCurrency(order.getSettleCurrency());
        cashLog.setSymbol(order.getSymbol());
        cashLog.setTransactionCurrency(order.getCurrency());
        cashLog.setTransactionDate(new Date());
        cashLog.setProfitLoss(order.getProfitLoss());
        cashLog.setInstitutionCode(order.getInstitutionCode());
        cashLog.setBrokerCommission(order.getBrokerCommDiff());
        cashLog.setResultingCashBalance(cashAccount.getBalance());

        return cashLog;
    }

    public void setCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
    }
}
