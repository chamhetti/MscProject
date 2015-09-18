package com.directfn.exchange_simulator.jms_writer;

import com.directfn.exchange_simulator.common_util.exceptions.SimulatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Map;
import java.util.Properties;

import static com.directfn.exchange_simulator.common_util.utils.GlobalLock.lock;

public class JMSQueueWriter {
    private static Logger logger = LoggerFactory.getLogger(JMSQueueWriter.class);

    private Properties connectionProperties = new Properties();
    private String queueName;
    private String jmsFactory;
    private int retryCount;
    private boolean isActive = false;
    private boolean isInitialized = false;
    private boolean isConnected = false;
    private ConnectionFactory factory;
    private Queue queue;
    private QueueSession session = null;
    private QueueConnection queueConnection = null;
    private QueueSender sender = null;
    private static final int THREAD_SLEEP_TIME = 5000;
    private MapMessageCreator mapMessageCreator;

    /**
     * Default constructor for JMSQueueWriter
     */
    public JMSQueueWriter() {

    }

    /**
     * constructor for JMSQueueWriter
     *
     * @param queueName
     * @param hostAddress
     * @param port
     * @param retryCount
     */
    public JMSQueueWriter(String queueName, String hostAddress, int port, int retryCount) {
        connectionProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        connectionProperties.put(Context.PROVIDER_URL, "remote://" + hostAddress + ":" + port);
        this.queueName = "jms/queue/" + queueName;
        this.jmsFactory = "jms/RemoteConnectionFactory";
        this.retryCount = retryCount;
    }

    /**
     * initialize queue writer
     */
    public final void start() {
        isActive = true;
        new Thread(new Connector()).start();
    }

    /**
     * stop the queue writer
     */
    public void stop() {
        isActive = false;
    }

    /**
     * Initialize the queue
     *
     * @throws NamingException
     */
    private void lookup() throws NamingException {
        logger.info("Looking up JMS provider for queue: {}" , queueName);
        Context remoteContext = new InitialContext(connectionProperties);
        factory = (ConnectionFactory) remoteContext.lookup(jmsFactory);
        queue = (Queue) remoteContext.lookup(queueName);
        logger.info("Lookup successful for queue: {}" , queueName);
        isInitialized = true;
    }

    /**
     * will create the connection between writer and the queue
     *
     * @throws JMSException
     */
    private void createConnection() throws JMSException {
        logger.info("Attempting to connect to the queue: {}" , queueName);
        queueConnection = (QueueConnection) factory.createConnection();
        session = (QueueSession) queueConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        sender = (QueueSender) session.createProducer(queue);
        queueConnection.setExceptionListener(new EL());
        queueConnection.start();
        logger.info("Successfully connected to queue: {}", queueName);
        isConnected = true;

    }

    /**
     * This message will process the String message into a MapMessage
     *
     * @param messageMap
     * @param retry
     * @throws Exception
     */
    public void sendMessage(Map<String, String> messageMap, int retry) throws SimulatorException {
        synchronized (lock) {
            logger.info("obtaining lock==>");
            int i = retry;
            while (i < retryCount) {
                i++;
                if (mapMessageCreator == null) {
                    mapMessageCreator = new MapMessageCreator();
                }
                if (session == null) {
                    try {
                        lookup();
                    } catch (NamingException e) {
                        logger.error("lookup fail", e);
                    }
                    try {
                        createConnection();
                    } catch (JMSException e) {
                        logger.error("JMS error", e);
                    }
                }
                boolean result;
                try {
                    logger.info("sending FIX message to FromSellSide: {}", messageMap.get("eventData"));
                    result = sendMessage(mapMessageCreator.createMapMessage(messageMap, session));
                } catch (JMSException e) {
                    logger.error("message not send", e);
                    throw new SimulatorException("message not send", e);
                }
                if (result) {
                    logger.debug("Sending new message to queue: {}, message {}" , queueName ,messageMap);
                    logger.info("returning lock==>");
                    return;
                }
                logger.info("Retrying the message sending...");
            }
            logger.error("Sending message aborted (Retry limit reached)");
            logger.info("returning lock==>");
        }
    }

    /**
     * MapMessage will write to the appropriate queue
     *
     * @param message
     * @return
     */
    private boolean sendMessage(Message message) {
        try {
            if (!isInitialized) {
                lookup();
            }
            if (!isConnected) {
                createConnection();
            }
            sender.send(message);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            closeConnection();
        }
        return false;
    }

    /**
     * close the queue connection
     */
    public void closeConnection() {
        try {
            if (queueConnection != null) {
                queueConnection.close();
            }
        } catch (Exception je) {
            // do nothing, since we do not need to know errors while disconnecting
        }
        isConnected = false;

    }

    /**
     * continuously this method will call and will check the queue connectivity. If queue listening interrupted
     * will retry after some time delay. This is use full to keep connectivity will relevant queue
     */
    private class Connector implements Runnable {
        @Override
        public void run() {
            while (isActive) {
                try {
                    if (!isInitialized) {
                        lookup();
                    }
                    if (!isConnected) {
                        createConnection();
                    }
                } catch (NamingException e) {
                    logger.error("Error performing lookup for queue {}, Will try again later. Error: {}" , queueName , e);

                } catch (JMSException e) {
                    logger.error("Error connecting to queue: {} Will try again later. Error: {}" , queueName , e);
                }
                try {
                    Thread.sleep(THREAD_SLEEP_TIME);
                } catch (InterruptedException e) {
                    logger.error("thread interrupted",e);
                }
            }
            logger.info("JMS Sender stopped");
        }

    }

    /**
     * Error handling
     */
    private class EL implements ExceptionListener {
        public void onException(JMSException e) {
            logger.error("JMS Error: {}" , e.getMessage());
            JMSQueueWriter.this.closeConnection();
        }
    }

    public QueueSession getSession() {
        return session;
    }

    public void setSession(QueueSession session) {
        this.session = session;
    }

    public MapMessageCreator getMapMessageCreator() {
        return mapMessageCreator;
    }

    public void setMapMessageCreator(MapMessageCreator mapMessageCreator) {
        this.mapMessageCreator = mapMessageCreator;
    }
}
