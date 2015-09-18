package lk.ac.ucsc.oms.common_utility.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.MessageSenderException;

import java.util.Map;


public interface MessageSender {

    void sendMessage(String queueName, String message) throws MessageSenderException;

    void sendMessage(String queueName, Map mapMessage) throws MessageSenderException;

    void publishMessage(String topicName, String message) throws MessageSenderException;


    void publishMessage(String topicName, Map mapMessage) throws MessageSenderException;
}

