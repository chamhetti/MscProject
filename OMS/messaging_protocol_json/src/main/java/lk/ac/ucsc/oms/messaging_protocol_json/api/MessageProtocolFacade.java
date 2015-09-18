package lk.ac.ucsc.oms.messaging_protocol_json.api;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;

/**
 * @author hetti
 */
public interface MessageProtocolFacade {

    Envelope getEnvelope(String envelopeString) throws MessageProtocolException;

    Header getHeaderFromEnvelop(String envelopeString) throws MessageProtocolException;

    Message getMessageFromEnvelop(Header header, String envelopeString) throws MessageProtocolException;

    Message getMessageFromEnvelop(String envelopeString) throws MessageProtocolException;

    <T extends Message> T getMessageFromBodyString(String bodyString, Class<T> type) throws MessageProtocolException;

    String getJSonString(Envelope messageEnvelope);

    String getJSonString(Header header, Message message);

    int parseVersion(String version) throws InvalidVersionException;

    String getJSonString(Object messageData);
}
