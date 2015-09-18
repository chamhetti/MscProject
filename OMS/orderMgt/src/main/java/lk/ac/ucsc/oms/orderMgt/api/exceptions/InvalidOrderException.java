package lk.ac.ucsc.oms.orderMgt.api.exceptions;

/**
 * This is an Exception class used to throw when the order given in invalid.
 *
 * @author Thilina Jayamini
 */
public class InvalidOrderException extends OrderException {

    /**
     * Construct a new InvalidOrderException with the given detailed message
     *
     * @param message reason for the exception
     */
    public InvalidOrderException(String message) {
        super(message);
    }

    /**
     * Construct a new InvalidOrderException using the given detailed message and the thrown Exception
     *
     * @param message is the detail message
     * @param e       is the exception thrown
     */
    public InvalidOrderException(String message, Exception e) {
        super(message, e);
    }
}
