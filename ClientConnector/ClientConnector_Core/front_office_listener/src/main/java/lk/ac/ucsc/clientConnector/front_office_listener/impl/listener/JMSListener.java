package lk.ac.ucsc.clientConnector.front_office_listener.impl.listener;

import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessor;
import lk.ac.ucsc.clientConnector.front_office_listener.api.TrsMessageListener;
import lk.ac.ucsc.clientConnector.front_office_listener.api.exception.MessageListenerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


public class JMSListener implements TrsMessageListener {
    public static final int SLEEP_TIME = 100;
    private static Logger logger = LoggerFactory.getLogger(JMSListener.class);
    private String queueName;
    private String host;
    private int port;
    private boolean isConnected = false;
    private boolean isClosed = false;
    private QueueConnection queueConnection = null;
    private QueueSession queueSession = null;
    private QueueReceiver queueReceiver = null;
    private ResponseProcessor responseProcessor;
    private static final String JNDI_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String JMS_FACTORY = "jms/RemoteConnectionFactory";


    public JMSListener(String queueName, String hostName, int port, ResponseProcessor responseProcessor) throws MessageListenerException {
        this.queueName = "jms/queue/" + queueName;
        this.host = hostName;
        this.port = port;
        this.responseProcessor = responseProcessor;
    }

    public final synchronized void initialize() {
        if (!isConnected) {
            try {
                logger.info("Initializing JMS Listener for Queue: " + queueName + " Port: " + port);
                Context remoteContext = getContext();

                ConnectionFactory factory = getConnectionFactory(remoteContext);
                Queue queue = getQueue(queueName, remoteContext);
                queueConnection = (QueueConnection) factory.createConnection();
                queueSession = (QueueSession) queueConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
                queueReceiver = queueSession.createReceiver(queue);
                queueConnection.setExceptionListener(new EL());
                queueConnection.start();
                isConnected = true;
                logger.info("JMS Listener Initialized to Queue: " + queueName);
            } catch (Exception e) {
                close();
            }
        }
    }


    public void readMessage() throws MessageListenerException {
        String reply = null;
        TextMessage replyQueueMsg = null;
        try {
            if (!isClosed) {
                replyQueueMsg = (TextMessage) queueReceiver.receiveNoWait();
                if (replyQueueMsg != null) {
                    reply = replyQueueMsg.getText();
                    logger.info("From " + queueName + " message: " + reply);
                }
                if (reply != null) {
                    responseProcessor.processMessage(reply);
                }
            }
        } catch (JMSException e) {
            throw new MessageListenerException(e.toString(), e);
        }
    }

    public void closeListener() {
        this.isClosed = true;
        close();
    }

    private void close() {
        logger.info("Terminating queue connection for " + queueName);
        try {
            if (queueReceiver != null) {
                queueReceiver.close();
            }
            if (queueSession != null) {
                queueSession.close();
            }
            if (queueConnection != null) {
                queueConnection.close();
            }
        } catch (Exception e) {
            logger.error("Problem in closing JMS resources ", e);
        }
        isConnected = false;
    }

    class EL implements ExceptionListener {

        public void onException(JMSException e) {
            JMSListener.this.close();
        }
    }


    public Context getContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        props.put(Context.PROVIDER_URL, "remote://" + host + ":" + port);
        return new InitialContext(props);
    }


    public ConnectionFactory getConnectionFactory(Context remoteContext) throws NamingException {
        return (ConnectionFactory) remoteContext.lookup(JMS_FACTORY);
    }


    public Queue getQueue(String queueName, Context remoteContext) throws NamingException {
        return (Queue) remoteContext.lookup(queueName);
    }

    /**
     * Set QueueReceiver
     *
     * @param queueReceiver
     */
    public void setQueueReceiver(QueueReceiver queueReceiver) {
        this.queueReceiver = queueReceiver;
    }

    /**
     * Set QueueSession
     *
     * @param queueSession
     */
    public void setQueueSession(QueueSession queueSession) {
        this.queueSession = queueSession;
    }


    /**
     * Set QueueConnection
     *
     * @param queueConnection
     */
    public void setQueueConnection(QueueConnection queueConnection) {
        this.queueConnection = queueConnection;
    }

}
