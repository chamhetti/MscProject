package lk.ac.ucsc.clientConnector.common.api;

/**
 * Created by amilasilva on 6/18/14.
 */
public class TrsException extends Exception {
    public TrsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrsException(String message) {
        super(message);
    }
}
