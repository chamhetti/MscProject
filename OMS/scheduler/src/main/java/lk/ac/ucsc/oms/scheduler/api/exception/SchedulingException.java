package lk.ac.ucsc.oms.scheduler.api.exception;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This class is used to handled the exceptions thrown by the institution module.
 * All the institution module implementation related exceptions should thrown by using this class.
 * <p/>
 * User: dasun
 * Date: 10/16/12
 * Time: 5:07 PM
 */
public class SchedulingException extends OMSException {

    /**
     * Construct a new exception with the given detailed error message
     *
     * @param message of the error
     */
    public SchedulingException(String message) {
        super(message);
    }

    /**
     * Construct a new exception by using detailed error message and the exception thrown
     *
     * @param message of the error occurred
     * @param e       exception thrown
     */
    public SchedulingException(String message, Exception e) {
        super(message, e);
    }
}
