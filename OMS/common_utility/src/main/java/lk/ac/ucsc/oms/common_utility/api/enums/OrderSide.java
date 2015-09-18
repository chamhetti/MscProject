package lk.ac.ucsc.oms.common_utility.api.enums;
/**
 * @author hetti
 */
public enum OrderSide  {
    /**
     * this order is buy order
     */
    BUY("1"),
    /**
     * this order is sel order
     */
    SELL("2"),


    /**
     * for testing purposes
     */
    NON_EXISTENT("-1");


    private String code;

    OrderSide(String c) {
        this.code = c;
    }

    /**
     * @return string code of the enum
     */
    public String getCode() {
        return this.code;
    }

    public static OrderSide getEnum(String code) {
        if (code == null) {
            return null;
        }
        switch (code) {
            case "1":
                return BUY;
            case "2":
                return SELL;
            default:
                return null;
        }
    }
}
