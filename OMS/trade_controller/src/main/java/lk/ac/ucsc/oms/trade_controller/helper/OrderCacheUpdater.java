package lk.ac.ucsc.oms.trade_controller.helper;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;


public class OrderCacheUpdater {
    private static Logger logger = LogManager.getLogger(OrderCacheUpdater.class);
    private OrderManager orderManagementFacade;

    public OrderCacheUpdater(ApplicationContext ctx) {
        orderManagementFacade = ctx.getBean(ModuleDINames.ORDER_FACTORY_DI_NAME, OrderManagementFactory.class).getOrderManagementFacadeInterface();
    }


    public Order persistOrder(Order order) {
        try {
            orderManagementFacade.createOrder(order);
        } catch (OrderException e) {
            logger.error("Error in create order", e.getMessage(), e);
            throw new OMSRuntimeException(e.getMessage(), e);
        }
        return order;
    }


    public Order updateOrder(Order order) {
        try {
            orderManagementFacade.updateOrder(order);
        } catch (OrderException e) {
            logger.error("Error in update order", e.getMessage(), e);
            throw new OMSRuntimeException(e.getMessage(), e);
        }
        return order;
    }


    public Order addToOrderCache(Order order) {
        persistOrder(order);
        return order;
    }


}
