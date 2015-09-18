package lk.ac.ucsc.oms.common_utility.implGeneral.messageSender;

import lk.ac.ucsc.oms.common_utility.api.exceptions.MessageSenderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.Map;


public class JMSSender {
    private Logger logger = LogManager.getLogger(JMSSender.class);

    private QueueSession queueSession;
    private MessageProducer sender;
    private QueueConnection queueConnection;
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private TopicPublisher publisher;
    private String queueName;
    private String topicName;
    private boolean isInitialized = false;

    private static final String QUEUE_NAME_PREFIX = "java:jboss/exported/jms/queue/";
    private static final String TOPIC_NAME_PREFIX = "jboss/exported/jms/topic/";

    public void initializeQueueConnection(String jmsQueueName) throws MessageSenderException {
        try {
            this.queueName = QUEUE_NAME_PREFIX + jmsQueueName;
            logger.info("Starting JMS Sender for Queue: " + queueName);
            Context context = createContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("java:/JmsXA");
            Queue queue = (Queue) context.lookup(queueName);
            queueConnection = (QueueConnection) factory.createConnection();
            queueSession = (QueueSession) queueConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            if (sender != null) {
                sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // this is to speed up the message delivery
            }
            sender = queueSession.createProducer(queue);

            isInitialized = true;
            queueConnection.start();
            logger.info("JMS Sender Initialized to Queue: " + queueName);
        } catch (Exception e) {
            logger.error("Error in initializing the queue connection. Queue Name- {}, Error - {}", queueName, e);
            throw new MessageSenderException(e.toString(), e);
        }
    }

    public void initializeTopicConnection(String jmsTopicName) throws MessageSenderException {
        try {
            topicName = TOPIC_NAME_PREFIX + jmsTopicName;

            logger.info("Starting JMS Sender for Queue: " + topicName);
            Context context = createContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("java:/JmsXA");

            Topic topic = (Topic) context.lookup(topicName);
            topicConnection = (TopicConnection) factory.createConnection();
            topicSession = (TopicSession) topicConnection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            publisher = topicSession.createPublisher(topic);
            if (publisher != null) {
                publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // this is to speed up the message delivery
            }
            topicConnection.start();
            isInitialized = true;
            logger.info("JMS Publisher Initialized to topic - {}", topicName);
        } catch (Exception e) {
            logger.error("Error in JMS Connection initialization: -{}", e);
            throw new MessageSenderException(e.getMessage(), e);
        }
    }


    public void closeConnection() {
        try {
            if (getSender() != null) {
                getSender().close();
            }
            if (getQueueSession() != null) {
                getQueueSession().close();
            }
            if (getQueueConnection() != null) {
                getQueueConnection().close();
            }
            if (getPublisher() != null) {
                getPublisher().close();
            }
            if (getTopicSession() != null) {
                getTopicSession().close();
            }
            if (getTopicConnection() != null) {
                getTopicConnection().close();
            }
        } catch (Exception je) {
            logger.error(je.getMessage(), je);
        }
        isInitialized = false;
    }

    public void sendMessage(String message, String jmsQueueName) throws MessageSenderException {
        try {
            if (!isInitialized()) {
                initializeQueueConnection(jmsQueueName);
            }
            TextMessage txtMessage = getQueueSession().createTextMessage();
            txtMessage.setText(message);
            getSender().send(txtMessage);

            logger.debug("Message Send to the Queue - {}", queueName);
        } catch (Exception e) {
            logger.error("Error in Send message to the Queue - {}. Error - {}", queueName, e);
            closeConnection();
            throw new MessageSenderException(e.toString(), e);
        }
    }

    public void sendMessage(Map map, String jmsQueueName) throws MessageSenderException {
        try {
            if (!isInitialized()) {
                initializeQueueConnection(jmsQueueName);
            }
            logger.info("Message Map received - {}", map);
            MapMessage mapMessage = getQueueSession().createMapMessage();

            Collection sCollection = null;
            sCollection = map.keySet();
            for (Object aSCollection : sCollection) {
                String field = (String) aSCollection;
                String value = (String) map.get(field);
                mapMessage.setString(field, value);
            }
            logger.info("Before send to toAppia Queue>>>>>>");
            getSender().send(mapMessage);
            logger.info("To Queue - {}, Message - {} ", queueName, mapMessage);
        } catch (Exception e) {
            logger.error("Error in send message to Queue - {} ", queueName);
            closeConnection();
            throw new MessageSenderException(e.toString(), e);
        }
    }


    public void publishMessage(String message, String jmsTopicName) throws MessageSenderException {
        try {
            if (!isInitialized()) {
                initializeTopicConnection(jmsTopicName);
            }
            TextMessage txtMessage = getTopicSession().createTextMessage();
            txtMessage.setText(message);
            getPublisher().publish(txtMessage);
            logger.debug("To Topic - {}, Message - {} ", topicName, message);
        } catch (Exception e) {
            logger.error("Error in send message: ", e);
            closeConnection();
            throw new MessageSenderException(e.getMessage(), e);
        }
    }

    public void publishMessage(Map map, String jmsTopicName) throws MessageSenderException {
        try {
            if (!isInitialized()) {
                initializeTopicConnection(jmsTopicName);
            }
            MapMessage mapMessage = getTopicSession().createMapMessage();
            Collection sCollection = null;
            sCollection = map.keySet();
            for (Object aSCollection : sCollection) {
                String field = (String) aSCollection;
                String value = (String) map.get(field);
                mapMessage.setString(field, value);
            }

            getPublisher().publish(mapMessage);
            logger.debug("To Topic - {}, Map Message - {} ", topicName, mapMessage);
        } catch (Exception e) {
            logger.error("Error in send message: ", e);
            closeConnection();
            throw new MessageSenderException(e.getMessage(), e);
        }
    }


    public Context createContext() throws NamingException {
        return new InitialContext();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public MessageProducer getSender() {
        return sender;
    }

    public QueueConnection getQueueConnection() {
        return queueConnection;
    }

    public QueueSession getQueueSession() {
        return queueSession;
    }

    public TopicSession getTopicSession() {
        return topicSession;
    }

    public TopicConnection getTopicConnection() {
        return topicConnection;
    }

    public TopicPublisher getPublisher() {
        return publisher;
    }
}
