package lk.ac.ucsc.oms.orderMgt.api.exceptions;

/**
 * This is an Exception class used to throw when the order status in invalid.
 *
 * @author Thilina Jayamini
 */
public class InvalidOrderStatusException extends OrderException {

    /**
     * Construct a new InvalidOrderStatusException with the given detailed message
     *
     * @param message reason for the exception
     */
    public InvalidOrderStatusException(String message) {
        super(message);
    }

    /**
     * Construct a new InvalidOrderStatusException using the given detailed message and the thrown Exception
     *
     * @param message is the detail message
     * @param e       is the exception thrown
     */
    public InvalidOrderStatusException(String message, Exception e) {
        super(message, e);
    }
}
