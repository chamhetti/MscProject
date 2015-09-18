package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/8/13
 * Time: 12:32 PM
 */

/**
 * Feed Level like DELAYED, REALTIME_L1, REALTIME_L2
 * string representation of enums
 * <ul>
 * <li>feed level DELAYED       = 0</li>
 * <li>feed level REALTIME_L1   = 1</li>
 * <li>feed level REALTIME_L2   = 2</li>
 * </ul>
 *
 */
public enum FeedLevel {
    DELAYED(0), REALTIME_L1(1), REALTIME_L2(2);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of FeedLevel
     */
    FeedLevel(int code) {
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
    public static FeedLevel getEnum(int code) {
        switch (code) {
            case 0:
                return DELAYED;
            case 1:
                return REALTIME_L1;
            case 2:
                return REALTIME_L2;
            default:
                return DELAYED;
        }
    }
}
