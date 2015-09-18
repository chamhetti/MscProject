package lk.ac.ucsc.oms.rms_equity.api;


public enum EquityRiskFailReasons {
    /**
     * Price Block value is invalid
     */
    INVALID_PRICE_BLOCK(1),
    /**
     * Currency rate is not defined
     */
    CURRENCY_RATE_NOT_DEFINED(2),
    /**
     * Risk Management is failed
     */
    RISK_MANAGEMENT_FAIL(3),
    /**
     * There is no parent cash account found in the system
     */
    PARENT_CASH_ACCOUNT_NOT_FOUND(4),
    /**
     * Not enough money (funds) in the customer cash account
     */
    NOT_ENOUGH_FUNDS(5),
    /**
     * Customer holding record not found
     */
    HOLDING_RECORD_NOT_FOUND(6),
    /**
     * Not enough stocks found in the customer holding account
     */
    NOT_ENOUGH_STOCKS(7),
    /**
     * Order net value is negative
     */
    ORDER_NET_VALUE_NEGATIVE(8),
    /**
     * Commission structure, or commission is not defined
     */
    COMMISSION_NOT_DEFINED(9),
    /**
     * Current order exceed available net day holdings
     */
    ORDER_QTY_EXCEED_NET_DAY_HOLDING(10),

    /**
     * Child order of a desk order is not allowed
     */
    DESK_CHILD_ORDER_VALIDATION(11),
    /**
     * Child order of multiNIN order is not allowed
     */
    MULTI_NIN_CHILD_ORDER_VALIDATION(12);

    private int code; //is the enum code ex: code of the INVALID_PRICE_BLOCK is 1
    private static final int INVALID_PX_BLOCK = 1;
    private static final int CURR_RATE_NOT_DEFINED = 2;
    private static final int RISK_MGT_FAIL = 3;
    private static final int PARENT_CASH_ACC_NOT_FOUND = 4;
    private static final int NO_ENOUGH_FUNDS = 5;
    private static final int NO_HOLDING_RECORD = 6;
    private static final int NO_STOCKS = 7;
    private static final int NET_VALUE_NEGATIVE = 8;
    private static final int COMM_NOT_DEFINED = 9;
    private static final int ORDER_QTY_EXCEED = 10;
    private static final int DESK_INVALID_CHILD_ORDER = 11;
    private static final int MULTI_NIN_INVALID_CHILD_ORDER = 12;

    EquityRiskFailReasons(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Get the Equity risk failed reasons
     *
     * @param code is the enum code
     * @return the enum
     */
    public static EquityRiskFailReasons getEnum(int code) {
        switch (code) {
            case INVALID_PX_BLOCK:
                return INVALID_PRICE_BLOCK;
            case CURR_RATE_NOT_DEFINED:
                return CURRENCY_RATE_NOT_DEFINED;
            case RISK_MGT_FAIL:
                return RISK_MANAGEMENT_FAIL;
            case PARENT_CASH_ACC_NOT_FOUND:
                return PARENT_CASH_ACCOUNT_NOT_FOUND;
            case NO_ENOUGH_FUNDS:
                return NOT_ENOUGH_FUNDS;
            case NO_HOLDING_RECORD:
                return HOLDING_RECORD_NOT_FOUND;
            case NO_STOCKS:
                return NOT_ENOUGH_STOCKS;
            case NET_VALUE_NEGATIVE:
                return ORDER_NET_VALUE_NEGATIVE;
            case COMM_NOT_DEFINED:
                return COMMISSION_NOT_DEFINED;
            case ORDER_QTY_EXCEED:
                return ORDER_QTY_EXCEED_NET_DAY_HOLDING;
            case DESK_INVALID_CHILD_ORDER:
                return DESK_CHILD_ORDER_VALIDATION;
            case MULTI_NIN_INVALID_CHILD_ORDER:
                return MULTI_NIN_CHILD_ORDER_VALIDATION;
            default:
                return null;
        }
    }
}
