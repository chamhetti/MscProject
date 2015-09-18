package lk.ac.ucsc.clientConnector.exceptions;


public class ClientConnectorException extends Exception {
    /**
     * @param message is the error message
     */
    public ClientConnectorException(String message) {
        super(message);
    }

    /**
     * @param message error message
     * @param cause   cause
     */
    public ClientConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
