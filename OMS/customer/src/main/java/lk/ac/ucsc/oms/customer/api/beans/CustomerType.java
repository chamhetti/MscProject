package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/7/13
 * Time: 10:10 AM
 */

/**
 * customer type like NORMAL, INTERNATIONAL
 * string representation of enums
 * <ul>
 * <li>customer type NORMAL         = 0</li>
 * <li>customer type INTERNATIONAL  = 1</li>
 * </ul>
 *
 */
public enum CustomerType {

    NORMAL(0), INTERNATIONAL(1);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of CustomerType
     */
    CustomerType(int code) {
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
    public static CustomerType getEnum(int code) {
        switch (code) {
            case 0:
                return NORMAL;
            case 1:
                return INTERNATIONAL;
            default:
                return NORMAL;
        }
    }

}
