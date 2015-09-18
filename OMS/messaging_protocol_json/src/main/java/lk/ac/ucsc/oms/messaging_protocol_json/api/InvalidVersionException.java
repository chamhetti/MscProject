package lk.ac.ucsc.oms.messaging_protocol_json.api;


public class InvalidVersionException extends MessageProtocolException {

    public InvalidVersionException(String message) {
        super(message);
    }

    public InvalidVersionException(String message, Throwable cause) {
        super(message, cause);
    }
}
