package lk.ac.ucsc.oms.symbol.api;

import lk.ac.ucsc.oms.scheduler.api.CronSchedulerName;
import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.implGeneral.shedule.*;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SystemConfigurationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SymbolFacadeFactory {

    private static Logger logger = LogManager.getLogger(SymbolFacadeFactory.class);

    private static SymbolFacadeFactory factory;
    private static ApplicationContext context;
    private static SchedulerInterface schedulerInterface;

    /**
     * private constructor of the class
     */
    private SymbolFacadeFactory() throws SymbolManageException {
        synchronized (this) {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-symbol.xml");
            }
        }
        if (schedulerInterface == null) {
            try {
                schedulerInterface = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
                throw new SymbolManageException("Error with Scheduler - Constructor failed", e);
            }
        }
    }

    /**
     * Method use to get the instance of the factory
     *
     * @return instance of the factory
     * @throws SymbolManageException any error during initialization of the module
     */
    public static SymbolFacadeFactory getInstance() throws SymbolManageException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    /**
     * @return factory instance
     * @throws SymbolManageException
     */
    private static synchronized SymbolFacadeFactory createInstance() throws SymbolManageException {
        if (factory != null) {
            return factory;
        }
        factory = new SymbolFacadeFactory();
        //Register with Scheduler module to persist the cache periodically
        try {

            //Symbol module schedule which persist the symbols in the cache to physical storage at the define time intervals
            SymbolSchedule sch = context.getBean("scheduleSymbolModule", SymbolSchedule.class);
            schedulerInterface.scheduleThisEvent(sch);
               //schedule to run the symbol price data scheduler which update the data in cache to DB
            SymbolPriceDataSchedule symbolPriceDataSchedule = context.getBean("scheduleSymbolPriceData", SymbolPriceDataSchedule.class);
            schedulerInterface.scheduleThisEvent(symbolPriceDataSchedule);

        } catch (SchedulingException e) {
            logger.debug("Error in Scheduling.:" + e.getMessage(), e);
            throw new SymbolManageException("Error with Scheduler:" + e.getMessage(), e);
        }
        //Subscribe with the price integration module to update the price data of symbols

        return factory;
    }

    /**
     * Method use to get the main service api of the module
     *
     * @return SymbolManager implementation of the service interface
     */
    public SymbolManager getSymbolManager() {
        return context.getBean("SymbolManager", SymbolManager.class);
    }

    /**
     * Method used to get the symbol related validator which does the basic validation related to symbol
     *
     * @return
     */
    public SymbolValidator getSymbolValidator() {
        return context.getBean("symbolValidator", SymbolValidator.class);
    }




    /**
     * give symbol price data manager which can be used to manage the price data related to the symbol
     *
     * @return
     */
    public SymbolPriceManager getSymbolPriceDataManager() {
        return context.getBean("symbolPriceDataManager", SymbolPriceManager.class);
    }

    /**
     * Initialize all the service facades of the module
     *
     * @return the status
     */
    public void initialize() throws SymbolManageException {
        getSymbolManager().initialize();
        getSymbolPriceDataManager().initialize();    // todo temp

    }

    /**
     * set the spring configuration file path.
     * Usage in unit testing only.
     *
     * @param context
     */

    public static void setContext(ApplicationContext context) {
        SymbolFacadeFactory.context = context;
    }

    /**
     * set the scheduler interface
     * Usage in unit testing only.
     *
     * @param schedulerInterface
     */
    public static void setSchedulerInterface(SchedulerInterface schedulerInterface) {
        SymbolFacadeFactory.schedulerInterface = schedulerInterface;
    }




}
