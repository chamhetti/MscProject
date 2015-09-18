package com.directfn.exchange_simulator.message_processor.impl.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.MapMessage;


public class FixMessageBean {
    private static Logger logger = LoggerFactory.getLogger(FixMessageBean.class);

    private String protocol = "FIX";     //fix message protocol
    private String messageType;          // fix message type
    private String messageData;          // message data
    private int eventType;               // one of APPLICATION_MESSAGE, SESSION_CONNECTED, SESSION_DISCONNECTED, MESSAGE_VALIDATION_ERROR, MESSAGE_EXPIRED
    private String clientMsgID = null;   // client message id
    private String sessionID = null;     // session id of the connection
    private String sequenceNo = null;    // sequence number of the message
    private String metaData = null;      // any meta data associated with the message
    private String eventData = null;     // data related to the specific event
    private String format = null;        // message format
    private String clientType;

    public FixMessageBean() {

    }

    public FixMessageBean(MapMessage mapMsg) {
        try {
            this.protocol = mapMsg.getString("Protocol");
            this.messageType = mapMsg.getString("MessageType");
            this.messageData = mapMsg.getString("MessageData");
            if (mapMsg.getString("EventType") != null) {
                this.eventType = mapMsg.getInt("EventType");
            }
            this.clientMsgID = mapMsg.getString("ClientMsgID");
            this.sessionID = mapMsg.getString("SessionID");
            this.sequenceNo = mapMsg.getString("SequenceNo");
            this.metaData = mapMsg.getString("MetaData");
            this.eventData = mapMsg.getString("EventData");
            this.format = mapMsg.getString("Format");
        } catch (Exception e) {
            logger.error("error while creating fix message bean", e);
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getClientMsgID() {
        return clientMsgID;
    }

    public void setClientMsgID(String clientMsgID) {
        this.clientMsgID = clientMsgID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
