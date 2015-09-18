package lk.ac.ucsc.oms.customer.implGeneral.beans.cash;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;

import java.util.Date;

public class CashLogBean extends CacheObject implements CashLog {

    private long cashLogId;
    private long cashAccId;
    private String status;
    private double amount;
    private String orderNumber;
    private String symbol;
    private double commission;
    private String cashLogCode;
    private Date transactionDate;
    private String narration;
    private String transactionCurrency;
    private double amountInTransCurrency;
    private String exchange;
    private Date settlementDate;
    private double exchangeCommission;
    private double lastPrice;
    private double issueSettleRate;
    private String customerNumber;
    private String settleCurrency;
    private int institutionId;
    private double lastShare;
    private double amtInSettleCurrency;
    private String execBrokerId;
    private double marginDue;
    private String accountNumber;
    private double brokerCommission;
    private double thirdPartyCommission;
    private double profitLoss;
    private double currencySpread;
    private String institutionCode;
    private double resultingCashBalance;


    @Override
    public long getCashLogId() {
        return cashLogId;
    }

    @Override
    public void setCashLogId(long cashLogId) {
        this.cashLogId = cashLogId;
    }

    @Override
    public long getCashAccId() {
        return cashAccId;
    }

    @Override
    public void setCashAccId(long cashAccId) {
        this.cashAccId = cashAccId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public double getCommission() {
        return commission;
    }

    @Override
    public void setCommission(double commission) {
        this.commission = commission;
    }

    @Override
    public String getCashLogCode() {
        return cashLogCode;
    }

    @Override
    public void setCashLogCode(String cashLogCode) {
        this.cashLogCode = cashLogCode;
    }

    @Override
    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String getNarration() {
        return narration;
    }

    @Override
    public void setNarration(String narration) {
        this.narration = narration;
    }

    @Override
    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    @Override
    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    @Override
    public double getAmountInTransCurrency() {
        return amountInTransCurrency;
    }

    @Override
    public void setAmountInTransCurrency(double amountInTransCurrency) {
        this.amountInTransCurrency = amountInTransCurrency;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public Date getSettlementDate() {
        return settlementDate;
    }

    @Override
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public double getExchangeCommission() {
        return exchangeCommission;
    }

    @Override
    public void setExchangeCommission(double exchangeCommission) {
        this.exchangeCommission = exchangeCommission;
    }

    @Override
    public double getLastPrice() {
        return lastPrice;
    }

    @Override
    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @Override
    public double getIssueSettleRate() {
        return issueSettleRate;
    }

    @Override
    public void setIssueSettleRate(double issueSettleRate) {
        this.issueSettleRate = issueSettleRate;
    }

    @Override
    public String getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String getSettleCurrency() {
        return settleCurrency;
    }

    @Override
    public void setSettleCurrency(String settleCurrency) {
        this.settleCurrency = settleCurrency;
    }

    @Override
    public int getInstitutionId() {
        return institutionId;
    }

    @Override
    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    @Override
    public double getLastShare() {
        return lastShare;
    }

    @Override
    public void setLastShare(double lastShare) {
        this.lastShare = lastShare;
    }

    @Override
    public double getAmtInSettleCurrency() {
        return amtInSettleCurrency;
    }

    @Override
    public void setAmtInSettleCurrency(double amtInSettleCurrency) {
        this.amtInSettleCurrency = amtInSettleCurrency;
    }

    @Override
    public String getExecBrokerId() {
        return execBrokerId;
    }

    @Override
    public void setExecBrokerId(String execBrokerId) {
        this.execBrokerId = execBrokerId;
    }

    @Override
    public double getMarginDue() {
        return marginDue;
    }

    @Override
    public void setMarginDue(double marginDue) {
        this.marginDue = marginDue;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public double getBrokerCommission() {
        return brokerCommission;
    }

    @Override
    public void setBrokerCommission(double brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    @Override
    public double getThirdPartyCommission() {
        return thirdPartyCommission;
    }

    @Override
    public void setThirdPartyCommission(double thirdPartyCommission) {
        this.thirdPartyCommission = thirdPartyCommission;
    }

    @Override
    public double getProfitLoss() {
        return profitLoss;
    }

    @Override
    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    @Override
    public double getCurrencySpread() {
        return currencySpread;
    }

    @Override
    public void setCurrencySpread(double currencySpread) {
        this.currencySpread = currencySpread;
    }

    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }

    @Override
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    @Override
    public double getResultingCashBalance() {
        return resultingCashBalance;
    }

    @Override
    public void setResultingCashBalance(double resultingCashBalance) {
        this.resultingCashBalance = resultingCashBalance;
    }

}
