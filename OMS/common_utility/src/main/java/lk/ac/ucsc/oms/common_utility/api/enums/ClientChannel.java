package lk.ac.ucsc.oms.common_utility.api.enums;


/**
 * @author hetti
 */
public enum ClientChannel{
    NON_EXISTING(0), WEB(1), TW(2), OMSCLIENT(3), MANUAL(4), FIX(5), TWS(6), AT(7), COND(8),
    MOBWEB(9), IVR(10), APPLET(11), DT(12), NON_EXISTING2(13), ANDROID_TAB(14), BLACKBERRY(15), IPHONE(16),
    IPAD(17), ANDROID(18), WEBAT(19), THIRD_PARTY(20), XT(21), MTP(22);
    private int code;

    ClientChannel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ClientChannel getEnum(int code) {
        ClientChannel[] values = ClientChannel.values();
        if (code < 0 || code >= values.length) {
            return NON_EXISTING;
        } else {
            return values[code];
        }
    }

}
