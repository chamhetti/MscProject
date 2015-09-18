package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/7/13
 * Time: 10:22 AM
 */

/**
 * gender of customer like MALE, FEMALE
 * string representation of enums
 * <ul>
 * <li>gender MALE   = 'M'</li>
 * <li>gender FEMALE = 'F'</li>
 * </ul>
 *
 */
public enum Gender {
    MALE('M'), FEMALE('F');
    private char code;

    /**
     * constructor of the Enum
     *
     * @param code of Gender
     */
    Gender(char code) {
        this.code = code;
    }

    /**
     * get code of the Enum
     *
     * @return code
     */
    public char getCode() {
        return code;
    }

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return AuthenticationType
     */
    public static Gender getEnum(int code) {
        switch (code) {
            case 'M':
                return MALE;
            case 'F':
                return FEMALE;
            default:
                return null;
        }
    }
}

