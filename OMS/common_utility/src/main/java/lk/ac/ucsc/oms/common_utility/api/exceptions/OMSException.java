package lk.ac.ucsc.oms.common_utility.api.exceptions;


public class OMSException extends Exception {
    /**
     * Constructs a new OMS  Exception
     */
    public OMSException() {
        super();
    }

    /**
     * Constructs a new OMS  Exception
     */
    public OMSException(Exception e) {
        super(e);
    }

    /**
     * Constructs a new OMS Exception with detail message and the cause
     *
     * @param message is the message
     * @param e       is the cause
     */
    public OMSException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Constructs a new OMS  Exception with detail message only
     *
     * @param message is the message
     */
    public OMSException(String message) {
        super(message);
    }

    /**
     * Constructs a new OMS  Exception with detail message and throwable cause
     *
     * @param message is the message
     * @param cause   is the cause
     */
    public OMSException(String message, Throwable cause) {
        super(message, cause);
    }
}
