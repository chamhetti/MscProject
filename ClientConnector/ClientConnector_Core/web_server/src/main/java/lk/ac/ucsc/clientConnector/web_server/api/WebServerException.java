package lk.ac.ucsc.clientConnector.web_server.api;

/**
 * Common checked exception thrown by web server module <p/>
 * Date: Jan 4, 2013
 *
 * @author Chathura
 * @since 1.0
 */
public class WebServerException extends Exception {
    /**
     * {@inheritDoc}
     */
    public WebServerException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public WebServerException(String msg, Exception e) {
        super(msg, e);
    }
}
