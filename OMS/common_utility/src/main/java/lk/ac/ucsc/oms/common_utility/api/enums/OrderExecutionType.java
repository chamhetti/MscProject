package lk.ac.ucsc.oms.common_utility.api.enums;

/**
 * @author hetti
 */

public enum OrderExecutionType {

    QUEUED("0"),
    PARTIALLY_FILLED("1"),
    FILLED("2"),
    DONE_FOR_DAY("3"),
    CANCELED("4"),
    REPLACED("5"),
    PENDING_CANCEL("6"),
    STOPPED("7"),
    REJECTED("8"),
    SUSPENDED("9"),
    PENDING_NEW("A"),
    RESTATED("D"),
    PENDING_REPLACE("E"),
    TRADE("F"),
    DEFAULT("X");


    private String code;

    OrderExecutionType(String code) {
        this.code = code;
    }

    /**
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * convert string in to enum.to store in data base or using in fix strings
     * order execution Type property will convert to string. to revert this string to enum
     * this method can use.
     *
     * @param code string value of order execution type
     * @return appropriate enum for given string
     */
    public static OrderExecutionType getEnum(String code) {
        if(code==null){
            return DEFAULT;
        }
        switch (code) {
            case "0":
                return QUEUED;
            case "1":
                return PARTIALLY_FILLED;
            case "2":
                return FILLED;
            case "3":
                return DONE_FOR_DAY;
            case "4":
                return CANCELED;
            case "5":
                return REPLACED;
            case "6":
                return PENDING_CANCEL;
            case "7":
                return STOPPED;
            case "8":
                return REJECTED;
            case "9":
                return SUSPENDED;
            case "A":
                return PENDING_NEW;
            case "D":
                return RESTATED;
            case "E":
                return PENDING_REPLACE;
            case "F":
                return TRADE;
            case "X":
                return DEFAULT;
            default:
                return DEFAULT;
        }
    }
}

