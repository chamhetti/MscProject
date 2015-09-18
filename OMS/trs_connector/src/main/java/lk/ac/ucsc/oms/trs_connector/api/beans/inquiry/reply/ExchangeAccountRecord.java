package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * User: vimalanathanr
 * Date: 5/20/13
 * Time: 1:45 PM
 */
public interface ExchangeAccountRecord {

    String getExchangeCode();

    void setExchangeCode(String exchangeCode);

    int getAccountType();

    void setAccountType(int accountType);

    String getExchangeAccNumber();

    void setExchangeAccNumber(String exchangeAccNumber);

    int getTradingEnable();

    void setTradingEnable(int tradingEnable);

    int getCustodianType();

    void setCustodianType(int custodianType);

    int getComminssionGrpId();

    void setComminssionGrpId(int comminssionGrpId);

    int getStatus();

    void setStatus(int status);

    String getExecBrokerCode();

    void setExecBrokerCode(String execBrokerCode);

    int getFeedLevel();

    void setFeedLevel(int feedLevel);

    String getMacasaAccNo();

    void setMacasaAccNo(String macasaAccNo);

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getBuySideBrokerCode();

    void setBuySideBrokerCode(String buySideBrokerCode);

    double getTransactionFee();

    void setTransactionFee(double transactionFee);

    double getCustomerRebate();

    void setCustomerRebate(double customerRebate);

    long getOrderFeePackage();

    void setOrderFeePackage(long orderFeePackage);

    int getOrderFeeApplicability();

    void setOrderFeeApplicability(int orderFeeApplicability);

    String getInvestorAccountNumber();

    void setInvestorAccountNumber(String investorAccountNumber);

    int getShariaComplaint();

    void setShariaComplaint(int shariaComplaint);
}
