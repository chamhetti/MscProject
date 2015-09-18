package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class ExchangeAccountBean {

    @SerializedName(EXCHANGE)
    private String exchange = null;

    @SerializedName(INVESTOR_ACCOUNT)
    private String investorAccount = null;

    @SerializedName(EXCHANGE_TRADING_ENABLED)
    private int isTrading = -1;

    @SerializedName(EXCHANGE_ACCOUNT_TYPE)
    private int exchangeAccountType = -1;

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String securityAccount = null;

    @SerializedName(CUSTODIAN)
    private String custodian = null;

    @SerializedName(EXCHANGE_ACCOUNT_NUMBER)
    private String exchangeAccountNumber = null;

    @SerializedName(APPROVAL_STATUS_ID)
    private int approvalStatus = -1;

    @SerializedName(TRADING_ENABLED_STATUS)
    private int tradingEnabledStatus = -1;

    @SerializedName(SHARIA_COMPLIANT)
    private int shariaCompliant = -1;

    @SerializedName(DEPOSITOR_ACCOUNT)
    private String depositorAccount = null;

    @SerializedName(ACCOUNT_CATEGORY)
    private int accountCategory = -1;

    @SerializedName(EXPIRE_TIME)
    private String expireDate;

    @SerializedName(SETTLEMENT_TYPE)
    private byte settlementType = -1;

    @SerializedName(DEFAULT_FEED_LEVEL)
    private byte defaultFeedLevel = -1;

    @SerializedName(REGION_TYPE)
    private byte regionType = 0;   // Exchange region - intl/local

    @SerializedName(CURRENCY)
    private String currency;

    @SerializedName(REALTIME_LEVEL1_FEE)
    private double realTimeLevel1Fee;

    @SerializedName(REALTIME_LEVEL2_FEE)
    private double realTimeLevel2Fee;

    @SerializedName(LEVEL_OF_FEED_SUPPORTED)
    private int levelOfFeedSupported;

    @SerializedName(MARKET_TYPE)
    private int marketType;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getInvestorAccount() {
        return investorAccount;
    }

    public void setInvestorAccount(String investorAccount) {
        this.investorAccount = investorAccount;
    }

    public int getTrading() {
        return isTrading;
    }

    public void setTrading(int trading) {
        isTrading = trading;
    }

    public int getExchangeAccountType() {
        return exchangeAccountType;
    }

    public void setExchangeAccountType(int exchangeAccountType) {
        this.exchangeAccountType = exchangeAccountType;
    }

    public String getSecurityAccount() {
        return securityAccount;
    }

    public void setSecurityAccount(String securityAccount) {
        this.securityAccount = securityAccount;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getExchangeAccountNumber() {
        return exchangeAccountNumber;
    }

    public void setExchangeAccountNumber(String exchangeAccountNumber) {
        this.exchangeAccountNumber = exchangeAccountNumber;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getTradingEnabledStatus() {
        return tradingEnabledStatus;
    }

    public void setTradingEnabledStatus(int tradingEnabledStatus) {
        this.tradingEnabledStatus = tradingEnabledStatus;
    }

    public int getShariaCompliant() {
        return shariaCompliant;
    }

    public void setShariaCompliant(int shariaCompliant) {
        this.shariaCompliant = shariaCompliant;
    }

    public String getDepositorAccount() {
        return depositorAccount;
    }

    public void setDepositorAccount(String depositorAccount) {
        this.depositorAccount = depositorAccount;
    }

    public int getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(int accountCategory) {
        this.accountCategory = accountCategory;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public byte getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(byte settlementType) {
        this.settlementType = settlementType;
    }

    public byte getDefaultFeedLevel() {
        return defaultFeedLevel;
    }

    public void setDefaultFeedLevel(byte defaultFeedLevel) {
        this.defaultFeedLevel = defaultFeedLevel;
    }

    public byte getRegionType() {
        return regionType;
    }

    public void setRegionType(byte regionType) {
        this.regionType = regionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRealTimeLevel1Fee() {
        return realTimeLevel1Fee;
    }

    public void setRealTimeLevel1Fee(double realTimeLevel1Fee) {
        this.realTimeLevel1Fee = realTimeLevel1Fee;
    }

    public double getRealTimeLevel2Fee() {
        return realTimeLevel2Fee;
    }

    public void setRealTimeLevel2Fee(double realTimeLevel2Fee) {
        this.realTimeLevel2Fee = realTimeLevel2Fee;
    }

    public int getLevelOfFeedSupported() {
        return levelOfFeedSupported;
    }

    public void setLevelOfFeedSupported(int levelOfFeedSupported) {
        this.levelOfFeedSupported = levelOfFeedSupported;
    }

    public int getMarketType() {
        return marketType;
    }

    public void setMarketType(int marketType) {
        this.marketType = marketType;
    }
}
