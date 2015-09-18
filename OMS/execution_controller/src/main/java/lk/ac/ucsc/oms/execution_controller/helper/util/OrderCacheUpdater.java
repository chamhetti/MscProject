package lk.ac.ucsc.oms.execution_controller.helper.util;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class OrderCacheUpdater {
    private static Logger logger = LogManager.getLogger(OrderCacheUpdater.class);

    private static OrderCacheUpdater instance;

    private OrderCacheUpdater() {

    }

    public static synchronized OrderCacheUpdater getInstance() {
        if (instance == null) {
            instance = new OrderCacheUpdater();
        }
        return instance;
    }


    public void updateOrder(Order order, ApplicationContext ctx) throws OMSException {

        OrderManager orderManagementFacade = ctx.getBean(ModuleDINames.ORDER_FACTORY_DI_NAME,
                OrderManagementFactory.class).getOrderManagementFacadeInterface();
        //update the order in cache
        orderManagementFacade.updateOrder(order);
        //we check the order has any executions and if there is any execution in the order we need to persist them
        //in the cache.
        List<Execution> executionList = order.getExecutions();
        //update the order executions
        if (executionList != null) {
            for (Execution e : executionList) {
                logger.info("adding execution : " + e.toString());
                //add executions to cache
                orderManagementFacade.addOrderExecution(e);
            }
        }
        order.getExecutions().clear();
    }

}
