package lk.ac.ucsc.oms.messaging_protocol_json.impl;

import lk.ac.ucsc.oms.messaging_protocol_json.api.InvalidVersionException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacade;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import com.google.gson.Gson;

import java.util.Map;

/**
 * @author hetti
 */
public class MessageProtocolFacadeImpl implements MessageProtocolFacade {
    private Gson jsonParser;
    private Map<String, String> requestMap;

    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope getEnvelope(String message) {
        return jsonParser.fromJson(message, EnvelopeImpl.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Header getHeaderFromEnvelop(String envelopeString) throws MessageProtocolException {
        Object messageEnvelop;
        try {
            messageEnvelop = jsonParser.fromJson(envelopeString, EnvelopeImpl.class);
            return ((EnvelopeImpl) messageEnvelop).getHeader();
        } catch (Exception e) {
            throw new MessageProtocolException("Error parsing message", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageFromEnvelop(Header header, String envelopeString) throws MessageProtocolException {

        int headerVersion = parseVersion(header.getProtocolVersion());
        String key = header.getMsgGroup() + "-" + header.getMsgType();
        String messageClass = requestMap.get(key);
        if (messageClass == null) {
            throw new MessageProtocolException("No such message: " + key);
        }

        String messageClassFqn = "lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver"
                + headerVersion + ".envelop." + messageClass;

        try {
            Class specificClass = Class.forName(messageClassFqn);
            Envelope specificEnvelope = (Envelope) jsonParser.fromJson(envelopeString, specificClass);

            return (Message) specificEnvelope.getBody();
        } catch (ClassNotFoundException e) {
            throw new InvalidVersionException("Invalid version");
        } catch (Exception e) {
            throw new MessageProtocolException("Error parsing message of type " + header.getMsgGroup() + "-" + header.getMsgType() + " from channel: " + header.getChannelId(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageFromEnvelop(String envelopeString) throws MessageProtocolException {
        try {
            EnvelopeImpl messageEnvelop = jsonParser.fromJson(envelopeString, EnvelopeImpl.class);
            Header header = messageEnvelop.getHeader();
            return getMessageFromEnvelop(header, envelopeString);
        } catch (MessageProtocolException e) {
            throw e;
        } catch (Exception e) {
            throw new MessageProtocolException("Error parsing message", e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Message> T getMessageFromBodyString(String body, Class<T> type) throws MessageProtocolException {
        try {
            return jsonParser.fromJson(body, type);
        } catch (Exception e) {
            throw new MessageProtocolException("Error parsing message", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJSonString(Envelope message) {
        return jsonParser.toJson(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJSonString(Header header, Message body) {
        Envelope envelope = new EnvelopeImpl((HeaderImpl) header, body);
        return jsonParser.toJson(envelope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int parseVersion(String version) throws InvalidVersionException {
        // format: DFN_JSON_xxx.0
        if (version == null || version.isEmpty()) {
            throw new InvalidVersionException("Invalid version");
        }
        try {
            String versionDigit = version.substring(9);
            versionDigit = versionDigit.substring(0, versionDigit.length() - 2);
            int parsedVersion = Integer.parseInt(versionDigit);
            return parsedVersion;
        } catch (Exception e) {
            throw new InvalidVersionException("Invalid version");
        }

    }

    public void setJsonParser(Gson jsonParser) {
        this.jsonParser = jsonParser;
    }

    public void setRequestMap(Map<String, String> requestMap) {
        this.requestMap = requestMap;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getJSonString(Object messageData) {
        return jsonParser.toJson(messageData);
    }
}
