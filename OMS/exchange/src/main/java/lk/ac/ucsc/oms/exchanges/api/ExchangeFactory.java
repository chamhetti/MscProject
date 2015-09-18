package lk.ac.ucsc.oms.exchanges.api;

import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.exchanges.implGeneral.schedule.ExchangeSchedule;
import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExchangeFactory {
    private static Logger logger = LogManager.getLogger(ExchangeFactory.class);
    private static ExchangeFactory factory;
    private static ApplicationContext context;
    private static SchedulerInterface schedulerInterface;


    private ExchangeFactory() throws ExchangeException {
        synchronized (this) {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-exchanges.xml");
            }
        }
        if (schedulerInterface == null) {
            try {
                schedulerInterface = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
                throw new ExchangeException("Error with Scheduler - Constructor failed", e);
            }
        }
    }


    public static ExchangeFactory getInstance() throws ExchangeException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    private static synchronized ExchangeFactory createInstance() throws ExchangeException {
        if (factory != null) {
            return factory;
        }
        factory = new ExchangeFactory();
        try {

            ExchangeSchedule sch = (ExchangeSchedule) context.getBean("exchangeScheduler");
            schedulerInterface.scheduleThisEvent(sch);

        } catch (SchedulingException e) {
            logger.debug("Error in Scheduling.:" + e.getMessage(), e);
            throw new ExchangeException("Error with Scheduler:", e);
        }
        return factory;
    }

    public ExchangeManager getExchangeManager() {
        return context.getBean("exchangeManager", ExchangeManager.class);
    }

    public ExchangeValidator getExchangeValidator() {
        return context.getBean("exchangeValidator", ExchangeValidator.class);
    }

    public void initialize() throws ExchangeException {
        getExchangeManager().initialize();
    }

    public static void setContext(ApplicationContext context) {
        ExchangeFactory.context = context;
    }

    public static void setSchedulerInterface(SchedulerInterface schedulerInterface) {
        ExchangeFactory.schedulerInterface = schedulerInterface;
    }



}
