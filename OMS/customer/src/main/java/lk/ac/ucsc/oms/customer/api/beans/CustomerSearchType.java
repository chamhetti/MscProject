package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: vimalanathanr
 * Date: 5/13/13
 * Time: 11:36 AM
 */

/**
 * CustomerSearch type like CUSTOMER_NUMBER, FIRST_NAME, .... etc
 * string representation of enums
 * <ul>
 * <li>customer search type CUSTOMER_NUMBER = 1 </li>
 * <li>customer search type FIRST_NAME      = 2 </li>
 * <li>customer search type LAST_NAME       = 3 </li>
 * <li>customer search type INST_CODE       = 4 </li>
 * <li>customer search type CUSTOMER_TYPE   = 5 </li>
 * <li>customer search type ACCOUNT_TYPE    = 6 </li>
 * <li>customer search type NATIONALITY     = 7 </li>
 * <li>customer search type EMAIL           = 8 </li>
 * <li>customer search type MOBILE_NO       = 9 </li>
 * <li>customer search type OFFICE_TELE     = 10</li>
 * <li>customer search type RECORD_STATUS   = 11</li>
 * </ul>
 */
public enum CustomerSearchType {
    CUSTOMER_NUMBER(1), FIRST_NAME(2), LAST_NAME(3), INST_CODE(4), CUSTOMER_TYPE(5), ACCOUNT_TYPE(6), NATIONALITY(7), EMAIL(8), MOBILE_NO(9), OFFICE_TELE(10), RECORD_STATUS(11);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of CustomerSearchType
     */
    CustomerSearchType(int code) {
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

    private static final int CUS_NUM = 1;
    private static final int F_NAME = 2;
    private static final int L_NAME = 3;
    private static final int INST_ID = 4;
    private static final int CUS_TYPE = 5;
    private static final int ACC_TYPE = 6;
    private static final int NATIONAL = 7;
    private static final int MAIL = 8;
    private static final int MOBILE = 9;
    private static final int TEL = 10;
    private static final int STATUS = 11;

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return AuthenticationType
     */
    public static CustomerSearchType getEnum(int code) {
        switch (code) {
            case CUS_NUM:
                return CUSTOMER_NUMBER;
            case F_NAME:
                return FIRST_NAME;
            case L_NAME:
                return LAST_NAME;
            case INST_ID:
                return INST_CODE;
            case CUS_TYPE:
                return CUSTOMER_TYPE;
            case ACC_TYPE:
                return ACCOUNT_TYPE;
            case NATIONAL:
                return NATIONALITY;
            case MAIL:
                return EMAIL;
            case MOBILE:
                return MOBILE_NO;
            case TEL:
                return OFFICE_TELE;
            case STATUS:
                return RECORD_STATUS;
            default:
                return null;
        }

    }
}
