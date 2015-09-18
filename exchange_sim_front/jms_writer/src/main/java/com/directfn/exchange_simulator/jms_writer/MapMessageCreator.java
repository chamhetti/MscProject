package com.directfn.exchange_simulator.jms_writer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.QueueSession;
import java.util.Map;


public class MapMessageCreator {

    public MapMessage createMapMessage(Map<String, String> map, QueueSession session) throws JMSException {
        MapMessage sendMsg = session.createMapMessage();
        sendMsg.setString("ClientMsgID", map.get("clientMessageId"));
        sendMsg.setString("EventType", map.get("eventType"));
        sendMsg.setString("SessionID", map.get("sessionId"));
        sendMsg.setString("Protocol", map.get("protocol"));
        sendMsg.setString("MessageType", map.get("messageType"));
        sendMsg.setString("EventData", map.get("eventData"));
        sendMsg.setString("MetaData", map.get("metaData"));
        return sendMsg;
    }
}
