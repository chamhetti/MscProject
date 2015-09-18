package lk.ac.ucsc.oms.orderMgt.implGeneral.bean;


import lk.ac.ucsc.oms.common_utility.api.enums.Status;
import lk.ac.ucsc.oms.orderMgt.api.beans.OrderValidationReply;


public class OrderValidationReplyBean implements OrderValidationReply {
    private Status status;
    private String rejectReasonText;
    private String errorMsgParameters;

    public OrderValidationReplyBean() {

    }


    public OrderValidationReplyBean(Status status,  String rejectReasonText) {
        this.status = status;
        this.rejectReasonText = rejectReasonText;
        this.errorMsgParameters = null;
    }


    public OrderValidationReplyBean(Status status,  String rejectReasonText, String errorMsgParameterList) {
        this.status = status;
        this.rejectReasonText = rejectReasonText;
        this.errorMsgParameters = errorMsgParameterList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(Status validationStatus) {
        this.status = validationStatus;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String getRejectReasonText() {
        return rejectReasonText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRejectReasonText(String rejectReasonText) {
        this.rejectReasonText = rejectReasonText;
    }


    @Override
    public String getErrorMsgParameters() {
        return errorMsgParameters;
    }

    @Override
    public void setErrorMsgParameters(String errorMsgParameters) {
        this.errorMsgParameters = errorMsgParameters;
    }
}
