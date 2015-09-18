package lk.ac.ucsc.clientConnector.front_office_connector.api.exception;

/**
 * This handles the exceptions thrown when sending messages via message sender
 *
 * @author Chathura, AmilaS
 *         Date: Dec 10, 2012
 */
public class MessageSenderException extends Exception {
    /**
     * @param message is the error message
     */
    public MessageSenderException(String message) {
        super(message);
    }

    /**
     * @param msg
     * @param e
     */
    public MessageSenderException(String msg, Exception e) {
        super(msg, e);
    }
}
