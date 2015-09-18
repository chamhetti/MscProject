package lk.ac.ucsc.oms.exchanges.api.beans;


public enum MarketConnectStatus {

    DISCONNECTED(0), CONNECTED(1), UNKNOWN(2);

    private int code;

    MarketConnectStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MarketConnectStatus getEnum(int code) {
        MarketConnectStatus[] values = MarketConnectStatus.values();
        if (code < 0 || code >= values.length) {
            return null;
        } else {
            return values[code];
        }
    }

}
