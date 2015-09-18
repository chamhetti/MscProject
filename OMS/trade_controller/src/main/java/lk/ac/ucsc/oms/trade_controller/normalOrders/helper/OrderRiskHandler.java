package lk.ac.ucsc.oms.trade_controller.normalOrders.helper;

import lk.ac.ucsc.oms.common_utility.api.MessageSender;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;

import lk.ac.ucsc.oms.orderMgt.api.beans.Order;

import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagementReply;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManager;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagerFactory;
import lk.ac.ucsc.oms.trade_controller.helper.ModuleDINames;
import lk.ac.ucsc.oms.trade_controller.helper.OrderCacheUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * contain the logic related to normal order handler those need to be run in side a single transaction
 */
public class OrderRiskHandler {
    private static Logger logger = LogManager.getLogger(OrderRiskHandler.class);
    private static ApplicationContext ctx;
    private OrderValidator orderValidator;
    private OrderCacheUpdater orderCacheUpdater;
    
    private OrderPropertyProcessorMFS propertyProcessor;
    private EquityRiskManager riskManager;
    private MessageSender messageSender;

    public OrderRiskHandler() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("/spring-config-trading_controller.xml");
        }
        orderValidator = new OrderValidatorMFS(ctx);
        orderCacheUpdater = new OrderCacheUpdater(ctx);
        propertyProcessor = new OrderPropertyProcessorMFS(ctx);
        messageSender = ctx.getBean(ModuleDINames.MESSAGE_SENDER_DI_NAME, MessageSender.class);
        riskManager =ctx.getBean(ModuleDINames.RISK_MANAGER_FACTORY_DI_NAME, EquityRiskManagerFactory.class).getEquityRiskManager();

    }

    /**
     * Use this constructor only for unit testing
     *
     * @param ctx
     */
    public OrderRiskHandler(ApplicationContext ctx) {

    }

    /**
     * contain risk management logic need to run inside a transaction
     *
     * @param order
     * @throws OMSException
     */
    public void manageRiskForNewOrder(Order order) throws OMSException {
        logger.info("Start Managing the risk for new order those need to be do inside a single transaction");

        //validate basic parameter of the order before risk management
        orderValidator.validateNewOrder(order);
        //if validation fail return the order to caller to send the reply to client
        if (order.getStatus() == OrderStatus.REJECTED) {
            //persist the order
            return;
        }

        //process the property of the order by applying price factors etc.
        try {
            propertyProcessor.processOrder(order);
        } catch (Exception e) {
            logger.error("Problem in populating the order parameters", e);
            order.setStatus(OrderStatus.REJECTED);
            order.setText(e.getMessage());
            return;
        }

        //Do the risk management of the order such as buying power, available qty etc.
        EquityRiskManagementReply EquityRiskManagementReply = riskManager.processNewOrder(order);
        if (!EquityRiskManagementReply.isSuccess()) {
            order.setStatus(OrderStatus.REJECTED);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, EquityRiskManagementReply);
        } else {
            if (order.getStatus() == OrderStatus.WAITING_FOR_APPROVAL) {
                logger.info("Order is waiting for approval");
            } else {
                logger.info("Risk Management for new order finished successfully");
                order.setStatus(OrderStatus.VALIDATED);
            }
        }
    }


    public OrderPropertyPopulator getOrderPropertyPopulator() {
        return OrderPropertyPopulator.getInstance();
    }



    /**
     * contain risk management logic of amend order need to run inside a transaction
     *
     * @param order
     * @param oldOrder
     * @throws OMSException
     */
    public void manageRiskForAmendOrder(Order order, Order oldOrder) throws OMSException {
        //Validate the Amend order
        orderValidator.validateAmendOrder(order, oldOrder);
        //if validation fail return the order to caller to send the reply to client
        if (order.getStatus() == OrderStatus.REJECTED) {
            order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
            return;
        }
        //process the property of the order by applying price factors etc.
        try {
            propertyProcessor.processOrder(order);
        } catch (Exception e) {
            logger.error("Problem in populating the order parameters", e);
            order.setStatus(OrderStatus.REJECTED);
            order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
            order.setText(e.getMessage());
            return;
        }
        EquityRiskManagementReply EquityRiskManagementReply = riskManager.validateRiskForAmendOrder(order);
        if (!EquityRiskManagementReply.isSuccess()) {
            order.setStatus(OrderStatus.REJECTED);
            order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
            getOrderPropertyPopulator().populateRejectOrderProperties(order, EquityRiskManagementReply);
        } else {
            if (order.getStatus() == OrderStatus.WAITING_FOR_APPROVAL) {
                logger.info("Order is waiting for approval");
            } else {
                logger.info("Risk Management for Amend order finished successfully");
                order.setStatus(OrderStatus.VALIDATED);
            }
        }

    }

    /**
     * contain risk management logic of cancel order need to run inside a transaction
     *
     * @param oldOrder
     * @throws OMSException
     */
    public void manageRiskForCancelOrder(Order oldOrder) throws OMSException {
        riskManager.processExpireOrder(oldOrder);
        oldOrder.setStatus(OrderStatus.CANCELED);
        orderCacheUpdater.updateOrder(oldOrder);
    }

    /**
     * contain risk management logic of expire order need to run inside a transaction
     *
     * @param order
     * @throws OMSException
     */
    public void manageRiskForExpireOrder(Order order) throws OMSException {
        riskManager.processExpireOrder(order);
        //set the status of the order as expired
        order.setStatus(OrderStatus.EXPIRED);
        //update the order status at cache
        orderCacheUpdater.updateOrder(order);
    }

    /**
     * contain risk management logic of reject order need to run inside a transaction
     *
     * @param order
     * @throws OMSException
     */
    public void manageRiskForRejectOrder(Order order) throws OMSException {
        riskManager.processExpireOrder(order);
        //set the status of the order as rejected.
        order.setStatus(OrderStatus.REJECTED);
        //save the updated order to cache.
        orderCacheUpdater.updateOrder(order);
    }



    public OrderCacheUpdater createOrderCacheUpdater() {
        return new OrderCacheUpdater(ctx);
    }

}
