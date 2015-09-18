package lk.ac.ucsc.oms.wrapper_trs_connector;


import lk.ac.ucsc.oms.boot_strapper.beans.SessionStartupBean;
import lk.ac.ucsc.oms.wrapper_trs_connector.messageProcessor.TrsMessageProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/TRSTransaction"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "minSessions", propertyValue = "1"),
        @ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
        @ActivationConfigProperty(propertyName = "maxMessagesPerSessions",propertyValue = "2"),
        @ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")}, name = "TransactionConnectorBeanEJB")

public class TransactionConnectorBean implements MessageListener {
    private Logger logger = LogManager.getLogger(TransactionConnectorBean.class);
    private TrsMessageProcessor messageProcessor;

    @EJB
    private SessionStartupBean sb;



    @PostConstruct
    public void init() {
        logger.info("TransactionConnectorBean Init start");
        //call the initialized method of the boot_strapper to make sure it is loaded before MDB start.
        if (!sb.isInitialized()) {
            logger.error("This should not happen ...");
        }
        messageProcessor = getTrsMessageProcessor();
        //initialized the core modules used for message processing.
        messageProcessor.initProcessor();
        logger.info("TransactionConnectorBean Init complete");
    }

    public TrsMessageProcessor getTrsMessageProcessor() {
        return new TrsMessageProcessor();
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void onMessage(Message message) {
        String mixMessage;
        try {
            mixMessage = ((TextMessage) message).getText();
            logger.info("Received Message from the TRS queue: TRSTransaction " + mixMessage);
            messageProcessor.processMessage(mixMessage);

        } catch (JMSException e) {
            logger.error("Issue in processing jms message", e);
        }


    }
}
