package lk.ac.ucsc.oms.customer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all the exceptions thrown in customer related operations.
 *
 * User: Hetti
 * Date: 12/26/12
 * Time: 12:29 PM
 */
public class CustomerException extends OMSException {

    /**
     * Construct a new CustomerException with the given detailed message
     *
     * @param message reason for the exception
     */
    public CustomerException(String message) {
        super(message);
    }

    /**
     * Construct a new CustomerException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public CustomerException(String message, Exception e) {
        super(message, e);
    }
}
