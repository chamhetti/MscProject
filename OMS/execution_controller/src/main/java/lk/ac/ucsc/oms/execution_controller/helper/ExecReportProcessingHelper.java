package lk.ac.ucsc.oms.execution_controller.helper;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;


import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManager;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagerFactory;
import lk.ac.ucsc.oms.execution_controller.helper.util.ModuleDINames;
import lk.ac.ucsc.oms.execution_controller.helper.util.OrderCacheUpdater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExecReportProcessingHelper {

    public static final String CHANGE_ORDER_ACTION = "CHANGE";
    private static final String NEW_ORDER_ACTION = "NEW";
    private static final double AVG_PRICE_LOWER_LIMIT = 0.99;
    private static final double AVG_PRICE_UPPER_LIMIT = 1.01;
    private Logger logger = LogManager.getLogger(ExecReportProcessingHelper.class);
    private ApplicationContext ctx;
    private EquityRiskManager riskManager;
    private OrderManager orderManager;

    static long exec_count = 0;
    private OrderCacheUpdater orderCacheUpdater;

    public ExecReportProcessingHelper() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("/spring-config-execution_controller.xml");
        }
        riskManager = ctx.getBean(ModuleDINames.RISK_MANAGER_FACTORY_DI_NAME, EquityRiskManagerFactory.class).getEquityRiskManager();
        orderManager = ctx.getBean(ModuleDINames.ORDER_MANAGER_DI_NAME, OrderManager.class);
        orderCacheUpdater = OrderCacheUpdater.getInstance();
    }


    public ExecReportProcessingHelper(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }


    public Order processNewReply(FixOrderInterface fixOrder) throws OMSException {
        logger.info("Processing the queued order response - {}", fixOrder);
        long startTime = System.currentTimeMillis();
        Order order = orderManager.getOrderByClOrderId(fixOrder.getClordID());
        logger.info("Order at OMS - {}", order);

        if (order.getStatus() != OrderStatus.EXPIRED && order.getStatus() != OrderStatus.PARTIALLY_FILLED &&
                order.getStatus() != OrderStatus.FILLED && order.getStatus() != OrderStatus.CANCELED) {
            order.setStatus(OrderStatus.NEW);
            order.setOrdID(fixOrder.getOrderID());
            order.setTransactionTime(fixOrder.getTransactTime());
            order.setExecID(fixOrder.getExecID());
            order.setExecType(String.valueOf(fixOrder.getExecType()));
            orderCacheUpdater.updateOrder(order, ctx);
            logger.info("Updated the order status as queued");
            long endTime = System.currentTimeMillis();
            logger.info("Time Elapsed For New Order Reply- " + (endTime - startTime));
        } else {
            logger.error("Order already Filled| Partially Filled| Expired| Cancelled. order status - {},  Order ClOrd id - {}", order.getStatus(), order.getClOrdID());
        }
        return order;

    }


    public Order processOrderAcceptedReply(OrderStatus status, FixOrderInterface fixOrder) throws OMSException {
        logger.info("Process the Accept order - {}", fixOrder);
        long startTime = System.currentTimeMillis();
        String clOrderId = fixOrder.getClordID();
        Order order = orderManager.getOrderByClOrderId(clOrderId);

        if (order.getStatus() != OrderStatus.EXPIRED && order.getStatus() != OrderStatus.PARTIALLY_FILLED && order
                .getStatus() != OrderStatus.FILLED && order.getStatus() != OrderStatus.NEW &&
                order.getStatus() != OrderStatus.REPLACED && order.getStatus() != OrderStatus.CANCELED) {
            order.setStatus(status);
            order.setExecID(fixOrder.getExecID());
            order.setExecType(String.valueOf(fixOrder.getExecType()));
            order.setOrdID(fixOrder.getOrderID());
            orderCacheUpdater.updateOrder(order, ctx);
        } else {
            logger.error("Order already Filled| Partially Filled| Expired| Cancelled. Order Status - {}, Order ClOrd ID - {}", order.getStatus(), order.getClOrdID());
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Accepted Reply- " + (endTime - startTime));
        return order;
    }


    public Order processReplaceReply(String tranSacTime, FixOrderInterface fixOrder) throws OMSException {
        long startTime = System.currentTimeMillis();
        String clOrderId = fixOrder.getClordID();
        //getting the original order from cache.
        Order order = orderManager.getOrderByClOrderId(clOrderId);
        if (order.getOrigClOrdID() == null || "-1".equalsIgnoreCase(order.getOrigClOrdID()) || "0".equalsIgnoreCase(order.getOrigClOrdID())) {
            logger.info("There is no valid original order hence this is not a valid replace reply");
            return order;
        }
        Order oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());
        riskManager.processReplaceOrder(order);
       if (order.getCumQty() < oldOrder.getCumQty()) {
            order.setCumCommission(oldOrder.getCumCommission());
            order.setCumQty(oldOrder.getCumQty());
            order.setCumExchangeCommission(oldOrder.getCumExchangeCommission());
            order.setCumThirdPartyCommission(oldOrder.getCumThirdPartyCommission());
            order.setCumBrokerCommission(oldOrder.getCumBrokerCommission());
            order.setCumParentCommission(oldOrder.getCumParentCommission());
            order.setCumNetValue(oldOrder.getCumNetValue());
            order.setCumNetSettle(oldOrder.getCumNetSettle());
            order.setCumOrderValue(oldOrder.getCumOrderValue());
            order.setAvgPrice(oldOrder.getAvgPrice());
        }

        if (oldOrder != null) {
            order.setLeavesQty(order.getQuantity() - oldOrder.getCumQty());
        }

        if (order.getQuantity() == order.getCumQty()) {
            order.setStatus(OrderStatus.FILLED);
        } else {
            order.setStatus(OrderStatus.REPLACED);
        }
        //update the order status in amend order and original order
        oldOrder.setStatus(OrderStatus.INVALIDATED);
        oldOrder.setIntermediateStatus(OrderStatus.INVALIDATED);
        oldOrder.getExecutions().clear();

        order.setTransactionTime(tranSacTime);
        order.setExecID(fixOrder.getExecID());
        order.setExecType(String.valueOf(fixOrder.getExecType()));


        orderCacheUpdater.updateOrder(order, ctx);
        orderCacheUpdater.updateOrder(oldOrder, ctx);
        long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Replace Order Reply- " + (endTime - startTime));
        return order;
    }


    public Order processCancelReply(String tranSacTime, FixOrderInterface fixOrder) throws OMSException {
        long startTime = System.currentTimeMillis();
        String clOrderId = fixOrder.getClordID();
        Order order = orderManager.getOrderByClOrderId(clOrderId);

        Order oldOrder = null;
        if (order.getOrigClOrdID() != null && !("-1").equals(order.getOrigClOrdID()) && !("").equals(order.getOrigClOrdID())) {
            try {
                oldOrder = orderManager.getOrderByClOrderId(order.getOrigClOrdID());
            } catch (Exception e) {
                logger.error("Old Order not found for the order id - {} ", clOrderId);
            }
        }
        if (oldOrder != null) {
            order.setCumCommission(oldOrder.getCumCommission());
            order.setCumQty(oldOrder.getCumQty());
            if (order.getCumQty() > 0) {
                order.setNetSettle(oldOrder.getCumNetSettle());
                order.setNetValue(oldOrder.getCumNetValue());
                order.setOrderValue(oldOrder.getCumOrderValue());
                order.setCommission(order.getCumCommission());
            }
            oldOrder.getExecutions().clear();

            riskManager.processExpireOrder(order);
            //update the order status of cancel order and original order
            oldOrder.setStatus(OrderStatus.INVALIDATED_BY_CANCEL);
            oldOrder.setIntermediateStatus(OrderStatus.INVALIDATED_BY_CANCEL);
            order.setStatus(OrderStatus.CANCELED);

        } else {
            riskManager.processExpireOrder(order);

        }

        order.setTransactionTime(tranSacTime);
        order.setExecID(fixOrder.getExecID());
        order.setExecType(String.valueOf(fixOrder.getExecType()));

        orderCacheUpdater.updateOrder(order, ctx);
        if (oldOrder != null) {
            orderCacheUpdater.updateOrder(oldOrder, ctx);
        }
               long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Cancel Order Reply- " + (endTime - startTime));
        return order;
    }


    public Order processExpireReply(String tranSacTime, FixOrderInterface fixOrder) throws OMSException {
        long startTime = System.currentTimeMillis();
        String clOrderId = fixOrder.getClordID();
        //getting the order from cache
        Order order = orderManager.getOrderByClOrderId(clOrderId);
        //doing the risk management
        riskManager.processExpireOrder(order);
        //update the order status
        order.setStatus(OrderStatus.EXPIRED);
        order.setTransactionTime(tranSacTime);
        order.setExecID(fixOrder.getExecID());
        order.setExecType(String.valueOf(fixOrder.getExecType()));

        if (order.getCumQty() > 0) {
            order.setOrderValue(order.getCumOrderValue());
            order.setNetValue(order.getCumNetValue());
            order.setNetSettle(order.getCumNetSettle());
            order.setCommission(order.getCumCommission());
        }
        order.getExecutions().clear();
        orderCacheUpdater.updateOrder(order, ctx);
        long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Expire Order Reply- " + (endTime - startTime));
        return order;
    }


    public Order processRejectReply(String rejectReason, String tranSacTime,
                                    FixOrderInterface fixOrder) throws OMSException {
        long startTime = System.currentTimeMillis();
        String clOrderId = fixOrder.getClordID();
        //loading the order from cache

        Order order = orderManager.getOrderByClOrderId(clOrderId);
        //doing the risk management
        riskManager.processExpireOrder(order);

        //update the order status
        order.setStatus(OrderStatus.REJECTED);
        order.setTransactionTime(tranSacTime);

        if (rejectReason == null) {               // todo update test - reject reason should not null for DT
            order.setText("Order is rejected");
        } else {
            order.setText(rejectReason);
        }

        order.setExecID(fixOrder.getExecID());
        order.setExecType(String.valueOf(fixOrder.getExecType()));

        orderCacheUpdater.updateOrder(order, ctx);

              long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Reject Order Reply- " + (endTime - startTime));
        return order;
    }


    public Order processExecutionReply(FixOrderInterface fixOrder) throws OMSException {
        long startTime = System.currentTimeMillis();
        //loading the order from cache
        Order order = orderManager.getOrderByClOrderId(fixOrder.getClordID());

        //Recalculate the average price if the average price is not a positive figure
        calculateAveragePrice(fixOrder, order);
        //fill the order using the data in response.
        order.setLastPx(fixOrder.getLastPx());
        order.setLastShares(fixOrder.getLastShares());
        order.setLeavesQty(fixOrder.getLeavesQty());
        //set the order id for safe side
        order.setOrdID(fixOrder.getOrderID());
        order.setCumQty(fixOrder.getCumQty());
        order.setExecID(fixOrder.getExecID());
        order.setMubExecID(getExecutionId());
        order.setPrice(fixOrder.getPrice());           // apply manual execution price changes
        order.setExecType(String.valueOf(fixOrder.getExecType()));
        order.setTransactionTime(fixOrder.getTransactTime());
        //set the status of the order as fill or pfill
        if (order.getStatus() != OrderStatus.FILLED) {
            order.setStatus(OrderStatus.getEnum(String.valueOf(fixOrder.getOrdStatus())));
        } else {
            logger.error("Out of order execution received - {}", fixOrder);
        }
        // Process order for RMS
        riskManager.processExecuteOrder(order);
        //generate and add the execution to order execution list
        order.getExecutions().clear();
        order.getExecutions().add(generateExecution(order, fixOrder));

        orderCacheUpdater.updateOrder(order, ctx);

        long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Execute Order Reply- " + (endTime - startTime));
        return order;
    }


    private Execution generateExecution(Order order, FixOrderInterface fixOrder) throws OMSException {
        Execution execution = orderManager.getEmptyExecution();
        execution.setClOrdID(order.getClOrdID());
        execution.setCumQty(order.getCumQty());
        execution.setExecutionId(fixOrder.getExecID());
        execution.setExecutionType(OrderExecutionType.getEnum(String.valueOf(fixOrder.getExecType())));
        execution.setOrdQty(order.getQuantity());
        execution.setLastPx(fixOrder.getLastPx());
        execution.setLastShare(fixOrder.getLastShares());
        execution.setLeaveQty(fixOrder.getLeavesQty());
        execution.setOrderNo(order.getOrderNo());
        execution.setOrdID(fixOrder.getOrderID());
        execution.setStatus(OrderStatus.getEnum(String.valueOf(fixOrder.getOrdStatus())));
        execution.setTransactionTime(fixOrder.getTransactTime());
        execution.setAvgPrice(order.getAvgPrice());
        execution.setCommission(order.getCommissionDiff());
        execution.setDateTime(new Date());
        return execution;
    }


    /**
     * Calculate the average price of the order using the current order execution
     *
     * @param fixOrder is the fix order
     * @param order    is the order bean
     */
    private void calculateAveragePrice(FixOrderInterface fixOrder, Order order) {
        if (fixOrder.getAvgPx() <= 0) {
            logger.info("Average price not came with the order. {}. Hence calculated the average price from the OMS side and assign to the order", fixOrder.getClordID());
            try {
                double dNewAvgPrice;
                if (order.getAvgPrice() > 0 && order.getCumQty() > 0 && fixOrder.getCumQty() > 0) {
                    dNewAvgPrice = (order.getAvgPrice() * order.getCumQty() + fixOrder.getLastShares() * fixOrder.getLastPx
                            ()) / fixOrder.getCumQty();
                    if (dNewAvgPrice > 0 && dNewAvgPrice * AVG_PRICE_LOWER_LIMIT < order.getAvgPrice() &&
                            dNewAvgPrice * AVG_PRICE_UPPER_LIMIT > order.getAvgPrice()) {
                        order.setAvgPrice(dNewAvgPrice);
                    } else {
                        order.setAvgPrice(fixOrder.getLastPx());
                    }
                } else {
                    order.setAvgPrice(fixOrder.getLastPx());
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            order.setAvgPrice(fixOrder.getAvgPx());
        }
    }

    /**
     * @return the created mubasher execution id
     */
    private String getExecutionId() {
        String execId = "";
        Format formatter;
        try {
            if (exec_count >= 999) {
                exec_count = 0;
            }
            formatter = new SimpleDateFormat("MMddhhmmss.SSS");
            //app server id is taken from the system property
            execId = this.getAppServerId() + "." + formatter.format(new Date()) + "." + String.format("%03d", exec_count++);

        } catch (Exception e) {
            logger.error("Error in creating the mubasher execution id");
        }
        return execId;
    }

    public String getAppServerId() {
        return System.getProperty("APPSVR_ID");
    }


}
