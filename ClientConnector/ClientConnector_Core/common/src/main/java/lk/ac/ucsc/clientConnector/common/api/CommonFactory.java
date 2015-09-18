package lk.ac.ucsc.clientConnector.common.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by amila on 5/22/14.
 */
public class CommonFactory {
    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("/implGeneral/spring-common.xml");
    private static CommonFactory factory;

    private CommonFactory() {
    }

    /**
     * This is the static factory method to get the TRSMonitor
     *
     * @return the TRSMonitor instance
     */
    public static CommonFactory getInstance() {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    private static synchronized CommonFactory createInstance() {
        if (factory != null) {
            return factory;
        }
        factory = new CommonFactory();
        return factory;
    }

    public RequestConfigManager getRequestConfigManager() {
        return CONTEXT.getBean("requestConfigManager", RequestConfigManager.class);
    }
}
