package lk.ac.ucsc.clientConnector.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class TrsApplicationContextFactory {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("/implGeneral/spring-config.xml");

    private TrsApplicationContextFactory() {
    }

    public static ApplicationContext getTrsContext() {
        return context;
    }

}
