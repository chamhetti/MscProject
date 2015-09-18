package lk.ac.ucsc.oms.orderMgt.implGeneral.facade;



import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.common_utility.api.formatters.DecimalFormatterUtil;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.beans.OrderValidationReply;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache.ExecutionCacheFacade;
import lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache.OrderCacheFacade;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class OrderManagerFacade implements OrderManager {
    private static final int PRICE_FACTOR_CONST = 7;
    private static Logger logger = LogManager.getLogger(OrderManagerFacade.class);
    private OrderCacheFacade orderCache;
    private ExecutionCacheFacade executionCache;
    private AbstractSequenceGenerator sequenceGen;
    private String moduleCode;

    /**
     * set OrderCacheFacade
     *
     * @param orderCache order cache
     */
    public void setOrderCache(OrderCacheFacade orderCache) {
        this.orderCache = orderCache;
    }

    /**
     * set ExecutionCacheFacade
     *
     * @param executionCache execution cache
     */
    public void setExecutionCache(ExecutionCacheFacade executionCache) {
        this.executionCache = executionCache;
    }

    /**
     * set Module Code of the Module
     *
     * @param moduleCode code of the module
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        OrderValidator.setModuleCode(moduleCode);
    }

    public void setSequenceGen(AbstractSequenceGenerator sequenceGen) {
        this.sequenceGen = sequenceGen;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() throws OrderException, OrderExecutionException {
        orderCache.initialize();
        executionCache.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createOrder(Order ord) throws OrderException {
        logger.info("Add given Order to the Cache, Order : -{}", ord);
        if (ord == null || (ord.getStatus() != null && ord.getStatus() != OrderStatus.REJECTED)) {
            OrderValidator.validateOrderCreation(ord);  //validate the order before order creation
        }
        String lastID;
        try {
            logger.debug("Getting the order id from sequence generator");
            lastID = sequenceGen.getSequenceNumber();  //get the next sequence no for order

            logger.debug("Next ID generated from Sequence Generator : -{}", lastID);
        } catch (Exception e) {
            logger.error("Error in getting sequence number for order Id", e);
            throw new OrderException("Error in getting sequence number for order Id", e);
        }

        ord.setClOrdID(lastID); //set generated id as the clOrdId
        if (ord.getOrderNo() == null || "-1".equalsIgnoreCase(ord.getOrderNo()) || "0".equalsIgnoreCase(ord.getOrderNo())) {
            ord.setOrderNo(lastID);
        }

        if (ord.getInternalRejReason() == null || ord.getInternalRejReason().isEmpty() && ord.getText() != null) {
            ord.setInternalRejReason(ord.getText());
        }
        try {
            orderCache.createOrder(ord);
        } catch (OrderException e) {
            logger.error("Adding Order to the cache failed", e);
            throw new OrderException("Adding Order to the cache failed", e);

        }
        logger.debug("Order Added Successfully to the cache");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOrder(Order ord) throws OrderException {
        logger.info("Update the Order {}", ord);
        OrderValidator.validateOrderCreation(ord);  //validate the order before update
        ord.setLastUpdateDate(new Date());
        if (ord.getInternalRejReason() == null || ord.getInternalRejReason().isEmpty() && ord.getText() != null) {
            ord.setInternalRejReason(ord.getText());
        }
        orderCache.updateOrder(ord);
        logger.debug("Update Order successful");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order getOrderByClOrderId(String clOrderId) throws OrderException {
        logger.info("Get Order by Client Order ID : -{}", clOrderId);
        if (clOrderId == null || "".equals(clOrderId)) {
            throw new OrderException("Client Order ID cannot be null or empty");
        }
        logger.debug("Validation successful for Client Order ID : ", clOrderId);
        return orderCache.findOrder(clOrderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order applyPriceFactor(double priceFactor, Order order) throws OrderException {
        if (order == null) {
            throw new OrderException("Order cannot be null");
        }
        if (order.getPrice() != -1) {
            order.setPrice(DecimalFormatterUtil.round(order.getPrice() * priceFactor, PRICE_FACTOR_CONST));
        }
        if (order.getStopPx() != -1) {
            order.setStopPx(DecimalFormatterUtil.round(order.getStopPx() * priceFactor, PRICE_FACTOR_CONST));
        }
        if (order.getLastPx() != -1) {
            order.setLastPx(DecimalFormatterUtil.round(order.getLastPx() * priceFactor, PRICE_FACTOR_CONST));
        }
        if (order.getAvgPrice() != -1) {
            order.setAvgPrice(DecimalFormatterUtil.round(order.getAvgPrice() * priceFactor, PRICE_FACTOR_CONST));
        }
        if (order.getMaxPrice() != -1) {
            order.setMaxPrice(DecimalFormatterUtil.round(order.getMaxPrice() * priceFactor, PRICE_FACTOR_CONST));
        }

        return order;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderValidationReply isValidAmendOrder(Order amendOrder, Order oldOrder) throws InvalidOrderException {
        if (amendOrder == null || oldOrder == null) {
            throw new InvalidOrderException("Amend Order or Old Order cannot be null");
        }
        return OrderValidator.isValidAmendOrder(amendOrder, oldOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderValidationReply isValidCancelOrder(Order cancelOrder, Order oldOrder) throws InvalidOrderException {
        if (cancelOrder == null) {
            throw new InvalidOrderException("Cancel Order cannot be null");
        }
        if (oldOrder == null) {
            throw new InvalidOrderException("Old Order cannot be null");
        }
        return OrderValidator.isValidCancelOrder(cancelOrder, oldOrder);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public OrderValidationReply isValidOrderParameters(Order order) throws InvalidOrderException {
        if (order == null) {
            throw new InvalidOrderException("Order cannot be null");
        }
        return OrderValidator.isValidOrder(order);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addOrderExecution(Execution execution) throws OrderExecutionException {
        if (execution == null) {
            throw new OrderExecutionException("Execution cannot be null");
        }
        logger.debug("Execution received for clOdrID:" + execution.getClOrdID() + " Execution id:" + execution
                .getExecutionId() + " and ExeType is:" + execution.getExecutionType());
        executionCache.add(execution);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public Order getOrderByRemoteClOrderId(String remoteClOrdId) throws OrderException {
        logger.info("Get Order by Remote Client Order Id : " + remoteClOrdId);
        if (remoteClOrdId == null || "".equals(remoteClOrdId) || "-1".equals(remoteClOrdId)) {
            throw new OrderException("Remote Client Order ID cannot be null or empty");
        }
        return orderCache.getOrderByRemoteClOrdId(remoteClOrdId);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void persistOrdersAsBulk() throws OrderException {  //TODO--dasun-- this method should not be here
        orderCache.persistAsBulk();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persistExecutionsAsBulk() throws OrderExecutionException {
        executionCache.persistAsBulk();
    }


    /**
     * Get empty order bean
     *
     * @return Order
     */
    @Override
    public Order getEmptyOrder() {
        return new OrderBean();
    }

    /**
     * Get empty Execution bean
     *
     * @return Execution
     */
    @Override
    public Execution getEmptyExecution() {
        return new ExecutionBean();
    }

}
