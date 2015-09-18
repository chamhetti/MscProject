package lk.ac.ucsc.oms.customer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all the exceptions thrown in cash account management related operations.
 *
 * User: Hetti
 * Date: 12/31/12
 * Time: 9:34 AM
 */
public class CashManagementException extends OMSException {

    /**
     * Construct a new CashManagementException with the given detailed message
     *
     * @param message reason for the exception
     */
    public CashManagementException(String message) {
        super(message);
    }

    /**
     * Construct a new CashManagementException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public CashManagementException(String message, Exception e) {
        super(message, e);
    }
}
