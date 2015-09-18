package lk.ac.ucsc.oms.customer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * User: Hetti
 * Date: 11/1/13
 * Time: 11:20 AM
 */
public class CustomerValidationException extends OMSException {

    /**
     * Construct a new CustomerValidationException with the given detailed message
     *
     * @param message reason for the exception
     */
    public CustomerValidationException(String message) {
        super(message);
    }

    /**
     * Construct a new CustomerValidationException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public CustomerValidationException(String message, Exception e) {
        super(message, e);
    }
}
