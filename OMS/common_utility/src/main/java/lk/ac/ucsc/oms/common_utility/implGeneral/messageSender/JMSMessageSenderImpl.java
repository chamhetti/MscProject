package lk.ac.ucsc.oms.common_utility.implGeneral.messageSender;

import lk.ac.ucsc.oms.common_utility.api.MessageSender;
import lk.ac.ucsc.oms.common_utility.api.exceptions.MessageSenderException;

import java.util.HashMap;
import java.util.Map;

public class JMSMessageSenderImpl implements MessageSender {
    private static Map<String, JMSSender> queueStore = new HashMap<>();


    @Override
    public void sendMessage(String queueName, String message) throws MessageSenderException {
        JMSSender sender = getQueueStore().get(queueName);
        if (sender == null) {
            sender = getJMSSender();
            sender.initializeQueueConnection(queueName);
            getQueueStore().put(queueName, sender);
        }
        sender.sendMessage(message, queueName);
    }

    @Override
    public void sendMessage(String queueName, Map mapMessage) throws MessageSenderException {
        JMSSender sender = getQueueStore().get(queueName);
        if (sender == null) {
            sender = getJMSSender();
            sender.initializeQueueConnection(queueName);
            getQueueStore().put(queueName, sender);
        }
        sender.sendMessage(mapMessage, queueName);
    }

    @Override
    public void publishMessage(String topicName, String message) throws MessageSenderException {
        JMSSender sender = getQueueStore().get(topicName);
        if (sender == null) {
            sender = getJMSSender();
            sender.initializeTopicConnection(topicName);
            getQueueStore().put(topicName, sender);
        }
        sender.publishMessage(message, topicName);
    }

    @Override
    public void publishMessage(String topicName, Map mapMessage) throws MessageSenderException {
        JMSSender sender = getQueueStore().get(topicName);
        if (sender == null) {
            sender = getJMSSender();
            sender.initializeTopicConnection(topicName);
            getQueueStore().put(topicName, sender);
        }
        sender.publishMessage(mapMessage, topicName);
    }

    public Map<String, JMSSender> getQueueStore() {
        return queueStore;
    }

    public JMSSender getJMSSender() {
        return new JMSSender();
    }
}
