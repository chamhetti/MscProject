package lk.ac.ucsc.clientConnector.api;

import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;


public interface OmsController extends Controller {

    /**
     * Place a message in controller to send to OMS
     *
     * @param message message to be sent
     */
    void placeMessage(TrsMessage message) throws ClientConnectorException;


}
