package lk.ac.ucsc.clientConnector.session_manager.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class SessionManagerFactory {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-session.xml");
    private static SessionManagerFacade sessionManagerFacade = null;

    private SessionManagerFactory() {
    }


    public static SessionManagerFacade getSessionManager() {
        if (sessionManagerFacade == null) {
            sessionManagerFacade = (SessionManagerFacade) context.getBean("sessionManager");
        }
        return sessionManagerFacade;
    }
}
