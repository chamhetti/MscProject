package lk.ac.ucsc.oms.common_utility.api.enums;


/**
 * @author hetti
 */
public enum OrderAction  {
    BUY("BUY"),
    SELL("SELL"),
    CHANGE("CHANGE"),
    CANCEL("CANCEL"),
    APPROVE("APPROVE"),
    REJECT("REJECT"),
    ACCEPT("ACCEPT"),
    EXPIRE("EXPIRE"),
    REVERSE("REVERSE");

    private String code;

    OrderAction(String action) {
        this.code = action;
    }


    public String getCode() {
        return code;
    }

    /**
     * @param code is the order action
     * @return the order action
     */
    public static OrderAction getEnum(String code) {
        switch (code) {
            case "BUY":
                return BUY;
            case "SELL":
                return SELL;
            case "CHANGE":
                return CHANGE;
            case "CANCEL":
                return CANCEL;
            case "APPROVE":
                return APPROVE;
            case "REJECT":
                return REJECT;
            case "ACCEPT":
                return ACCEPT;
            case "EXPIRE":
                return EXPIRE;
            case "REVERSE":
                return REVERSE;
            default:
                return null;
        }
    }
}
