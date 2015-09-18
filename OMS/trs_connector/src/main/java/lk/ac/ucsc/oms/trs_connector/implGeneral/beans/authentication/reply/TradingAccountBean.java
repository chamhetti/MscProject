package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.reply;

import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.TradingAccount;


public class TradingAccountBean implements TradingAccount {
    private String exchange;
    private String tradingAcctNo;
    private boolean istradingEnabled;
    private String trdDisenablingReason;
    private boolean isexchangeTradingEnabled;

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getTradingAcctNo() {
        return tradingAcctNo;
    }

    @Override
    public void setTradingAcctNo(String tradingAcctNo) {
        this.tradingAcctNo = tradingAcctNo;
    }

    @Override
    public boolean isIstradingEnabled() {
        return istradingEnabled;
    }

    @Override
    public void setIstradingEnabled(boolean istradingEnabled) {
        this.istradingEnabled = istradingEnabled;
    }

    @Override
    public String getTrdDisenablingReason() {
        return trdDisenablingReason;
    }

    @Override
    public void setTrdDisenablingReason(String trdDisenablingReason) {
        this.trdDisenablingReason = trdDisenablingReason;
    }

    @Override
    public boolean isIsexchangeTradingEnabled() {
        return isexchangeTradingEnabled;
    }

    @Override
    public void setIsexchangeTradingEnabled(boolean isexchangeTradingEnabled) {
        this.isexchangeTradingEnabled = isexchangeTradingEnabled;
    }

    @Override
    public String toString() {
        return "TradingAccountBean{" +
                "exchange='" + exchange + '\'' +
                ", tradingAcctNo='" + tradingAcctNo + '\'' +
                ", istradingEnabled=" + istradingEnabled +
                ", trdDisenablingReason='" + trdDisenablingReason + '\'' +
                ", isexchangeTradingEnabled=" + isexchangeTradingEnabled +
                '}';
    }
}
