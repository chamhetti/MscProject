package lk.ac.ucsc.clientConnector.front_office_connector.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public final class FrontOfficeConnectorFactory {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("/implGeneral/spring-oms_connector.xml");

    private FrontOfficeConnectorFactory() {

    }


    public static FrontOfficeConnectorFacade getOmsConnecter() {
        return (FrontOfficeConnectorFacade) context.getBean("omsConnector");
    }
}
