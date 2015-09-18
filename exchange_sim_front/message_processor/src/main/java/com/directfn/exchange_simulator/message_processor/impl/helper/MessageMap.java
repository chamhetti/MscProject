package com.directfn.exchange_simulator.message_processor.impl.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Ruchira Ranaweera
 * Date: 5/8/14
 */
public class MessageMap {
    private Map<String, String> messageMap = new HashMap<>();

    public MessageMap(String sessionId, String messageType, String protocol, String eventData, String sequenceNumber, String clientMessageId, String eventType, String metaData) {
        this.messageMap.put("sessionId", sessionId);
        this.messageMap.put("messageType", messageType);
        this.messageMap.put("protocol", protocol);
        this.messageMap.put("eventData", eventData);
        this.messageMap.put("sequenceNumber", sequenceNumber);
        this.messageMap.put("clientMessageId", clientMessageId);
        this.messageMap.put("eventType", eventType);
        this.messageMap.put("metaData", metaData);
    }

    public Map<String, String> getMessageMap() {
        return messageMap;
    }
}
