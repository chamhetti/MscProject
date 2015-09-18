package lk.ac.ucsc.oms.orderMgt.api;

/**
 * User: Hetti
 * Date: 10/7/13
 * Time: 12:46 PM
 */
public enum OrderRejectReason {
 INVALID_ORDER(1),INVALID_SYMBOL(2),INVALID_SIDE(3);

    private int ordRejectCode;

    private static final int INV_ORD=1;
    private static final int INV_SYM =2;
    private static final int INV_SIDE=3;

    OrderRejectReason(int code) {
        this.ordRejectCode = code;
    }

    public int getCode() {
        return ordRejectCode;
    }
    public static OrderRejectReason getEnum(int code) {
        switch (code) {
            case INV_ORD:
                return INVALID_ORDER;
            case INV_SYM:
                return INVALID_SYMBOL;
            case INV_SIDE:
                return INVALID_SIDE;
            default:
                return null;
        }
    }
}
