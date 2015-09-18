package lk.ac.ucsc.oms.trade_controller.normalOrders.helper;


import lk.ac.ucsc.oms.orderMgt.api.beans.Order;


public interface OrderValidator {
    /**
     * Validate the New Order before doing the risk management
     *
     * @param order is the order bean
     * @return the Order
     */
    Order validateNewOrder(Order order);

    /**
     * Validate the amend order
     *
     * @param amendOrder is the amend order
     * @param oldOrder   is the old older
     * @return the Order
     */
    Order validateAmendOrder(Order amendOrder, Order oldOrder);

    /**
     * Validate the Cancel Order
     *
     * @param cancelOrder is the cancel order
     * @param oldOrder    is the old order
     * @return the Order
     */
    Order validateCancelOrder(Order cancelOrder, Order oldOrder);

    /**
     * Validate the basic order parameters
     *
     * @param order
     * @return
     */
    Order validateBasicOrderProperties(Order order);

}
