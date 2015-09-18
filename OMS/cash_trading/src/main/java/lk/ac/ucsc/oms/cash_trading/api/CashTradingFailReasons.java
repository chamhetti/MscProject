package lk.ac.ucsc.oms.cash_trading.api;


public enum CashTradingFailReasons {
    /**
     * Customer account loading problem
     */
    ACCOUNT_LOADING_ISSUE(1),
    /**
     * Parent currency rate not defined
     */
    PARENT_CURRENCY_RATE_NOT_FOUND(2),
    /**
     * Not enough buying power in the customer cash account
     */
    NOT_ENOUGH_BUYING_POWER(3),
    /**
     * Total over draft limit exceed
     */
    TOTAL_OD_LIMIT_EXCEED(4),
    /**
     * Customer loading problem
     */
    CUSTOMER_LOADING_ISSUE(5),
    /**
     * Cash account loading problem
     */
    CASH_ACCOUNT_LOADING_ISSUE(6),
    /**
     * Institution loading problem
     */
    INSTITUTION_LOADING_ISSUE(7),
    /**
     * Maximum day limit has exceed
     */
    MAX_DAY_LIMIT_EXCEEDED(8),

    /**
     * Not enough buying power parent
     */
    NOT_ENOUGH_PARENT_BUYING_POWER(9);


    private int code; //this is the enum code i.e code of the ACCOUNT_LOADING_ISSUE is 1
    private static final int ACC_LOAD_ISSUE = 1;
    private static final int PARENT_CURR_RATE_NOT = 2;
    private static final int NOT_ENOUGH_BP = 3;
    private static final int OD_LIMIT_EXCEED = 4;
    private static final int CUST_LOAD_ISSUE = 5;
    private static final int CASH_LOAD_ISSUE = 6;
    private static final int INST_LOAD_ISSUE = 7;
    private static final int MAX_DAY_LT_EXCEED = 8;
    private static final int NOT_ENOUGH_BP_PARENT = 9;

    CashTradingFailReasons(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CashTradingFailReasons getEnum(int code) {
        switch (code) {
            case ACC_LOAD_ISSUE:
                return ACCOUNT_LOADING_ISSUE;
            case PARENT_CURR_RATE_NOT:
                return PARENT_CURRENCY_RATE_NOT_FOUND;
            case NOT_ENOUGH_BP:
                return NOT_ENOUGH_BUYING_POWER;
            case OD_LIMIT_EXCEED:
                return TOTAL_OD_LIMIT_EXCEED;
            case CUST_LOAD_ISSUE:
                return CUSTOMER_LOADING_ISSUE;
            case CASH_LOAD_ISSUE:
                return CASH_ACCOUNT_LOADING_ISSUE;
            case INST_LOAD_ISSUE:
                return INSTITUTION_LOADING_ISSUE;
            case MAX_DAY_LT_EXCEED:
                return MAX_DAY_LIMIT_EXCEEDED;
            case NOT_ENOUGH_BP_PARENT:
                return NOT_ENOUGH_PARENT_BUYING_POWER;
            default:
                return null;
        }
    }
}
