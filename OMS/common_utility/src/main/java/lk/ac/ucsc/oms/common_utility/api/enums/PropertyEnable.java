package lk.ac.ucsc.oms.common_utility.api.enums;


public enum PropertyEnable {
    NO(0), YES(1);
    private int code;

    PropertyEnable(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PropertyEnable getEnum(int code) {
        switch (code) {
            case 0:
                return NO;
            case 1:
                return YES;

            default:
                return NO;
        }
    }
}
