package lk.ac.ucsc.oms.customer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all the exceptions thrown in customer login related operations.
 *
 * User: Hetti
 * Date: 1/8/13
 * Time: 4:59 PM
 */
public class CustomerLoginException  extends OMSException {

    /**
     * Construct a new CustomerLoginException with the given detailed message
     *
     * @param message reason for the exception
     */
    public CustomerLoginException(String message) {
        super(message);
    }

    /**
     * Construct a new CustomerLoginException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public CustomerLoginException(String message, Exception e) {
        super(message, e);
    }
}
