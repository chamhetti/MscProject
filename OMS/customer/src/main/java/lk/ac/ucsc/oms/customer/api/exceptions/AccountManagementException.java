package lk.ac.ucsc.oms.customer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all exceptions thrown in customer account management related operations.
 *
 * User: Hetti
 * Date: 1/11/13
 * Time: 10:49 AM
 */
public class AccountManagementException extends OMSException {

    /**
     * Construct a new AccountManagementException with the given detailed message
     *
     * @param message reason for the exception
     */
    public AccountManagementException(String message) {
        super(message);
    }

    /**
     * Construct a new AccountManagementException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public AccountManagementException(String message, Exception e) {
        super(message, e);
    }
}
