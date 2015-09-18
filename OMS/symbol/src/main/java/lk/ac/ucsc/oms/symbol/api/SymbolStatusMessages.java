package lk.ac.ucsc.oms.symbol.api;


public enum SymbolStatusMessages {
    INVALID_SYMBOL(1), TRADING_DISABLE(2), SYMBOL_NOT_APPROVED(3), INVALID_MIN_QTY(4), LOCAL_ONLY_SYMBOL(5);
    private int code;

    SymbolStatusMessages(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SymbolStatusMessages getEnum(int code) {
        switch (code) {

            case 1:
                return INVALID_SYMBOL;
            case 2:
                return TRADING_DISABLE;
            case 3:
                return SYMBOL_NOT_APPROVED;
            case 4:
                return INVALID_MIN_QTY;
            case 5:
                return LOCAL_ONLY_SYMBOL;
            default:
                return INVALID_SYMBOL;
        }
    }
}
