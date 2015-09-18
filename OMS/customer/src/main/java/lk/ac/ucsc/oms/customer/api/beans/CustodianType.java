package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/8/13
 * Time: 12:44 PM
 */

/**
 * Custodian type of customer like NONE, FULL, SHARE
 * string representation of enums
 * <ul>
 * <li>custodian type NONE  = 0</li>
 * <li>custodian type FULL  = 1</li>
 * <li>custodian type SHARE = 2</li>
 * </ul>
 *
 */
public enum CustodianType {
    NONE(0), FULL(1), SHARE(2);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of CustodianType
     */
    CustodianType(int code) {
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
    public static CustodianType getEnum(int code) {
        switch (code) {
            case 0:
                return NONE;
            case 1:
                return FULL;
            case 2:
                return SHARE;
            default:
                return NONE;
        }
    }
}
