package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.common;


public class ExchangeAccBean {

    private String exchange = null;
    private byte defaultFeedLevel = -1;
    private byte marketType =0;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public byte getDefaultFeedLevel() {
        return defaultFeedLevel;
    }

    public void setDefaultFeedLevel(byte defaultFeedLevel) {
        this.defaultFeedLevel = defaultFeedLevel;
    }

    public byte getMarketType() {
        return marketType;
    }

    public void setMarketType(byte marketType) {
        this.marketType = marketType;
    }
}
