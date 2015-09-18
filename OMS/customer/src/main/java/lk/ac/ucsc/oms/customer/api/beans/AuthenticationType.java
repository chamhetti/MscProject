package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/7/13
 * Time: 10:18 AM
 */

/**
 * Authentication type of customers like NO_PASSWORD, PASSWORD_ONCE, ... etc
 * string representation of enums
 * <ul>
 * <li>authentication type NO_PASSWORD          = 1</li>
 * <li>authentication type PASSWORD_ONCE        = 2</li>
 * <li>authentication type PASSWORD_EACH_TIME   = 3</li>
 * <li>authentication type USB                  = 4</li>
 * <li>authentication type SMS_OPT              = 5</li>
 * <li>authentication type FINGER_PRINT         = 6</li>
 * </ul>
 */
public enum AuthenticationType {
    NON_EXISTING(0), NO_PASSWORD(1), PASSWORD_ONCE(2), PASSWORD_EACH_TIME(3), USB(4), SMS_OPT(5), FINGER_PRINT(6);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of authentication type
     */
    AuthenticationType(int code) {
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

    private static final int NO_PASS = 1;
    private static final int PASS_ONCE = 2;
    private static final int PASS_EACHTIME = 3;
    private static final int USB1 = 4;
    private static final int OPT = 5;
    private static final int FING_PRINT = 6;

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return AuthenticationType
     */
    public static AuthenticationType getEnum(int code) {
        switch (code) {
            case NO_PASS:
                return NO_PASSWORD;
            case PASS_ONCE:
                return PASSWORD_ONCE;
            case PASS_EACHTIME:
                return PASSWORD_EACH_TIME;
            case USB1:
                return USB;
            case OPT:
                return SMS_OPT;
            case FING_PRINT:
                return FINGER_PRINT;
            default:
                return NON_EXISTING;
        }
    }
}
