package lk.ac.ucsc.oms.common_utility.api.enums;

/**
 * @author hetti
 */
public enum OrderCategory {
    /**
     * Order category normal
     */
    NORMAL(1),
    /**
     * Order category offline orders
     */
    OFFLINE(2),
    /**
     * Order category conditional
     */
    CONDITIONAL(3),
    /**
     * Order category algorithmic
     */
    SLICE(4),
    /**
     * Order category desk orders
     */
    DESK(5),
    /**
     * Order category child orders
     */
    CHILD(6),
    /**
     * Order category advance orders
     */
    ADVANCE(7);

    private int code;

    OrderCategory(int c) {
        this.code = c;
    }

    /**
     * @return code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * @param code String Market Code
     * @return type
     */
    public static OrderCategory getEnum(int code) {
        switch (code) {
            case 1:
                return NORMAL;
            case 2:
                return OFFLINE;
            case 3:
                return CONDITIONAL;
            case 4:
                return SLICE;
            case 5:
                return DESK;
            case 6:
                return CHILD;
            case 7:
                return ADVANCE;
            default:
                return null;
        }
    }
}
