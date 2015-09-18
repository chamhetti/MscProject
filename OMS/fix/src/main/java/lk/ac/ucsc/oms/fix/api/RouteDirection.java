package lk.ac.ucsc.oms.fix.api;

/**
 * Define in the Message Route Direction
 * <p/>
 * IN("IN") - FIX Messages comes into the OMS
 * OUT("OUT") - FIX Messages goes out from the OMS
 *
 * @author
 *         Date: 11/29/13
 */
public enum RouteDirection {
    IN("IN"), OUT("OUT");

    String code;

    RouteDirection(String code) {
        this.code = code;
    }

    public RouteDirection getEnum(String code) {
        switch (code) {
            case "IN":
                return IN;
            case "OUT":
                return OUT;
            default:
                return null;
        }
    }
}
