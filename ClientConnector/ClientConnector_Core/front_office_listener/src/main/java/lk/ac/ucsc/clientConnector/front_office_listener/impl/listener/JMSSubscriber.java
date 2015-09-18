package lk.ac.ucsc.clientConnector.front_office_listener.impl.listener;

import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessor;
import lk.ac.ucsc.clientConnector.front_office_listener.api.TrsMessageListener;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class JMSSubscriber implements TrsMessageListener, MessageListener {
    private static Logger logger = LoggerFactory.getLogger(JMSSubscriber.class);
    private LinkedList<String> msgQueueForSingleUser = new LinkedList<>();
    private LinkedList<String> msgQueueForMultiple = new LinkedList<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    public static final int SLEEP_TIME = 5000;

    // is subscriber is running
    private boolean isActive = false;
    // is lookup complete
    private boolean isInitialized = false;
    // is connected to OMS
    private boolean isConnected = false;

    private Properties connectionProperties = new Properties();
    private String topicName;
    private String jmsFactory;

    // these are reusable once looked-up
    private HornetQConnectionFactory factory;
    private Topic topic;

    private TopicConnection topicConnection = null;

    private ResponseProcessor responseProcessor;


    public JMSSubscriber(String topicName, String hostName, int port, ResponseProcessor responseProcessor,
                         String initialContextFactory, String jmsFactory, String protocol) {
        connectionProperties.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
        connectionProperties.put(Context.PROVIDER_URL, protocol + "://" + hostName + ":" + port);
        this.topicName = "jms/topic/" + topicName;
        this.jmsFactory = jmsFactory;
        this.responseProcessor = responseProcessor;
    }


    public final synchronized void initialize() {
        isActive = true;
        new Thread(new Connector()).start();
    }

    private void lookup() throws NamingException {
        logger.info("Looking up JMS provider for topic: " + topicName);
        Context remoteContext = getContext(connectionProperties);
//                factory =  getConnectionFactory(remoteContext);
        factory = getConnectionFactory(remoteContext);
        factory.setUseGlobalPools(false);
        factory.setThreadPoolMaxSize(-1);
        factory.setScheduledThreadPoolMaxSize(30);
        topic = (Topic) remoteContext.lookup(topicName);
        logger.info("Lookup successful for topic: " + topicName);
        isInitialized = true;
    }

    private void createConnection() throws JMSException {
        logger.info("Attempting to subscribe to the topic: " + topicName);
        topicConnection = (TopicConnection) factory.createConnection();
        TopicSession topicSession = (TopicSession) topicConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
        topicSubscriber.setMessageListener(this);
        topicConnection.setExceptionListener(new EL());
        topicConnection.start();
        logger.info("Successfully subscribed to topic: " + topicName);
        isConnected = true;
    }

    @Override
    public void onMessage(Message message) {
        logger.info("Started processing message from OMS topic: {}", topicName);
        long startTime = new Date().getTime();
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                responseProcessor.processMessage(textMessage.getText());

            } else if (message instanceof MapMessage) {         // this is a response with multiple receivers (eg: trade response)
                MapMessage mapMessage = (MapMessage) message;
                String textMessage = mapMessage.getString("MESSAGE");
                String[] userArray = null;

                Object usersObject = mapMessage.getObject("USERS");
                if (usersObject != null) {
                    try {
                        // The user list comes as 32443,34343, 3434,
                        String commaSeparatedUserList = (String) usersObject;
                        StringTokenizer tokenizer = new StringTokenizer(commaSeparatedUserList, ",");
                        List<String> stringList = new ArrayList<>();
                        while (tokenizer.hasMoreTokens()) {
                            stringList.add(tokenizer.nextToken());
                        }
                        userArray = new String[stringList.size()];
                        userArray = stringList.toArray(userArray);
                    } catch (ClassCastException e) {
                        logger.warn("Error retrieving user list:" + message, e);
                    }
                }
                responseProcessor.processMessage(textMessage, userArray);

            }
            logger.info("Finished processing message from OMS topic: {}. Time elapsed: {}ms", topicName,  (new Date().getTime() - startTime));
        } catch (JMSException e) {
            logger.error("Error extracting message:" + message, e);
        }
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
                    logger.error("Error performing lookup for topic" + topicName + " Will try again later. Error: ", e);

                } catch (JMSException e) {
                    logger.error("Error connecting to topic: " + topicName + " Will try again later. Error: " + e);
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    logger.error("thread interrupted");
                }
            }
            logger.info("JMS Listener stopped");
        }
    }

    private class EL implements ExceptionListener {

        public void onException(JMSException e) {
            logger.error("JMS Error: " + e.getMessage());
            JMSSubscriber.this.close();
        }
    }

    private void close() {
        try {
            if (topicConnection != null) {
                topicConnection.close();
            }
            logger.debug("Connection terminated for " + topicName);

        } catch (Exception e) {
            logger.debug("Error while disconnecting");
        }
        isConnected = false;
    }


    public Context getContext(Properties connectionProperties) throws NamingException {
        return new InitialContext(connectionProperties);
    }


    public HornetQConnectionFactory getConnectionFactory(Context remoteContext) throws NamingException {
        return (HornetQConnectionFactory) remoteContext.lookup(jmsFactory);
    }

}
