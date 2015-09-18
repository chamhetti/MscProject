package lk.ac.ucsc.oms.trs_writer.implGeneral;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.trs_writer.api.MessagePublisher;
import lk.ac.ucsc.oms.trs_writer.api.exceptions.TrsWriterException;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JmsMessagePublisher implements MessagePublisher {
    private static Logger logger = Logger.getLogger(JmsMessagePublisher.class);

    private TopicConnection topicConnection;
    private TopicSession session;
    private TopicPublisher publisher;
    private String topicName;
    private boolean isInitialized = false;

    /**
     * @param topicName is the queue name
     * @throws Exception in the initialization of a topic publisher
     */
    public JmsMessagePublisher(String topicName) throws OMSException {
        this.topicName = "jboss/exported/jms/topic/" + topicName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeConnection() throws TrsWriterException {
        try {
            logger.info("Starting JMS Sender for Queue: " + topicName);

            Context context = createContext();

            ConnectionFactory factory = (ConnectionFactory) context.lookup("java:/JmsXA");

            Topic topic = (Topic) context.lookup(topicName);
            topicConnection = (TopicConnection) factory.createConnection();

            session = (TopicSession) topicConnection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);

            publisher = session.createPublisher(topic);
            topicConnection.start();
            isInitialized = true;

            logger.info("JMS Publisher Initialized to topic: " + topicName);
        } catch (Exception e) {
            logger.error("Error in JMS Connection initialization: -{}", e);
            throw new TrsWriterException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishMessage(String message) throws TrsWriterException {
        try {
            if (!isInitialized) {
                initializeConnection();
            }
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText(message);
            publisher.publish(txtMessage);
            logger.debug("To queue Sender: " + topicName + ", message " + message);
        } catch (Exception e) {
            logger.error("Error in send message: ", e);
            close();
            throw new TrsWriterException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishMessage(String message, String[] users) throws TrsWriterException {
        try {
            if (!isInitialized) {
                initializeConnection();
            }
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("MESSAGE", message);
            mapMessage.setObject("USERS", getArrayAsString(users));

            publisher.publish(mapMessage);
            logger.debug("To queue Sender: " + topicName + ", message " + message);
        } catch (Exception e) {
            logger.error("Error in send message: ", e);
            close();
            throw new TrsWriterException(e.getMessage(), e);
        }
    }

    /**
     * Creating new context
     *
     * @return
     * @throws NamingException
     */
    public Context createContext() throws NamingException {
        return new InitialContext();
    }

    /**
     * Closes the JMS connection
     */
    private void close() {
        isInitialized = false;
        try {
            if (publisher != null) {
                publisher.close();
            }
            if (session != null) {
                session.close();
            }
            if (topicConnection != null) {
                topicConnection.close();
            }
        } catch (Exception je) {
            logger.error(je.getMessage(), je);
        }
        isInitialized = false;
    }

    /**
     * Convert string array to String object to support MapMessage since it does not support collection or arrays
     *
     * @param strings
     * @return
     */
    private String getArrayAsString(String[] strings) {
        StringBuilder output = new StringBuilder();
        for (String s : strings) {
            //TODO introduce new delimiter to tokenize
            output.append(s).append(',');
        }
        return output.toString();
    }


    /**
     * Setting the initialization
     * Use only for unit testing
     *
     * @param initialized
     */
    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    /**
     * Use only for unit testing
     *
     * @return
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Setting the publisher
     * Use only for unit testing
     *
     * @param publisher
     */
    public void setPublisher(TopicPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Setting the session
     * Use only for unit testing
     *
     * @param session
     */
    public void setSession(TopicSession session) {
        this.session = session;
    }
}
