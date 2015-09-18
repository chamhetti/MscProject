package lk.ac.ucsc.oms.customer.api.beans.account;


import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.CustodianType;
import lk.ac.ucsc.oms.customer.api.beans.FeedLevel;
import lk.ac.ucsc.oms.customer.api.beans.MarketType;


public interface ExchangeAccount {

    long getExchangeAccId();

    void setExchangeAccId(long exchangeAccId);

    String getExchangeAccNumber();

    void setExchangeAccNumber(String accountNumber);

    String getExchangeCode();

    void setExchangeCode(String exchangeCode);

    int getAccountType();

    void setAccountType(int accountType);

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getExecBrokerCode();

    void setExecBrokerCode(String execBrokerId);

    RecordStatus getStatus();

    void setStatus(RecordStatus status);

    PropertyEnable getTradingEnable();

    void setTradingEnable(PropertyEnable tradingEnable);

    PropertyEnable getShariaComplient();

    void setShariaComplient(PropertyEnable shariaComplient);

    long getBackOfficeId();

    void setBackOfficeId(long backOfficeId);

    String getExternalRefNo();

    void setExternalRefNo(String externalRefNo);

}
