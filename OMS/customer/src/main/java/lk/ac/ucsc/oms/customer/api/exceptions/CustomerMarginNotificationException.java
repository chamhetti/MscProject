package lk.ac.ucsc.oms.customer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all the exceptions thrown in customer margin notification related operations.
 *
 * User: vimalanathanr
 * Date: 12/3/13
 * Time: 1:32 PM
 */
public class CustomerMarginNotificationException extends OMSException {

    /**
     * Construct a new CustomerMarginNotificationException with the given detailed message
     *
     * @param message reason for the exception
     */
    public CustomerMarginNotificationException(String message) {
        super(message);
    }

    /**
     * Construct a new CustomerMarginNotificationException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public CustomerMarginNotificationException(String message, Exception e) {
        super(message, e);
    }
}
