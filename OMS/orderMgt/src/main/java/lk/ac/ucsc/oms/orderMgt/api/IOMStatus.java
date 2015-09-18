package lk.ac.ucsc.oms.orderMgt.api;

/**
 * User: Hetti
 * Date: 8/10/13
 * Time: 12:53 AM
 */

/**
 * IOMStatus like DEFAULT, INTERNALLY_MATCH, .....etc
 * string representation of enums
 * <ul>
 * <li>IOMStatus DEFAULT                                = 0</li>
 * <li>IOMStatus INTERNALLY_MATCH                       = 1</li>
 * <li>IOMStatus PENDING_FOR_INTERNALLY_MATCH           = 2</li>
 * <li>IOMStatus PENDING_CANCEL_FOR_INTERNALLY_MATCH    = 3</li>
 * <li>IOMStatus CANCELED_FOR_INTERNALLY_MATCH          = 4</li>
 * </ul>
 */
public enum IOMStatus {
    DEFAULT(0),
    INTERNALLY_MATCH(1),
    PENDING_FOR_INTERNALLY_MATCH(2),
    PENDING_CANCEL_FOR_INTERNALLY_MATCH(3),
    CANCELED_FOR_INTERNALLY_MATCH(4);

    private int code;

    IOMStatus(int code) {
        this.code = code;
    }

    /**
     * @return code
     */
    public int getCode() {
        return this.code;
    }

    public static IOMStatus getEnum(int code) {
        switch (code) {
            case 0:
                return DEFAULT;
            case 1:
                return INTERNALLY_MATCH;
            case 2:
                return PENDING_FOR_INTERNALLY_MATCH;
            case 3:
                return PENDING_CANCEL_FOR_INTERNALLY_MATCH;
            case 4:
                return CANCELED_FOR_INTERNALLY_MATCH;
            default:
                return DEFAULT;
        }
    }
}
