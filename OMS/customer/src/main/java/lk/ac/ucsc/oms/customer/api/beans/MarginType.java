package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/8/13
 * Time: 1:18 PM
 */

/**
 * margin type of customer like NORMAL, CONVENTIONAL, MURABAH
 * string representation of enums
 * <ul>
 * <li>margin type NORMAL       = 0</li>
 * <li>margin type CONVENTIONAL = 1</li>
 * <li>margin type MURABAH      = 2</li>
 * </ul>
 *
 */
public enum MarginType {
    NORMAL(0),CONVENTIONAL(1), MURABAH(2);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of MarginType
     */
    MarginType(int code) {
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
    public static MarginType getEnum(int code) {
        switch (code) {
            case 0:
                return NORMAL;
            case 1:
                return CONVENTIONAL;
            case 2:
                return MURABAH;
            default:
                return NORMAL;
        }
    }
}
