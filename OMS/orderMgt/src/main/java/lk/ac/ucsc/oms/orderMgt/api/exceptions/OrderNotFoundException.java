package lk.ac.ucsc.oms.orderMgt.api.exceptions;

/**
 * This is an Exception class used to throw when the required order not found.
 *
 * @author Thilina Jayamini
 */
public class OrderNotFoundException extends OrderException {

    /**
     * Construct a new OrderNotFoundException with the given detailed message
     *
     * @param message reason for the exception
     */
    public OrderNotFoundException(String message) {
        super(message);
    }

    /**
     * Construct a new OrderNotFoundException using the given detailed message and the thrown Exception
     *
     * @param message is the detail message
     * @param e       is the exception thrown
     */
    public OrderNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
