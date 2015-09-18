package lk.ac.ucsc.oms.common_utility.api.enums;


/**
 * @author hetti
 */
public enum OrderType {

    /**
     *
     */
    MARKET("1"),
    /**
     *
     */
    LIMIT("2"),
    /**
     * Stop loss order is also known as stop market order
     */
    STOP_LOSS("3"),
    /**
     *
     */
    STOP_LIMIT("4"),
    /**
     *
     */
    MARKET_ON_CLOSE("5"),
    /**
     *
     */
    SQUARE_OFF("c"),
    /**
     *
     */
    LIMIT_ON_CLOSE("B"),
    /**
     * this is undefined order type there are these types of entries in back office - //todo should check and confirm
     */
    UNDEFINED_ORDER_TYPE("X");

    private String code;

    OrderType(String c) {
        this.code = c;
    }

    /**
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code String Market Code
     * @return type
     */
    public static OrderType getEnum(String code) {

        if (code == null) {
            return UNDEFINED_ORDER_TYPE;
        }

        switch (code) {
            case "1":
                return MARKET;
            case "2":
                return LIMIT;
            case "3":
                return STOP_LOSS;
            case "4":
                return STOP_LIMIT;
            case "5":
                return MARKET_ON_CLOSE;
            case "c":
                return SQUARE_OFF;
            case "B":
                return LIMIT_ON_CLOSE;
            case "X":
                return UNDEFINED_ORDER_TYPE;
            default:
                return UNDEFINED_ORDER_TYPE;
        }
    }
}
