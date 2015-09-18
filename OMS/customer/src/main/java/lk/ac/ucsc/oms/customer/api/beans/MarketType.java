package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: chamindah
 * Date: 2/24/15
 * Time: 4:48 PM
 */
/**
 * Market type like SYMBOL_DRIVEN, FULL
 * string representation of enums
 * <ul>
 * <li>feed level SYMBOL_DRIVEN       = 0</li>
 * <li>feed level FULL   = 1</li>
 * </ul>
 *
 */
public enum MarketType {
    SYMBOL_DRIVEN(0), FULL(1), ;
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of FeedLevel
     */
    MarketType(int code) {
        this.code = code;
    }

    /**
     * get code of the Enum
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return AuthenticationType
     */
    public static MarketType getEnum(int code) {
        switch (code) {
            case 0:
                return SYMBOL_DRIVEN;
            case 1:
                return FULL;
            default:
                return SYMBOL_DRIVEN;
        }
    }
}
