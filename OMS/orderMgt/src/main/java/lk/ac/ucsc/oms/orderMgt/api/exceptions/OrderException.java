package lk.ac.ucsc.oms.orderMgt.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all exceptions thrown in order management related operations.
 *
 * User: dasun
 * Date: 10/16/12
 * Time: 5:08 PM
 */
public class OrderException extends OMSException {

    /**
     * Construct a new OrderException with the given detailed message
     *
     * @param message reason for the exception
     */
    public OrderException(String message) {
        super(message);
    }

    /**
     * Construct a new OrderException using the given detailed message and the thrown Exception
     *
     * @param message is the detail message
     * @param e       is the exception thrown
     */
    public OrderException(String message, Exception e) {
        super(message, e);
    }
}
