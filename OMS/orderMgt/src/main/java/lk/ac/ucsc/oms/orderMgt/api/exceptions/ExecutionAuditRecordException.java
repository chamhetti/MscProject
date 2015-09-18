package lk.ac.ucsc.oms.orderMgt.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all exceptions thrown in execution audit records related operations.
 *
 * @author Chathura
 *         Date: 5/30/14
 */
public class ExecutionAuditRecordException extends OMSException {
    /**
     * Construct a new ExecutionAuditRecordException with the given detailed message
     *
     * @param message reason for the exception
     */
    public ExecutionAuditRecordException(String message) {
        super(message);
    }

    /**
     * Construct a new ExecutionAuditRecordException using the given detailed message and the thrown Exception
     *
     * @param message is the detail message
     * @param e       is the exception thrown
     */
    public ExecutionAuditRecordException(String message, Exception e) {
        super(message, e);
    }
}
