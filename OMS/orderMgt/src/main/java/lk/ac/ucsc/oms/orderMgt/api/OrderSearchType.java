package lk.ac.ucsc.oms.orderMgt.api;

/**
 * @author: Shashikak
 */

/**
 * Order Filter Type
 * string representation of enums
 * <ul>
 * <li>order history search type SYMBOL = 1</li>
 * <li>order history search type ORDER_STATUS = 2</li>
 * <li>order history search type ORDER_NO = 3</li>
 * <li>order history search type EXCHANGE = 4</li>
 * <li>order history search type INSTITUTION_ID =5</li>
 * <li>order history search type SECURITY_ACCOUNT =6</li>
 * <li>order history search type ORDER_SIDE =7</li>
 * <li>order history search type ORDER_TYPE =8</li>
 * <li>order history search type SECURITY_TYPE =9</li>
 * <li>order history search type MARKET_CODE =10</li>
 * <li>order history search type CHANNEL =11</li>
 * <li>order history search type INSTITUTION =12</li>
 * </ul>
 */
public enum OrderSearchType {

    SYMBOL(1),
    ORDER_STATUS(2),
    ORDER_NO(3),
    EXCHANGE(4),
    INSTITUTION_ID(5),
    SECURITY_ACCOUNT(6),
    ORDER_SIDE(7),
    ORDER_TYPE(8),
    SECURITY_TYPE(9),
    MARKET_CODE(10),
    CHANNEL(11),
    INSTITUTION(12);


    private int code;

    OrderSearchType(int c) {
        this.code = c;
    }

    /**
     * @return code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * @param code of the enum
     * @return orderSearchType
     */
    public static OrderSearchType getEnum(int code) {
        switch (code) {
            case 1:
                return SYMBOL;
            case 2:
                return ORDER_STATUS;
            case 3:
                return ORDER_NO;
            case 4:
                return EXCHANGE;
            case 5:
                return INSTITUTION_ID;
            case 6:
                return SECURITY_ACCOUNT;
            case 7:
                return ORDER_SIDE;
            case 8:
                return ORDER_TYPE;
            case 9:
                return SECURITY_TYPE;
            case 10:
                return MARKET_CODE;
            case 11:
                return CHANNEL;
            case 12:
                return INSTITUTION;
            default:
                return null;
        }
    }
}