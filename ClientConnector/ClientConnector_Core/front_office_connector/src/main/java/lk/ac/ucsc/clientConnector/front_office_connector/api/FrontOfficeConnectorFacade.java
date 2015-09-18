package lk.ac.ucsc.clientConnector.front_office_connector.api;

import lk.ac.ucsc.clientConnector.common.api.ConnectionProperties;


public interface FrontOfficeConnectorFacade {

    /**
     * This creates the message sender for given queue parameters
     *
     * @param queueProperties is the message queue properties
     * @return MessageSender for the queue
     */
    MessageSender createMessageSender(ConnectionProperties queueProperties);
}
