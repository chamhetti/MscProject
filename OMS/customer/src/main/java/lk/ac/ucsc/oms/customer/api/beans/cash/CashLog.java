package lk.ac.ucsc.oms.customer.api.beans.cash;

import java.util.Date;

public interface CashLog {

    long getCashLogId();

    void setCashLogId(long cashLogId);

    long getCashAccId();

    void setCashAccId(long cashAccId);

    String getStatus();

    void setStatus(String status);

    double getAmount();

    void setAmount(double amount);

    String getOrderNumber();

    void setOrderNumber(String orderNumber);

    String getSymbol();

    void setSymbol(String symbol);

    double getCommission();

    void setCommission(double commission);

    String getCashLogCode();


    void setCashLogCode(String cashLogCode);

    Date getTransactionDate();

    void setTransactionDate(Date transactionDate);

    String getNarration();

    void setNarration(String narration);

    String getTransactionCurrency();


    void setTransactionCurrency(String transactionCurrency);

    double getAmountInTransCurrency();


    void setAmountInTransCurrency(double amountInTransCurrency);

    String getExchange();

    void setExchange(String exchange);

    Date getSettlementDate();

    void setSettlementDate(Date settlementDate);

    double getExchangeCommission();

    void setExchangeCommission(double exchangeCommission);

   double getLastPrice();

    void setLastPrice(double lastPrice);

    double getIssueSettleRate();

    void setIssueSettleRate(double issueSettleRate);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    String getSettleCurrency();

    void setSettleCurrency(String settleCurrency);

    int getInstitutionId();

    void setInstitutionId(int institutionId);

    double getLastShare();

    void setLastShare(double lastShare);

    double getAmtInSettleCurrency();


    void setAmtInSettleCurrency(double amtInSettleCurrency);

    String getExecBrokerId();

    void setExecBrokerId(String execBrokerId);

    double getMarginDue();

    void setMarginDue(double marginDue);

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    double getBrokerCommission();

    void setBrokerCommission(double brokerCommission);

    double getThirdPartyCommission();

    void setThirdPartyCommission(double thirdPartyCommission);

    double getProfitLoss();

    void setProfitLoss(double profitLoss);

    double getCurrencySpread();

    void setCurrencySpread(double currencySpread);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

    double getResultingCashBalance();

    void setResultingCashBalance(double resultingCashBalance);



}
