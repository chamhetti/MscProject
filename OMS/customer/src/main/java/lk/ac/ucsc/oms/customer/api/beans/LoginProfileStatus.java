package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/8/13
 * Time: 2:57 PM
 */

/**
 * login profile status of customer like PENDING, ACTIVE, .... etc
 * string representation of enums
 * <ul>
 * <li>login profile status PENDING     = 0</li>
 * <li>login profile status ACTIVE      = 1</li>
 * <li>login profile status LOCKED      = 2</li>
 * <li>login profile status SUSPENDED   = 3</li>
 * </ul>
 */
public enum LoginProfileStatus {
    PENDING(0), ACTIVE(1), LOCKED(2), SUSPENDED(3), DELETED(4);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of LoginProfileStatus
     */
    LoginProfileStatus(int code) {
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

    private static final int PENDNG = 0;
    private static final int ACTVE = 1;
    private static final int LOCK = 2;
    private static final int SUSPEND = 3;
    private static final int DELETE =4;

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return AuthenticationType
     */
    public static LoginProfileStatus getEnum(int code) {
        switch (code) {
            case PENDNG:
                return PENDING;
            case ACTVE:
                return ACTIVE;
            case LOCK:
                return LOCKED;
            case SUSPEND:
                return SUSPENDED;
            case DELETE:
                return DELETED;
            default:
                return PENDING;
        }
    }
}
