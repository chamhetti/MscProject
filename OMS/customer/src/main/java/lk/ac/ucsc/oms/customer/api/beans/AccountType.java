package lk.ac.ucsc.oms.customer.api.beans;

/**
 * 0-Individual 1-Joint Account 2-Corporate
 * User: Hetti
 * Date: 1/21/13
 * Time: 4:10 PM
 */

/**
 * account type of customer like INDIVIDUAL, JOINT, CORPORATE
 * string representation of enums
 * <ul>
 * <li>account type INDIVIDUAL  = 0</li>
 * <li>account type JOINT       = 1</li>
 * <li>account type CORPORATE   = 2</li>
 * </ul>
 *
 */
public enum AccountType {
    INDIVIDUAL(0), JOINT(1), CORPORATE(2);
    private int code;

    /**
     * constructor of the enum
     *
     * @param code of account type
     */
    AccountType(int code) {
        this.code = code;
    }

    /**
     * get code of the enum
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * get enum for the code
     *
     * @param code of the enum
     * @return accountType
     */
    public static AccountType getEnum(int code) {
        switch (code) {
            case 0:
                return INDIVIDUAL;
            case 1:
                return JOINT;
            case 2:
                return CORPORATE;
            default:
                return INDIVIDUAL;
        }
    }
}
