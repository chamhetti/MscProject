package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 1/11/13
 * Time: 2:59 PM
 */

/**
 * Customer Status Messages like Fail, Success, .... etc
 * string representation of enums
 * <ul>
 * <li>customer status message Fail                       = 0 </li>
 * <li>customer status message Success                    = 1 </li>
 * <li>customer status message Invalid_Login_Name         = 2 </li>
 * <li>customer status message Password_Expired           = 3 </li>
 * <li>customer status message Invalid_Password           = 4 </li>
 * <li>customer status message Customer_Not_Found         = 5 </li>
 * <li>customer status message Account_Inactive           = 6 </li>
 * <li>customer status message Password_Empty             = 7 </li>
 * <li>customer status message CustomerNumber_Empty       = 8 </li>
 * <li>customer status message Trading_Password_Empty     = 9 </li>
 * <li>customer status message New_and_Old_Password_Same  = 10</li>
 * <li>customer status message Account_Number_Empty       = 11</li>
 * <li>customer status message Invalid_Trading_Password   = 12</li>
 * <li>customer status message Invalid_Input_Parameters   = 13</li>
 * <li>customer status message Invalid_Account            = 14</li>
 * <li>customer status message Inactive_Account           = 15</li>
 * <li>customer status message Invalid_Exchange_Account   = 16</li>
 * <li>customer status message Trading_Disable            = 17</li>
 * <li>customer status message Invalid_Cash_Account       = 18</li>
 * </ul>
 */
public enum CustomerStatusMessages {

    FAIL(0), SUCCESS(1), INVALID_LOGIN_NAME(2), PASSWORD_EXPIRED(3), INVALID_PASSWORD(4), CUSTOMER_NOT_FOUND(5), ACCOUNT_INACTIVE(6),
    PASSWORD_EMPTY(7), CUSTOMER_NUMBER_EMPTY(8), TRADING_PASSWORD_EMPTY(9), NEW_AND_OLD_PASSWORD_SAME(10),
    ACCOUNT_NUMBER_EMPTY(11), INVALID_TRADING_PASSWORD(12), INVALID_INPUT_PARAMETER(13), INVALID_ACCOUNT(14), INACTIVE_ACCOUNT(15),
    INVALID_EXCHANGE_ACCOUNT(16), TRADING_DISABLE(17), INVALID_CASH_ACCOUNT(18), SYSTEM_ERROR(19),
    INVALID_PARENT_ACCOUNT(20), INVALID_INSTITUTION(21), PRICE_USER_EXPIRED(22), NOT_MOVED_TO_FO(23), MAXIMUM_ATTEMPTS_EXCEEDED(24);

    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of CustomerStatusMessages
     */
    CustomerStatusMessages(int code) {
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
    public static CustomerStatusMessages getEnum(int code) {
        switch (code) {
            case 0:
                return FAIL;
            case 1:
                return SUCCESS;
            case 2:
                return INVALID_LOGIN_NAME;
            case 3:
                return PASSWORD_EXPIRED;
            case 4:
                return INVALID_PASSWORD;
            case 5:
                return CUSTOMER_NOT_FOUND;
            case 6:
                return ACCOUNT_INACTIVE;
            case 7:
                return PASSWORD_EMPTY;
            case 8:
                return CUSTOMER_NUMBER_EMPTY;
            case 9:
                return TRADING_PASSWORD_EMPTY;
            case 10:
                return NEW_AND_OLD_PASSWORD_SAME;
            case 11:
                return ACCOUNT_NUMBER_EMPTY;
            case 12:
                return INVALID_TRADING_PASSWORD;
            case 13:
                return INVALID_INPUT_PARAMETER;
            case 14:
                return INVALID_ACCOUNT;
            case 15:
                return INACTIVE_ACCOUNT;
            case 16:
                return INVALID_EXCHANGE_ACCOUNT;
            case 17:
                return TRADING_DISABLE;
            case 18:
                return INVALID_CASH_ACCOUNT;
            case 19:
                return SYSTEM_ERROR;
            case 20:
                return INVALID_PARENT_ACCOUNT;
            case 21:
                return INVALID_INSTITUTION;
            case 22:
                return PRICE_USER_EXPIRED;
            default:
                return SYSTEM_ERROR;
        }
    }
}
