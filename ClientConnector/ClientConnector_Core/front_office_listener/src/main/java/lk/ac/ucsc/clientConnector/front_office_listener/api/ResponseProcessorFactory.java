package lk.ac.ucsc.clientConnector.front_office_listener.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class ResponseProcessorFactory {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("/implGeneral/spring-response_processor.xml");

    private ResponseProcessorFactory() {
    }


    public static ResponseProcessorFacade getResponseProcessorFacade() {
        return (ResponseProcessorFacade) context.getBean("responseProcessorFacade");
    }
}
