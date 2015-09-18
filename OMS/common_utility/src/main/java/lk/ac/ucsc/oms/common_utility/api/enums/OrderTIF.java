package lk.ac.ucsc.oms.common_utility.api.enums;


/**
 * @author hetti
 */
public enum OrderTIF  {
    DAY(0), GTC(1), AT_OPENING(2), IOC(3), FOK(4), GT_CROSSING(5), GTD(6), AT_CLOSE(7), G_THR_CRS(8), AT_CRS(9), NONE(-1);

    private static final int DAY_CODE = 0;
    private static final int GTC_CODE = 1;
    private static final int AT_OPENING_CODE = 2;
    private static final int IOC_CODE = 3;
    private static final int FOK_CODE = 4;
    private static final int GT_CROSSING_CODE = 5;
    private static final int GTD_CODE = 6;
    private static final int AT_CLOSE_CODE = 7;
    private static final int G_THR_CRS_CODE = 8;
    private static final int AT_CRS_CODE = 9;

    private int code;

    OrderTIF(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderTIF getEnum(int code) {
        switch (code) {
            case DAY_CODE:
                return DAY;
            case GTC_CODE:
                return GTC;
            case AT_OPENING_CODE:
                return AT_OPENING;
            case IOC_CODE:
                return IOC;
            case FOK_CODE:
                return FOK;
            case GT_CROSSING_CODE:
                return GT_CROSSING;
            case GTD_CODE:
                return GTD;
            case AT_CLOSE_CODE:
                return AT_CLOSE;
            case G_THR_CRS_CODE:
                return G_THR_CRS;
            case AT_CRS_CODE:
                return AT_CRS;
            default:
                return NONE;
        }

    }
}
