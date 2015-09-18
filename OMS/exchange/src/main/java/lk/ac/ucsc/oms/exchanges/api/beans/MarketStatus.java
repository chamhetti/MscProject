package lk.ac.ucsc.oms.exchanges.api.beans;


public enum MarketStatus {
    UNKNOWN(0), HALTED(1), OPEN(2), CLOSED(3), PRE_OPEN(4), PRE_CLOSE(5);

    private int code;

    MarketStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MarketStatus getEnum(int code) {
        MarketStatus[] values = MarketStatus.values();
        if (code < 0 || code >= values.length) {
            return null;
        } else {
            return values[code];
        }
    }

}
