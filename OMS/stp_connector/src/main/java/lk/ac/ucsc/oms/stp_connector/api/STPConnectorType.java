package lk.ac.ucsc.oms.stp_connector.api;


public enum STPConnectorType {
    APPIA("appia"), STP("stp");

    private String code;
    private static final String TYPE_APPIA = "appia";
    private static final String TYPE_STP = "stp";


    STPConnectorType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    static STPConnectorType getEnum(String code) {

        switch (code) {
            case TYPE_APPIA:
                return APPIA;
            case TYPE_STP:
                return STP;
            default:
                return APPIA;
        }
    }
}
