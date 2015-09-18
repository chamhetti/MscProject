package lk.ac.ucsc.oms.wrapper_stp_connector;

import lk.ac.ucsc.oms.wrapper_stp_connector.message_processor.STPMessageProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.jms.Message;
import javax.jms.MessageListener;



@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/FromSellSide"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1"),
        @ActivationConfigProperty(propertyName = "minSessions", propertyValue = "1"),
        @ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
        @ActivationConfigProperty(propertyName = "maxMessagesPerSessions", propertyValue = "1"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")}, name = "SellSideConnectorBeanEJB")

public class SellSideConnectorBean implements MessageListener {
    private Logger logger = LogManager.getLogger(SellSideConnectorBean.class);
    private STPMessageProcessor messageProcessor;

    @EJB
    private lk.ac.ucsc.oms.boot_strapper.beans.SessionStartupBean sb;

    @PostConstruct
    public void init() {
        logger.info("SellSideConnectorBean Init start");
        if (!sb.isInitialized()) {
            logger.error("This should not happen ...");
        }
        messageProcessor = getSTPMessageProcessor();
        messageProcessor.initProcessor();
        logger.info("SellSideConnectorBean Init complete");
    }

    public STPMessageProcessor getSTPMessageProcessor() {
        return new STPMessageProcessor();
    }


    /**
     * {@inheritDoc}
     *
     * @param message JMS message from sell side
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void onMessage(Message message) {
        logger.info("Message Received from Sell Side >>{}" , message);
        messageProcessor.processMessage(message);
    }


}
