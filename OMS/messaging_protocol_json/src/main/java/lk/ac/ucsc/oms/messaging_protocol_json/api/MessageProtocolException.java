package lk.ac.ucsc.oms.messaging_protocol_json.api;

/**
 * @author : hetti
 *         Exception thrown by messaging protocol module
 */
public class MessageProtocolException extends Exception {

    public MessageProtocolException(String message) {
        super(message);
    }

    public MessageProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}
