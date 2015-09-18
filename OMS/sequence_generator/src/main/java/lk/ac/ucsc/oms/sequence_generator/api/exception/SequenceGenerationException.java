package lk.ac.ucsc.oms.sequence_generator.api.exception;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * The checked exception thrown by sequence generator module
 *
 */
public class SequenceGenerationException extends OMSException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SequenceGenerationException(String message) {
        super(message);
    }

    public SequenceGenerationException(String message, Exception cause) {
        super(message, cause);
    }
}
