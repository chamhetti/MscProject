package lk.ac.ucsc.oms.messaging_protocol_json.api;

import lk.ac.ucsc.oms.messaging_protocol_json.impl.MessageProtocolFacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MessageProtocolFacadeFactory {

    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("impl/spring-config-messaging_protocol_json.xml");
    private static MessageProtocolFacadeFactory factory;

    private MessageProtocolFacadeFactory() {

    }

    public static MessageProtocolFacadeFactory getInstance() {
        if (factory == null) {
            factory = new MessageProtocolFacadeFactory();
            return factory;
        }
        return factory;
    }

    public MessageProtocolFacadeImpl getMessageProtocolFacade() {
        return ctx.getBean("messageProtocolFacade", MessageProtocolFacadeImpl.class);
    }

    public static void setContext(ApplicationContext context) {
        MessageProtocolFacadeFactory.ctx = context;
    }

    public ApplicationContext getContext() {
        return ctx;
    }
}
