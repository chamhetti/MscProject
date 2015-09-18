package lk.ac.ucsc.oms.common_utility.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.MessageSenderException;
import lk.ac.ucsc.oms.common_utility.implGeneral.messageSender.JMSMessageSenderImpl;


public final class MiddlewareSenderFactory {
    private static MiddlewareSenderFactory factory;
    private MiddlewareSenderFactory() {
    }

    public static MiddlewareSenderFactory getInstance() throws MessageSenderException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    private static synchronized MiddlewareSenderFactory createInstance() throws MessageSenderException {
        if (factory != null) {
            return factory;
        }
        factory = new MiddlewareSenderFactory();

        return factory;
    }

    public MessageSender getMessageSender() {
        return new JMSMessageSenderImpl();
    }
}
