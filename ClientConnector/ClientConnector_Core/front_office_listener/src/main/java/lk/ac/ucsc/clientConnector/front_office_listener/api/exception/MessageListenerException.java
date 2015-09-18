package lk.ac.ucsc.clientConnector.front_office_listener.api.exception;

/**
 * User: Chathura
 * Date: Jan 8, 2013
 */
public class MessageListenerException extends Exception {
    /**
     * @param message is the error message
     */
    public MessageListenerException(String message) {
        super(message);
    }

    /**
     * @param msg
     * @param e
     */
    public MessageListenerException(String msg, Exception e) {
        super(msg, e);
    }
}
