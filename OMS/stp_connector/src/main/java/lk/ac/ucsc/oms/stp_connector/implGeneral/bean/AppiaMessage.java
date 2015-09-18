package lk.ac.ucsc.oms.stp_connector.implGeneral.bean;

import lk.ac.ucsc.oms.stp_connector.api.bean.AppiaMessageInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.MapMessage;


public class AppiaMessage implements AppiaMessageInterface {
    private static Logger logger = LogManager.getLogger(AppiaMessage.class);
    private String protocol = "FIX";     //fix message protocol
    private String messageType;          // fix message type
    private String messageData;          // message data
    private int eventType;               // one of APPLICATION_MESSAGE, SESSION_CONNECTED, SESSION_DISCONNECTED, MESSAGE_VALIDATION_ERROR, MESSAGE_EXPIRED
    private String clientMsgID = null;   // client message id
    private String sessionID = null;     // session id of the connection
    private String sequenceNo = null;    // sequence number of the message
    private String metaData = null;      // any meta data associated with the message
    private String eventData = null;     // data related to the specific event
    private String format = "0";        // message format

    public AppiaMessage(String clientMessageId, String sessionId, String messageType, String messageData) {
        this.clientMsgID = clientMessageId;
        this.sessionID = sessionId;
        this.messageType = messageType;
        this.messageData = messageData;
    }


    public AppiaMessage(MapMessage mapMsg) {
        try {
            this.eventType = mapMsg.getInt("EventType");
            this.clientMsgID = mapMsg.getString("ClientMsgID");
            this.sessionID = mapMsg.getString("SessionID");
            this.protocol = mapMsg.getString("Protocol");
            this.messageType = mapMsg.getString("MessageType");
            this.sequenceNo = mapMsg.getString("SequenceNo");
            this.metaData = mapMsg.getString("MetaData");
            this.eventData = mapMsg.getString("EventData");
        } catch (Exception e) {
            logger.debug("Message parsing error ", e);
        }
    }

    @Override
    public int getEventType() {
        return eventType;
    }


    @Override
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    @Override
    public String getClientMsgID() {
        return clientMsgID;
    }

    @Override
    public void setClientMsgID(String clientMsgID) {
        this.clientMsgID = clientMsgID;
    }

    @Override
    public String getSessionID() {
        return sessionID;
    }

    @Override
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }


    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    @Override
    public String getSequenceNo() {
        return sequenceNo;
    }


    @Override
    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    @Override
    public String getMetaData() {
        return metaData;
    }

    @Override
    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }


    @Override
    public String getEventData() {
        return eventData;
    }

    @Override
    public void setEventData(String eventData) {
        this.eventData = eventData;
    }


    @Override
    public String getMessageData() {
        return messageData;
    }


    @Override
    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }


    @Override
    public String getFormat() {
        return format;
    }


    @Override
    public void setFormat(String format) {
        this.format = format;
    }
}
