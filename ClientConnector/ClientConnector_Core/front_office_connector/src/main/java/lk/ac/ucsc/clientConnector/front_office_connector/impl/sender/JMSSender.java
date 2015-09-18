package lk.ac.ucsc.clientConnector.front_office_connector.impl.sender;

import lk.ac.ucsc.clientConnector.front_office_connector.api.MessageSender;
import lk.ac.ucsc.clientConnector.front_office_connector.api.exception.MessageSenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Properties;


public class JMSSender implements MessageSender {
    private static final int SLEEP_TIME = 5000;
    private static Logger logger = LoggerFactory.getLogger(JMSSender.class);
    // is JMS sender running
    private boolean isActive = false;
    // is lookup complete
    private boolean isInitialized = false;
    // is connected to OMS
    private boolean isConnected = false;
    private Date connectedTime;
    private String hostName;
    private Properties connectionProperties = new Properties();
    private String queueName;
    private String jmsFactory;
    // these are reusable once looked-up
    private ConnectionFactory factory;
    private Queue queue;
    private QueueSession session = null;
    private QueueSender sender = null;
    private QueueConnection queueConnection = null;
    private int retryCount;


    public JMSSender(String queueName, String hostAddress, int port, int retryCount, String initialContextFactory, String jmsFactory, String protocol) {
        connectionProperties.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
        connectionProperties.put(Context.PROVIDER_URL, protocol + "://" + hostAddress + ":" + port);
        this.hostName = hostAddress;
        this.queueName = "jms/queue/" + queueName;
        this.jmsFactory = jmsFactory;
        this.retryCount = retryCount;
    }


    @Override
    public final void start() {
        isActive = true;
        new Thread(new Connector()).start();
    }

    @Override
    public void stop() {
        isActive = false;
    }

    private void lookup() throws NamingException {
        logger.info("Looking up JMS provider for queue: " + queueName);
        Context remoteContext = createContext();
        factory = (ConnectionFactory) createFactory(remoteContext);
        queue = (Queue) createQueue(remoteContext);
        logger.info("Lookup successful for queue: " + queueName);
        isInitialized = true;
    }


    public Context createContext() throws NamingException {
        return new InitialContext(connectionProperties);
    }


    public Object createFactory(Context remoteContext) throws NamingException {
        return remoteContext.lookup(jmsFactory);
    }


    public Object createQueue(Context remoteContext) throws NamingException {
        return remoteContext.lookup(queueName);
    }


    private void createConnection() throws JMSException {
        logger.info("Attempting to connect to the queue: " + queueName);
        queueConnection = (QueueConnection) factory.createConnection();
        session = createQueueSessionWithTuningParameters();
        sender = (QueueSender) session.createProducer(queue);
        queueConnection.setExceptionListener(new EL());
        queueConnection.start();
        logger.info("Successfully connected to queue: " + queueName);
        isConnected = true;
        connectedTime= new Date();
    }


    private QueueSession createQueueSessionWithTuningParameters() throws JMSException {
        if ("TRSInquiry".equalsIgnoreCase(queueName)) {
            session = (QueueSession) queueConnection.createSession(false, QueueSession.CLIENT_ACKNOWLEDGE);
        } else {
            session = (QueueSession) queueConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        }
        return session;
    }


    @Override
    public void sendMessage(String message, int retry) throws MessageSenderException {
        int i = retry;
        while (i < retryCount) {
            i++;
            logger.debug("Starting to send new message to queue: " + queueName + ", message " + message);
            boolean result = sendMessage(message);
            if (result) {
                logger.debug("Finished sending message to queue: " + queueName);
                return;
            }
            logger.info("Retrying the message sending...");
        }
        logger.error("Sending message aborted (Retry limit reached)");

    }

    private boolean sendMessage(String message) {
        try {
            if (!isConnected) {
                createConnection();
            }
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText(message);
            sender.send(txtMessage);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            closeConnection();
        }
        return false;
    }

    private void closeConnection() {
        try {
            if (queueConnection != null) {
                queueConnection.close();
            }
        } catch (Exception je) {
            // do nothing, since we do not need to know errors while disconnecting
        }
        isConnected = false;
        connectedTime = null;
    }

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
                    logger.error("Error performing lookup for queue" + queueName + " Will try again later. Error: ", e);

                } catch (JMSException e) {
                    logger.error("Error connecting to queue: " + queueName + " Will try again later. Error: ", e);
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    logger.error("thread interrupted");
                }
            }
            logger.info("JMS Sender stopped");
        }

    }

    private class EL implements ExceptionListener {

        public void onException(JMSException e) {
            logger.error("JMS Error: " + e.getMessage());
            JMSSender.this.closeConnection();
        }
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public Date getConnectedTime() {
        return connectedTime;
    }

    @Override
    public String getHostName() {
        return hostName;
    }
}
