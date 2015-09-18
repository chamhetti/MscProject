package lk.ac.ucsc.oms.orderMgt.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.schedules.*;
import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Dasun perera
 * @author Thilina Jayamini
 *
 */

/**
 * Factory that is used to get the service interfaces of the order management module to access main services provided by the module \
 * Initializing and scheduling of the order management module also done through this factory.
 */
public class OrderManagementFactory {
    private static Logger logger = LogManager.getLogger(OrderManagementFactory.class);

    private static ApplicationContext context;
    private static OrderManagementFactory factory;
    private static SchedulerInterface schedulerInterface;

    /**
     * Private constructor to avoid instantiation to have only one factory instance.
     */
    private OrderManagementFactory() {
        synchronized (this) {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-orderMgt.xml");
            }
        }
        if (schedulerInterface == null) {
            try {
                schedulerInterface = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
            }
        }
    }

    /**
     * Get the instance of OrderManagementFactory
     *
     * @return OrderManagementFactory
     * @throws OrderException
     */
    public static synchronized OrderManagementFactory getInstance() throws OrderException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    private static synchronized OrderManagementFactory createInstance() throws OrderException {
        if (factory != null) {
            return factory;
        }
        factory = new OrderManagementFactory();
        try {
            OrderSchedule orderSchedule = context.getBean("orderSchedule", OrderSchedule.class);
            schedulerInterface.scheduleThisEvent(orderSchedule);

            OrderExecutionSchedule orderExecutionSchedule = context.getBean("orderExecutionSchedule", OrderExecutionSchedule.class);
            schedulerInterface.scheduleThisEvent(orderExecutionSchedule);



        } catch (SchedulingException e) {
            logger.error("Error In creating schedulers for order Module: " + e.getMessage(), e);
            throw new OrderException("Error with Scheduler: " + e.getMessage(), e);
        }


        return factory;
    }


    public static void setContext(ApplicationContext context) {
        OrderManagementFactory.context = context;
    }

    public static void setSchedulerInterface(SchedulerInterface schedulerInterface) {
        OrderManagementFactory.schedulerInterface = schedulerInterface;
    }

    /* get the implementation of the OrderManagementFacadeInterface bean
    *
    * @return OrderManagementFacadeInterface
    */
    public OrderManager getOrderManagementFacadeInterface() {
        return context.getBean("orderManagerFacade", OrderManager.class);
    }

    /**
     * get the implementation of the OrderSearchHistoryFacadeInterface bean
     *
     * @return OrderSearchHistoryFacadeInterface
     */
    public OrderHistorySearchFacadeInterface getOrderHistorySearchFacade() {
        return context.getBean("orderHistorySearchFacade", OrderHistorySearchFacadeInterface.class);
    }

    /**
     * get the implementation of the OrderSearchFacadeInterface bean
     *
     * @return OrderSearchFacadeInterface
     */
    public OrderSearchFacadeInterface getOrderSearchFacade() {
        return context.getBean("orderSearchFacade", OrderSearchFacadeInterface.class);
    }



    /**
     * Returns the implementation of the BasketManager
     *
     * @return OrderSearchFacadeInterface
     */
    public ExecutionAuditRecordManager getExecutionAuditRecordManager() {
        return context.getBean("executionAuditManagerFacade", ExecutionAuditRecordManager.class);
    }

    /**
     * Initialize all the service facades of the module
     */
    public void initialize() throws OMSException {
        getOrderManagementFacadeInterface().initialize();
        getExecutionAuditRecordManager().initialize();
    }

    /**
     * Method is called by the EJB Scheduler
     * This will call the order manager facade to do the persistence of the orders and executions if available
     */
    public void persistOrderDetails() throws OMSException {   //TODO --dasun this is not needed - This is needed for scheduling order cache persistance using EJB schedulers as we are not using quartz schedulers for order cache.
        //persist order beans cache to DB
        getOrderManagementFacadeInterface().persistOrdersAsBulk();
        //persist execution beans cache to DB
        getOrderManagementFacadeInterface().persistExecutionsAsBulk();
    }


}
