package lk.ac.ucsc.clientConnector.front_office_connector.impl.facade;

import lk.ac.ucsc.clientConnector.common.api.ConnectionProperties;
import lk.ac.ucsc.clientConnector.front_office_connector.api.FrontOfficeConnectorFacade;
import lk.ac.ucsc.clientConnector.front_office_connector.api.MessageSender;
import lk.ac.ucsc.clientConnector.front_office_connector.impl.sender.JMSSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FrontOfficeConnectorFacadeImpl implements FrontOfficeConnectorFacade {
    private final Logger logger = LoggerFactory.getLogger(FrontOfficeConnectorFacadeImpl.class);
    private int retryCount;
    private String initialContextFactory;
    private String jmsFactory;

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setInitialContextFactory(String initialContextFactory) {
        this.initialContextFactory = initialContextFactory;
    }

    public void setJmsFactory(String jmsFactory) {
        this.jmsFactory = jmsFactory;
    }

    @Override
    public MessageSender createMessageSender(ConnectionProperties queueProperties) {
        MessageSender queueSender = null;
        switch (queueProperties.getQueueType()) {
            case JMS:
                queueSender = new JMSSender(queueProperties.getName(), queueProperties.getIP(), queueProperties.getPort(),
                        retryCount, initialContextFactory, jmsFactory, queueProperties.getProtocol());
                break;
            default:
                logger.error("Fatal error: Unsupported message type: " + queueProperties.getQueueType());
                System.exit(1);
                break;

        }
        return queueSender;
    }
}
