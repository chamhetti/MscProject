package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class TradingAccountBean {

    @SerializedName(SECURITY_EXCHANGE)
    private String exchange = null;

    @SerializedName(TRADING_ACCOUNT_NUMBER)
    private String tradingAccountNumber = null;

    @SerializedName(TRADING_ENABLED_STATUS)
    private int tradingEnabledStatus = -1;

    @SerializedName(TRADING_DISABLED_REASON)
    private String tradingDisabledReason = null;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTradingAccountNumber() {
        return tradingAccountNumber;
    }

    public void setTradingAccountNumber(String tradingAccountNumber) {
        this.tradingAccountNumber = tradingAccountNumber;
    }

    public int getTradingEnabledStatus() {
        return tradingEnabledStatus;
    }

    public void setTradingEnabledStatus(int tradingEnabledStatus) {
        this.tradingEnabledStatus = tradingEnabledStatus;
    }

    public String getTradingDisabledReason() {
        return tradingDisabledReason;
    }

    public void setTradingDisabledReason(String tradingDisabledReason) {
        this.tradingDisabledReason = tradingDisabledReason;
    }

}
