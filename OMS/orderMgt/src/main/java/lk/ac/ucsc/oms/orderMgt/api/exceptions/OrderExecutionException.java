package lk.ac.ucsc.oms.orderMgt.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is an Exception class used to wrap all exceptions thrown in order execution related operations.
 *
 * @author: rajithv
 * Date: 2/15/13
 * Time: 4:18 PM
 */
public class OrderExecutionException extends OMSException {

    /**
     * Construct a new OrderExecutionException with the given detailed message
     *
     * @param message reason for the exception
     */
    public OrderExecutionException(String message) {
        super(message);
    }

    /**
     * Construct a new OrderExecutionException using the given detailed message and the thrown Exception
     *
     * @param message is the detail message
     * @param e       is the exception thrown
     */
    public OrderExecutionException(String message, Exception e) {
        super(message, e);
    }
}
