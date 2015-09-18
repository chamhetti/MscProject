package lk.ac.ucsc.clientConnector.front_office_listener.api;

import lk.ac.ucsc.clientConnector.common.api.ConnectionProperties;
import lk.ac.ucsc.clientConnector.front_office_listener.api.exception.MessageListenerException;

public interface ResponseProcessorFacade {

    /**
     * Creates a message listener
     *
     * @param messageProcessor is the message processor
     */
    void createMessageListener(ConnectionProperties topicProperties, ResponseProcessor messageProcessor);

    /**
     * Starts all listeners
     *
     * @throws MessageListenerException
     */
    void initialize() throws MessageListenerException;

}
