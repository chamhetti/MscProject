package lk.ac.ucsc.oms.trade_controller.helper;

import lk.ac.ucsc.oms.common_utility.api.enums.Status;


public class ValidationReply {
    private final Status status;
    private final String rejectReasonText;
    private final int moduleCode;
    private final String errorMsgParameters;

    /**
     * Construct the validation reply for the successful validations
     *
     * @param status is the validation status
     */
    public ValidationReply(Status status) {
        this(status,  "");
    }

    /**
     * Constructs the validation reply for reject orders with reject reason and reject reason text
     *
     * @param status           the validation status
     * @param rejectReasonText is the reject reason text
     */
    public ValidationReply(Status status,  String rejectReasonText) {
        this.status = status;
        this.rejectReasonText = rejectReasonText;
        this.moduleCode = 1;
        this.errorMsgParameters = null;
    }

    /**
     * Constructs the validation reply for reject orders with reject reason and reject reason text and error message
     * parameter list
     *
     * @param status                the validation status
     * @param rejectReasonText      is the reject reason text
     * @param errorMsgParameterList is the error message parameter list
     */
    public ValidationReply(Status status,  String rejectReasonText, String errorMsgParameterList) {
        this.status = status;
        this.rejectReasonText = rejectReasonText;
        this.moduleCode = 1;
        this.errorMsgParameters = errorMsgParameterList;
    }

    public Status getStatus() {
        return status;
    }



    public String getRejectReasonText() {
        return rejectReasonText;
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public String getErrorMsgParameters() {
        return errorMsgParameters;
    }

    @Override
    public String toString() {
        return "ValidationReply{" +
                "status=" + status +
                ", rejectReasonText='" + rejectReasonText + '\'' +
                ", moduleCode=" + moduleCode +
                ", errorMsgParameters='" + errorMsgParameters + '\'' +
                '}';
    }
}
