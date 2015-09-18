package lk.ac.ucsc.oms.fixConnection.api;

import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import lk.ac.ucsc.oms.fixConnection.implGeneral.schedule.FIXConnectionSchedule;
import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FIXConnectionFactory {
    private static Logger logger = LogManager.getLogger(FIXConnectionFactory.class);
    private static FIXConnectionFactory factory;
    private static ApplicationContext ctx;
    private static SchedulerInterface schedulerInterface;

    private FIXConnectionFactory() throws FIXConnectionException {
        synchronized (this) {
            if (ctx == null) {
                ctx = new ClassPathXmlApplicationContext("/implGeneral/spring-config-fix-connection.xml");
            }
        }
        if (schedulerInterface == null) {
            try {
                schedulerInterface = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
                throw new FIXConnectionException("Error with Scheduler - Constructor failed", e);
            }
        }
    }

    public static FIXConnectionFactory getInstance() throws FIXConnectionException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    public void initialize() throws FIXConnectionException {
        getFIXConnectionFacade().initialize();
    }

    private static synchronized FIXConnectionFactory createInstance() throws FIXConnectionException {
        if (factory != null) {
            return factory;
        }

        factory = new FIXConnectionFactory();

        try {

            FIXConnectionSchedule fixConnectionSchedule = ctx.getBean("fixConnectionSchedule", FIXConnectionSchedule.class);
            schedulerInterface.scheduleThisEvent(fixConnectionSchedule);
        } catch (SchedulingException e) {
            logger.debug("Error in scheduling...", e);
            throw new FIXConnectionException("Error in scheduling - Initialize fix connection module failed", e);
        }

        return factory;
    }

    public FIXConnectionFacade getFIXConnectionFacade() {
        return ctx.getBean("fixFacade", FIXConnectionFacade.class);
    }

    public static void setContext(ApplicationContext context) {
        FIXConnectionFactory.ctx = context;
    }

    public static void setSchedulerInterface(SchedulerInterface schedulerInterface) {
        FIXConnectionFactory.schedulerInterface = schedulerInterface;
    }
}
