package lk.ac.ucsc.oms.stp_connector.api.bean;


public interface AppiaMessageInterface {

    int getEventType();

    void setEventType(int eventType);

    String getClientMsgID();

    void setClientMsgID(String clientMsgID);

    String getSessionID();

    void setSessionID(String sessionID);

    String getProtocol();

    void setProtocol(String protocol);

    String getMessageType();

    void setMessageType(String messageType);

    String getSequenceNo();

    void setSequenceNo(String sequenceNo);

    String getMetaData();

    void setMetaData(String metaData);

    String getEventData();

    void setEventData(String eventData);

    String getMessageData();

    void setMessageData(String messageData);

    String getFormat();

    void setFormat(String format);
}
