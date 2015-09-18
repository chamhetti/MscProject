package lk.ac.ucsc.oms.stp_connector.implGeneral.bean;


import lk.ac.ucsc.oms.common_utility.api.enums.Status;
import lk.ac.ucsc.oms.stp_connector.api.bean.STPValidationReply;


public class STPValidationReplyImpl implements STPValidationReply {
    private Status status;
    private String rejectReason;
    private String rejectReasonText;


    public STPValidationReplyImpl() {
    }


    public STPValidationReplyImpl(Status status,  String rejectReasonText) {
        this.status = status;
        this.rejectReason = rejectReasonText;
        this.rejectReasonText = rejectReasonText;
    }

    @Override
    public Status getStatus() {
        return status;
    }


    @Override
    public void setStatus(Status validationStatus) {
        this.status = validationStatus;
    }

    @Override
    public String getRejectReason() {
        return rejectReason;
    }


    @Override
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Override
    public String getRejectReasonText() {
        return rejectReasonText;
    }

    @Override
    public void setRejectReasonText(String rejectReasonText) {
        this.rejectReasonText = rejectReasonText;
    }
}
