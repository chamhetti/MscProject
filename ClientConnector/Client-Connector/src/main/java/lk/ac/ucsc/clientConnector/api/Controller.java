package lk.ac.ucsc.clientConnector.api;

import lk.ac.ucsc.clientConnector.front_office_listener.api.exception.MessageListenerException;
import lk.ac.ucsc.clientConnector.web_server.api.WebServerException;


public interface Controller {

    /**
     * Initializes the controller
     */
    void initController() throws WebServerException;

    /**
     * Starts the controller
     */
    boolean startController() throws MessageListenerException;

    /**
     * Stops the controller
     */
    boolean stopController();

}
