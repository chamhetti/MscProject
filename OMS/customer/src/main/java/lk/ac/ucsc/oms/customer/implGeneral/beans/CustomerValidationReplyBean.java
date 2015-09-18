package lk.ac.ucsc.oms.customer.implGeneral.beans;

import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.CustomerValidationReply;

/**
 * User: Hetti
 * Date: 2/13/13
 * Time: 2:47 PM
 *
 * implements the methods defined in customer validation reply interface with relevant variables
 */
public class CustomerValidationReplyBean implements CustomerValidationReply{
    private CustomerValidationResult validationResult;
    private CustomerStatusMessages rejectReason;

    /**
     * constructor with validation result and reject reason provided
     * @param validationResult
     * @param rejectReason
     */
    public CustomerValidationReplyBean(CustomerValidationResult validationResult, CustomerStatusMessages rejectReason) {
        this.validationResult = validationResult;
        this.rejectReason = rejectReason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerValidationResult getValidationResult() {
        return validationResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerStatusMessages getRejectReason() {
        return rejectReason;
    }
}
