package lk.ac.ucsc.oms.customer.api.beans.login;


public enum LoginReplyStatus {
    LOGIN_FAILED(0), LOGIN_SUCCESS(1), IS_FIRST_TIME(2), SMS_OPT_REQUIRED(3), PASSWORD_EXPIRED(4), LOG_OUT(5);

    private int code;

    /**
     * constructor of the enum class
     *
     * @param code of the reply status
     */
    LoginReplyStatus(int code) {
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

    private static final int FAILED = 0;
    private static final int SUCCESS = 1;
    private static final int FIRST_TIME = 2;
    private static final int SMS_OPT = 3;
    private static final int PASS_EXPIRED = 4;
    private static final int LOGGED_OUT = 5;

    /**
     * get enum value
     *
     * @param code of the enum
     * @return loginReplyStatus
     */
    public static LoginReplyStatus getEnum(int code) {
        switch (code) {
            case FAILED:
                return LOGIN_FAILED;
            case SUCCESS:
                return LOGIN_SUCCESS;
            case FIRST_TIME:
                return IS_FIRST_TIME;
            case SMS_OPT:
                return SMS_OPT_REQUIRED;
            case PASS_EXPIRED:
                return PASSWORD_EXPIRED;
            case LOGGED_OUT:
                return LOG_OUT;
            default:
                return LOGIN_FAILED;
        }
    }


}
