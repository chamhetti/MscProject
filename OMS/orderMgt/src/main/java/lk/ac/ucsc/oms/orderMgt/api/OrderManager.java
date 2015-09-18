package lk.ac.ucsc.oms.orderMgt.api;

import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.beans.OrderValidationReply;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderNotFoundException;

import java.util.List;


public interface OrderManager {

    void initialize() throws OrderException, OrderExecutionException;

    void createOrder(Order order) throws OrderException;

    void updateOrder(Order order) throws OrderException;

    Order getOrderByClOrderId(String clOrderId) throws OrderException;


    Order applyPriceFactor(double priceFactor, Order order) throws OrderException;


    void addOrderExecution(Execution execution) throws OrderExecutionException;


    OrderValidationReply isValidAmendOrder(Order amendOrder, Order oldOrder) throws InvalidOrderException;


    OrderValidationReply isValidCancelOrder(Order cancelOrder, Order oldOrder) throws InvalidOrderException;


    OrderValidationReply isValidOrderParameters(Order order) throws InvalidOrderException;

    Order getOrderByRemoteClOrderId(String remoteClOrdId) throws OrderException;


    Order getEmptyOrder();

    Execution getEmptyExecution();

    void persistOrdersAsBulk() throws OrderException;

    void persistExecutionsAsBulk() throws OrderExecutionException;


}
