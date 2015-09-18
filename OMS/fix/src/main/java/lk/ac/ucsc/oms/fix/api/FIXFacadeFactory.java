package lk.ac.ucsc.oms.fix.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FIXFacadeFactory {
    private static Logger logger = LogManager.getLogger(FIXFacadeFactory.class);

    private static ApplicationContext context;
    private static FIXFacadeFactory factory;
    private static SchedulerInterface schedulerInterface;


    public FIXFacadeInterface getFixFacade() {
        return context.getBean("fixFacade", FIXFacadeInterface.class);
    }

    private FIXFacadeFactory() throws OMSException {
        synchronized (this) {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-fix.xml");
            }
        }
        if (schedulerInterface == null) {
            try {
                schedulerInterface = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
                throw new OMSException("Error with Scheduler - Constructor failed", e);
            }
        }
    }


    public static FIXFacadeFactory getInstance() throws OMSException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }


    private static synchronized FIXFacadeFactory createInstance() throws OMSException {
        if (factory != null) {
            return factory;
        }
        factory = new FIXFacadeFactory();

        return factory;
    }

    public static void setContext(ApplicationContext context) {
        FIXFacadeFactory.context = context;
    }

    public static void setSchedulerInterface(SchedulerInterface schedulerInterface) {
        FIXFacadeFactory.schedulerInterface = schedulerInterface;
    }
}
