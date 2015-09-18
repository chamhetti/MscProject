package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.schedule.*;
import lk.ac.ucsc.oms.scheduler.api.CronSchedulerName;
import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SystemConfigurationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerFactory {
    private static Logger logger = LogManager.getLogger(CustomerFactory.class);

    private static CustomerFactory factory;
    private static ApplicationContext context;
    private static SchedulerInterface scheduler;

    private CustomerFactory() throws CustomerException {
        synchronized (this) {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-customer.xml");
            }
        }
        if (scheduler == null) {
            try {
                scheduler = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
                throw new CustomerException("Error with Scheduler - Constructor failed", e);
            }
        }
    }

    public static CustomerFactory getInstance() throws CustomerException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }


    private static synchronized CustomerFactory createInstance() throws CustomerException {
        if (factory != null) {
            return factory;
        }
        factory = new CustomerFactory();
        try {

            //Starting the account schedule which persists changes in account cache to physical media
            AccountSchedule accountScheduler = context.getBean("accountScheduler", AccountSchedule.class);
            scheduler.scheduleThisEvent(accountScheduler);

            CashLogSchedule cashLogSchedule = context.getBean("cashLogScheduler", CashLogSchedule.class);
            scheduler.scheduleThisEvent(cashLogSchedule);

            CashSchedule cashSchedule = context.getBean("cashScheduler", CashSchedule.class);
            scheduler.scheduleThisEvent(cashSchedule);

            //Starting the customer schedule which persists changes in customer cache to physical media
            CustomerSchedule customerSchedule = context.getBean("customerScheduler", CustomerSchedule.class);
            scheduler.scheduleThisEvent(customerSchedule);

            HoldingLogSchedule holdingLogSchedule = context.getBean("holdingLogScheduler", HoldingLogSchedule.class);
            scheduler.scheduleThisEvent(holdingLogSchedule);

            HoldingSchedule holdingSchedule = context.getBean("holdingScheduler", HoldingSchedule.class);
            scheduler.scheduleThisEvent(holdingSchedule);

            //Starting the login history schedule which persists changes in login history cache to physical media
            LoginHistorySchedule loginHistoryScheduler = (LoginHistorySchedule) context.getBean("loginHistoryScheduler");
            scheduler.scheduleThisEvent(loginHistoryScheduler);


        } catch (SchedulingException e) {
            logger.error("Error In creating schedulers for Customer Module: " + e.getMessage(), e);
            throw new CustomerException("Error with Scheduler: " + e.getMessage(), e);
        }
        return factory;
    }

    public CustomerManager getCustomerManager() {
        return context.getBean("customerManager", CustomerManager.class);
    }

    public AccountManager getAccountManager() {
        return context.getBean("accountManager", AccountManager.class);
    }

    public CashManager getCashManager() {
        return context.getBean("cashManager", CashManager.class);
    }

    public HoldingManager getHoldingManager() {
        return context.getBean("holdingManager", HoldingManager.class);
    }

    public CustomerLoginManager getCustomerLoginManager() {
        return context.getBean("cusLoginManager", CustomerLoginManager.class);
    }

    public CustomerValidator getCustomerValidator() {
        return context.getBean("customerValidator", CustomerValidator.class);
    }

    public void initialize() throws OMSException {
        getAccountManager().initialize();
        getCustomerManager().initialize();
        getCashManager().initialize();
        getHoldingManager().initialize();
        getCustomerLoginManager().initialize();
    }

    public static void setContext(ApplicationContext context) {
        CustomerFactory.context = context;
    }


    public static void setScheduler(SchedulerInterface scheduler) {
        CustomerFactory.scheduler = scheduler;
    }

}
