package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException;


public interface MessageHandler {

    /**
     * Process the json message from the client and send responses to the client/s
     *
     * @param header
     * @param message
     * @throws lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException
     */
    Envelope processMessage(Header header, Message message) throws NoSuchProcessException;
}
