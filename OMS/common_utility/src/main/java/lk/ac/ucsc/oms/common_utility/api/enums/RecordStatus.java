package lk.ac.ucsc.oms.common_utility.api.enums;


public enum RecordStatus {
    PENDING_APPROVAL(1), APPROVED(2), REJECTED(3), PENDING_DELETE(4), DELETED(5);

    private static final int PENDING_APPROVAL_CODE = 1;
    private static final int APPROVED_CODE = 2;
    private static final int REJECTED_CODE = 3;
    private static final int PENDING_DELETE_CODE = 4;
    private static final int DELETED_CODE = 5;

    private int code;

    RecordStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RecordStatus getEnum(int code) {
        switch (code) {
            case PENDING_APPROVAL_CODE:
                return PENDING_APPROVAL;
            case APPROVED_CODE:
                return APPROVED;
            case REJECTED_CODE:
                return REJECTED;
            case PENDING_DELETE_CODE:
                return PENDING_DELETE;
            case DELETED_CODE:
                return DELETED;
            default:
                return APPROVED;
        }
    }

}
