package lk.ac.ucsc.oms.orderMgt.api.beans;


import lk.ac.ucsc.oms.common_utility.api.enums.Status;

/**
 * Order validation reply for oms. this interface contains all the required variable getters setters for validation
 * reply message
 *
 * @auther: Chathura
 * Date: Feb 15, 2013
 */
public interface OrderValidationReply {

    /**
     * get status of the OrderValidationReply
     *
     * @return status
     */
    Status getStatus();

    /**
     * set status of the OrderValidationReply
     *
     * @param validationStatus {@inheritDoc} is the order validation reply
     */
    void setStatus(Status validationStatus);



    /**
     * get reject reason text from the order validation reply
     *
     * @return rejectionReasonText
     */
    String getRejectReasonText();

    /**
     * set rejection reason text in the order validation reply
     *
     * @param rejectReasonText is the order reject text
     */
    void setRejectReasonText(String rejectReasonText);

    String getErrorMsgParameters();

    void setErrorMsgParameters(String errorMsgParameters);
}
