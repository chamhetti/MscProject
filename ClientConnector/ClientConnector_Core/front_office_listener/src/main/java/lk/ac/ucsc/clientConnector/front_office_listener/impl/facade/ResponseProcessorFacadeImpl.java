package lk.ac.ucsc.clientConnector.front_office_listener.impl.facade;

import lk.ac.ucsc.clientConnector.common.api.ConnectionProperties;
import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessor;
import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessorFacade;
import lk.ac.ucsc.clientConnector.front_office_listener.api.TrsMessageListener;
import lk.ac.ucsc.clientConnector.front_office_listener.api.exception.MessageListenerException;
import lk.ac.ucsc.clientConnector.front_office_listener.impl.listener.JMSSubscriber;

import java.util.ArrayList;
import java.util.List;


public class ResponseProcessorFacadeImpl implements ResponseProcessorFacade {
    private List<TrsMessageListener> listeners = new ArrayList<>();
    private String initialContextFactory;
    private String jmsFactory;

    public void setInitialContextFactory(String initialContextFactory) {
        this.initialContextFactory = initialContextFactory;
    }

    public void setJmsFactory(String jmsFactory) {
        this.jmsFactory = jmsFactory;
    }

    public void setListeners(List<TrsMessageListener> listeners) {
        this.listeners = listeners;
    }


    @Override
    public void createMessageListener(ConnectionProperties topicProperties, ResponseProcessor messageProcessor) {

        TrsMessageListener listener = getJMSSubscriber(topicProperties, messageProcessor);
        listeners.add(listener);
    }

    @Override
    public void initialize() throws MessageListenerException {
        for (TrsMessageListener listener : listeners) {
            listener.initialize();
        }
    }


    public JMSSubscriber getJMSSubscriber(ConnectionProperties topicProperties, ResponseProcessor messageProcessor) {
        return new JMSSubscriber(topicProperties.getName(), topicProperties.getIP(), topicProperties.getPort(),
                messageProcessor, initialContextFactory, jmsFactory, topicProperties.getProtocol());

    }

}
