package lk.ac.ucsc.clientConnector.api;

import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;


public interface MessageRouter {

    /**
     * Sets the front office controller
     *
     * @param frontOfficeController front office controller
     */
    void setFrontOfficeController(OmsController frontOfficeController);


    /**
     * Adds a new message to be routed
     *
     * @param message message to be routed
     */
    void putMessage(TrsMessage message) throws ClientConnectorException;
}
