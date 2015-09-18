package lk.ac.ucsc.oms.customer.api.exceptions;

/**
 * This is the Exception thrown when required holding not found.
 *
 * User: vimalanathanr
 * Date: 11/14/13
 * Time: 5:09 PM
 */
public class HoldingNotFoundException extends HoldingManagementException {

    /**
     * Construct a new HoldingNotFoundException with the given detailed message
     *
     * @param message reason for the exception
     */
    public HoldingNotFoundException(String message) {
        super(message);
    }

    /**
     * Construct a new HoldingNotFoundException using the given detailed message and the thrown Exception
     *
     * @param message detailed message
     * @param e exception thrown
     */
    public HoldingNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
