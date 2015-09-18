package lk.ac.ucsc.oms.trade_controller.normalOrders;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;


import lk.ac.ucsc.oms.orderMgt.api.beans.Order;

import lk.ac.ucsc.oms.trade_controller.normalOrders.helper.OrderRiskHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import static lk.ac.ucsc.oms.common_utility.api.GlobalLock.lock;


@Stateless
public class NormalOrderControllerBean {
    private final Logger logger = LogManager.getLogger(NormalOrderControllerBean.class);
    private OrderRiskHandler orderRiskHandler;

    /**
     * Contain the logic related to normal order risk management those need to be handling inside a single transaction.
     *
     * @param order
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processNewOrder(Order order) {
        try {
            getOrderRiskHandler().manageRiskForNewOrder(order);
        } catch (Exception e) {
            logger.error("Error occurred while managing risk for new order", e);
            //Need to roll back all the changes did while managing risk
            throw new OMSRuntimeException("Error occurred while managing risk for new order: " + e.getMessage(), e);
        }
    }

    /**
     * contain the logic related to amend order those need to be handle inside a transaction
     *
     * @param order
     * @param oldOrder
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processAmendOrder(Order order, Order oldOrder) {
        try {
            getOrderRiskHandler().manageRiskForAmendOrder(order, oldOrder);
        } catch (Exception e) {
            logger.error("Error occurred while managing the risk for amend order", e);
            //to roll back all the updating happen in side this
            throw new OMSRuntimeException("Error occurred while managing the risk for amend order", e);
        }
    }

    /**
     * logic related to cancel those need to be running in a single transaction
     *
     * @param oldOrder
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processCancelOrder(Order oldOrder) {
        try {
            getOrderRiskHandler().manageRiskForCancelOrder(oldOrder);
        } catch (Exception e) {
            logger.error("Error occurred while managing the risk for cancel order", e);
            //to roll back all the updating happen in side this
            throw new OMSRuntimeException("Error occurred while managing the risk for cancel order", e);
        }
    }

    /**
     * logic related to expire order those need to be run inside a transaction
     *
     * @param order
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processExpireOrder(Order order) {
        long startTime=System.currentTimeMillis();
        synchronized (lock) {
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() -startTime));
            try {
                getOrderRiskHandler().manageRiskForExpireOrder(order);
            } catch (Exception e) {
                logger.error("Error occurred while managing the risk for Expire order", e);
                //to roll back all the updating happen in side this
                throw new OMSRuntimeException("Error occurred while managing the risk for Expire order", e);
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processRejectOrder(Order order) {
        long startTime =System.currentTimeMillis();
        synchronized (lock) {
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() -startTime));
            try {
                getOrderRiskHandler().manageRiskForRejectOrder(order);
            } catch (Exception e) {
                logger.error("Error occurred while managing the risk for Expire order", e);
                //to roll back all the updating happen in side this
                throw new OMSRuntimeException("Error occurred while managing the risk for Expire order", e);
            }
        }
    }


    /**
     * Access the orderRiskHandler variable always through this method
     *
     * @return
     */
    public OrderRiskHandler getOrderRiskHandler() {
        if (orderRiskHandler == null) {
            orderRiskHandler = new OrderRiskHandler();
        }
        return orderRiskHandler;
    }

}
