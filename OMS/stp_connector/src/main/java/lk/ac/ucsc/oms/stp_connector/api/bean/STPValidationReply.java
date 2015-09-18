package lk.ac.ucsc.oms.stp_connector.api.bean;


import lk.ac.ucsc.oms.common_utility.api.enums.Status;


public interface STPValidationReply {


    Status getStatus();

    void setStatus(Status validationStatus);

    String getRejectReason();


    void setRejectReason(String rejectReason);

    String getRejectReasonText();

    void setRejectReasonText(String rejectReasonText);
}
