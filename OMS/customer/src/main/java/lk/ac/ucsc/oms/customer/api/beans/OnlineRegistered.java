package lk.ac.ucsc.oms.customer.api.beans;

/**
 * @Author: thakshilad
 * Date: 8/30/13
 * Time: 3:29 PM
 */
public enum OnlineRegistered {

    AT(0),ONLINE(1), INTERNATIONAL(2);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of OnlineRegistered
     */
    OnlineRegistered(int code) {
        this.code = code;
    }

    /**
     * get code of the Enum
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return AuthenticationType
     */
    public static OnlineRegistered getEnum(int code) {
        switch (code) {
            case 0:
                return AT;
            case 1:
                return ONLINE;
            case 2:
                return INTERNATIONAL;
            default:
                return INTERNATIONAL;
        }
    }

}
