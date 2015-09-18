package lk.ac.ucsc.oms.trs_connector.api;

import lk.ac.ucsc.oms.orderMgt.api.beans.Order;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.*;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;


public interface NormalOrderControllerInterface {

    /**
     * process new a order from TRS
     *
     * @param newOrderRequest new order request
     * @return the order bean
     */
    Order processNewOrderFromTrs(NewOrderTRSRequest newOrderRequest);

    /**
     * method which process a new order from any client
     *
     * @param newOrderTRSRequest is the new order request bean
     * @return the order bean
     */
    Order processNewOrder(NewOrderTRSRequest newOrderTRSRequest);

    /**
     * process a amend order request from any client
     *
     * @param amendOrderTRSRequest is the amend order request bean
     * @return the order bean
     */
    void processAmendOrder(AmendOrderTRSRequest amendOrderTRSRequest);

    /**
     * method used to cancel a existing order
     *
     * @param cancelOrderTRSRequest is the cancel order request bean
     * @return the order bean
     */
    void processCancelOrder(CancelOrderTRSRequest cancelOrderTRSRequest);

    /**
     * method used to expire a existing order
     *
     * @param expireOrderTRSRequest is the expire order request bean
     * @return the order bean
     */
    void processExpireOrder(ExpireOrderTRSRequest expireOrderTRSRequest);

    /**
     * method used to expire a offline order
     *
     * @param expireOrderTRSRequest is the expire order request bean
     * @return the order bean
     */
    void processExpireOfflineOrder(ExpireOrderTRSRequest expireOrderTRSRequest);


  }
