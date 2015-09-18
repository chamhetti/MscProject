package lk.ac.ucsc.oms.common_utility.api.exceptions;

/**
 * User: Hetti
 */

public class MessageSenderException extends OMSException {

    public MessageSenderException(String message, Exception e) {
        super(message, e);
    }

    public MessageSenderException(String message) {
        super(message);
    }
}
