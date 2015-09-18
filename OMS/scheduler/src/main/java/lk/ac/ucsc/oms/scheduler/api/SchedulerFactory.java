package lk.ac.ucsc.oms.scheduler.api;

import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.scheduler.impl.Scheduler;
import lk.ac.ucsc.oms.scheduler.quartzImpl.SchedulerQuartImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the factory class of the scheduler module. Main service class of the scheduler module.
 * When creating new schedule, need to go through this factory class and create get an instance of the
 * scheduler interface according to the context configuration given int the configuration file
 * <p/>
 * User: dasun, Hetti
 */
public class SchedulerFactory {

    private static SchedulerInterface si = null;
    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-config-scheduler.xml");
    private static Logger logger = LogManager.getLogger(SchedulerFactory.class);

    /**
     * Method to get a scheduler interface. If scheduler interface  already exists
     * this will return the existing scheduler interface, otherwise create new instance
     * of the scheduler interface by using configuration file.
     *
     * @return scheduler interface
     */
    public static SchedulerInterface getScheduler() throws SchedulingException {
        logger.info("Retrieving scheduler from scheduler factory");
        if (si == null) {
            return createInstance();
        }
        return si;
    }

    /**
     * @return factory instance
     * @throws SchedulingException
     */
    private static synchronized SchedulerInterface createInstance() throws SchedulingException {
        if (si != null) {
            return si;
        }
        si = context.getBean("Scheduler", Scheduler.class);
        return si;
    }


}
