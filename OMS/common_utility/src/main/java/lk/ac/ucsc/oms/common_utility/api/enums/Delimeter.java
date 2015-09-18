package lk.ac.ucsc.oms.common_utility.api.enums;

/**
 * @author hetti
 */
public enum Delimeter {
    FD("\u0001"), ID("\u0018"), DD("\u001E"), FS("\u001C"), DS("\u0002"), FIELD_SEPARATOR("\u001D"), COMMA_SEPARATOR(","),
    VL("\u007C"), TS("\u0019");
    private String code;

    Delimeter(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
