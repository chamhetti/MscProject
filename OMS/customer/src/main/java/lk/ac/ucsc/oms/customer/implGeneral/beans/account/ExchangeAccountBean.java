package lk.ac.ucsc.oms.customer.implGeneral.beans.account;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.CustodianType;
import lk.ac.ucsc.oms.customer.api.beans.FeedLevel;
import lk.ac.ucsc.oms.customer.api.beans.MarketType;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;

@Indexed
public class ExchangeAccountBean extends CacheObject implements ExchangeAccount {
    @Id
    @Field
    private long exchangeAccId;
    @Field
    private String exchangeCode;
    @Field
    private int accountType;
    @Field
    private String exchangeAccNumber;
    @Field
    private PropertyEnable tradingEnable;
    @Field
    private RecordStatus status;
    @Field
    private String execBrokerCode;
    @Field
    private String accountNumber;
    @Field
    private PropertyEnable shariaComplient;
    @Field
    private long backOfficeId;
    @Field
    private String externalRefNo;

    public ExchangeAccountBean(String accountNumber, String exchangeCode) {
        this.exchangeAccNumber = accountNumber;
        this.exchangeCode = exchangeCode;
    }

    protected ExchangeAccountBean() {

    }

    @Override
    public String getExecBrokerCode() {
        return execBrokerCode;
    }

    @Override
    public void setExecBrokerCode(String execBrokerCode) {
        this.execBrokerCode = execBrokerCode;
    }

    @Override
    public RecordStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    @Override
    public PropertyEnable getTradingEnable() {
        return tradingEnable;
    }

    @Override
    public void setTradingEnable(PropertyEnable tradingEnable) {
        this.tradingEnable = tradingEnable;
    }

    @Override
    public String getExchangeAccNumber() {
        return exchangeAccNumber;
    }

    @Override
    public void setExchangeAccNumber(String exchangeAccNumber) {
        this.exchangeAccNumber = exchangeAccNumber;
    }

    @Override
    public int getAccountType() {
        return accountType;
    }

    @Override
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    @Override
    public String getExchangeCode() {
        return exchangeCode;
    }

    @Override
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    @Override
    public long getExchangeAccId() {
        return exchangeAccId;
    }


    public void setExchangeAccId(long exchangeAccId) {
        this.exchangeAccId = exchangeAccId;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    @Override
    public PropertyEnable getShariaComplient() {
        return shariaComplient;
    }

    @Override
    public void setShariaComplient(PropertyEnable shariaComplient) {
        this.shariaComplient = shariaComplient;
    }

    public long getBackOfficeId() {
        return backOfficeId;
    }

    public void setBackOfficeId(long backOfficeId) {
        this.backOfficeId = backOfficeId;
    }

    @Override
    public String getExternalRefNo() {
        return externalRefNo;
    }

    @Override
    public void setExternalRefNo(String externalRefNo) {
        this.externalRefNo = externalRefNo;
    }

}
