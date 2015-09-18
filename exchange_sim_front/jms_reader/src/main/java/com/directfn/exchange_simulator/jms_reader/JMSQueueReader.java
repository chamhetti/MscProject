package com.directfn.exchange_simulator.jms_reader;

import com.directfn.exchange_simulator.message_processor.api.ControllerLogic;
import com.directfn.exchange_simulator.message_processor.api.MessageProcessor;
import com.directfn.exchange_simulator.message_processor.impl.MessageProcessorImpl;
import com.directfn.exchange_simulator.message_processor.impl.beans.TradingSessions;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class JMSQueueReader implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(JMSQueueReader.class);
    private static final int THREAD_COUNT = 100;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    private Properties connectionProperties = new Properties();
    private String queueName;
    private String jmsFactory;
    private boolean isActive = false;
    private boolean isInitialized = false;
    private boolean isConnected = false;
    private ConnectionFactory factory;
    private Queue queue;
    private ControllerLogic controllerLogic;
    private MessageProcessor messageProcessor;
    private static Gson gson = new Gson();
    private QueueConnection queueConnection = null;
    public static final int THREAD_SLEEP_TIME = 5000;
    private static boolean isFirstTime=true;

    /**
     * constructor for JMSQueueReader
     */
    public JMSQueueReader() {

    }

    /**
     * constructor for JMSQueueReader
     *
     * @param topicName
     * @param hostName
     * @param port
     */
    public JMSQueueReader(String topicName, String hostName, int port) {
        connectionProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        connectionProperties.put(Context.PROVIDER_URL, "remote://" + hostName + ":" + port);
        this.queueName = "jms/queue/" + topicName;
        this.jmsFactory = "jms/RemoteConnectionFactory";
    }

    /**
     * JMS queue initialization happens here
     */
    public final synchronized void initialize() {
        isActive = true;
        new Thread(new Connector()).start();
    }

    /**
     * Implementation for onMessage of MessageListener. asynchronously onMessage method will call
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        logger.info("message reads from ToAppia queue: {} " , message);
        executorService.execute(new FutureTask<>(new MessageProcessorImpl(message)));
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
                    logger.error("Error performing lookup for topic: {} Will try again later. Error: {}" , queueName , e);

                } catch (JMSException e) {
                    logger.error("Error connecting to topic: {} Will try again later. Error: {}" , queueName, e);
                }
                try {
                    Thread.sleep(THREAD_SLEEP_TIME);
                } catch (InterruptedException e) {
                    logger.error("thread interrupted");
                }
            }
            logger.info("JMS Listener stopped");
        }
    }

    /**
     * Initialize the queue
     *
     * @throws NamingException
     */
    private void lookup() throws NamingException {
        logger.info("Looking up JMS provider for topic: {}", queueName);
        Context remoteContext = new InitialContext(connectionProperties);
        factory = (ConnectionFactory) remoteContext.lookup(jmsFactory);
        queue = (Queue) remoteContext.lookup(queueName);
        logger.info("Lookup successful for topic: {}" , queueName);
        isInitialized = true;
    }

    /**
     * will create the connection between listener and the queue
     *
     * @throws JMSException
     */
    private void createConnection() throws JMSException {
        logger.info("Attempting to subscribe to the queue: {}" , queueName);
        queueConnection = (QueueConnection) factory.createConnection();
        QueueSession queueSession = (QueueSession) queueConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        QueueReceiver queueReceiver = queueSession.createReceiver(queue);
        queueReceiver.setMessageListener(this);
        queueConnection.setExceptionListener(new EL());
        queueConnection.start();
        logger.info("Successfully subscribed to queue: {}" , queueName);
        isConnected = true;
        if (messageProcessor == null) {
            messageProcessor = new MessageProcessorImpl();
        }

    }

    /**
     * Error handling
     */
    private class EL implements ExceptionListener {
        public void onException(JMSException e) {
            JMSQueueReader.this.close();
        }
    }

    /**
     * close the JMS connection
     */
    public void close() {
        try {
            if (queueConnection != null) {
                queueConnection.close();
            }
            logger.debug("Connection terminated for {}" , queueName);

        } catch (Exception e) {
            logger.error("do nothing, since we do not need to know errors while disconnecting", e);
            // do nothing, since we do not need to know errors while disconnecting
        }
        isConnected = false;
        isInitialized=false;


        if (messageProcessor == null) {
            messageProcessor = new MessageProcessorImpl();
        }
        List<TradingSessions> tradingSessionsList = controllerLogic.getTradingSessions();
        int index = 0;
        for (TradingSessions i : tradingSessionsList) {
            index = index + 1;
            i.setConnectionStatus("Connecting...");
            messageProcessor.generateTradingSessionStatusMapMessage(i.getTradingSession(), "23210");

        }

    }
    public static void setIsFirstTime(boolean isFirstTime) {
        JMSQueueReader.isFirstTime = isFirstTime;
    }
}

