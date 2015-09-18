package lk.ac.ucsc.oms.trs_connector.implGeneral.fix;

import lk.ac.ucsc.oms.common_utility.api.enums.*;

import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal.AmendOrderTRSRequestBean;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal.CancelOrderTRSRequestBean;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal.NewOrderTRSRequestBean;

import java.util.Date;


public class FixOrderHandler {

    private NewOrderTRSRequest newOrderTRSRequest;
    private TrsConnectorFactory trsConnectorFactory;

    private AmendOrderTRSRequest amendOrderTRSRequest;
    private CancelOrderTRSRequest cancelOrderTRSRequest;

    /**
     * Process the order from FIX Channel
     *
     * @param fixOrder is the fix order bean
     */
    public void processNewFixOrder(FixOrderInterface fixOrder) {
        newOrderTRSRequest = new NewOrderTRSRequestBean();
        newOrderTRSRequest.setAccountNumber(fixOrder.getPortfolioNo());
        newOrderTRSRequest.setChannel(ClientChannel.FIX);
        newOrderTRSRequest.setExchangeCode(fixOrder.getSecurityExchange());
        newOrderTRSRequest.setExpireTime(fixOrder.getExpireTime());
        newOrderTRSRequest.setMaxFloor(fixOrder.getMaxFloor());
        newOrderTRSRequest.setMinQty(fixOrder.getMinQty());
        newOrderTRSRequest.setOrderQty(fixOrder.getOrderQty());
        newOrderTRSRequest.setOrderType(OrderType.getEnum(String.valueOf(fixOrder.getOrdType())));
        newOrderTRSRequest.setPrice(fixOrder.getPrice());
        newOrderTRSRequest.setRemoteClOrdId(fixOrder.getRemoteClOrdID());
        newOrderTRSRequest.setRemoteOrigOrdId(fixOrder.getRemoteOrigClOrdID());
        newOrderTRSRequest.setSide(OrderSide.getEnum(String.valueOf(fixOrder.getSide())));
        newOrderTRSRequest.setSymbolCode(fixOrder.getSymbol());
        newOrderTRSRequest.setTimeInForce(fixOrder.getTimeInForce());
        newOrderTRSRequest.setUserName(fixOrder.getUserID());
        newOrderTRSRequest.setFixVersion(fixOrder.getFixVersion());
        newOrderTRSRequest.setSecurityIDSource(fixOrder.getSecurityIDSource());
        newOrderTRSRequest.setOrderCategory(fixOrder.getOrderCategory());
        newOrderTRSRequest.setParentAccountNumber(fixOrder.getRoutingAccRef());
        newOrderTRSRequest.setRemoteAccountNumber(fixOrder.getRemoteAccountNumber());
        newOrderTRSRequest.setBrokerFIXID(fixOrder.getBrokerFIXID());
        newOrderTRSRequest.setTargetCompID(fixOrder.getTargetCompID());
        newOrderTRSRequest.setTargetSubID(fixOrder.getTargetSubID());
        newOrderTRSRequest.setSenderSubID(fixOrder.getSenderSubID());
        newOrderTRSRequest.setOnBehalfOfCompID(fixOrder.getOnBehalfOfCompID());
        newOrderTRSRequest.setOnBehalfOfSubID(fixOrder.getOnBehalfOfSubID());
        newOrderTRSRequest.setInternalMatchStatus(IOMStatus.DEFAULT);
        newOrderTRSRequest.setRemoteExchange(fixOrder.getRemoteExchange());
        newOrderTRSRequest.setRemoteSymbol(fixOrder.getRemoteSymbol());
        newOrderTRSRequest.setRemoteSecurityID(fixOrder.getRemoteSecurityID());
        newOrderTRSRequest.setConnectionSid(fixOrder.getConnectionSid());
        newOrderTRSRequest.setMessageType(fixOrder.getMessageType().getCode());
        //at this level check the fix channel order type. if the order type is STOP or STOP LIMIT then need to process this as condition order
        if (newOrderTRSRequest.getOrderType() == OrderType.STOP_LOSS || newOrderTRSRequest.getOrderType() == OrderType.STOP_LIMIT) {

        } else {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            trsConnectorFactory.getNormalOrderControllerInterface().processNewOrder(newOrderTRSRequest);
        }

    }

    /**
     * Process order amend request from the FIX Channel
     *
     * @param fixOrder is the fix order bean
     */
    public void processAmendFixOrder(FixOrderInterface fixOrder) {
        amendOrderTRSRequest = new AmendOrderTRSRequestBean();
        amendOrderTRSRequest.setChannel(ClientChannel.FIX);
        amendOrderTRSRequest.setExpireTime(fixOrder.getExpireTime());
        amendOrderTRSRequest.setMaxFloor(fixOrder.getMaxFloor());
        amendOrderTRSRequest.setMinQty(fixOrder.getMinQty());
        amendOrderTRSRequest.setOrderQty(fixOrder.getOrderQty());
        amendOrderTRSRequest.setOrderType(OrderType.getEnum(String.valueOf(fixOrder.getOrdType())));
        amendOrderTRSRequest.setPrice(fixOrder.getPrice());
        amendOrderTRSRequest.setRemoteClOrdId(fixOrder.getRemoteClOrdID());
        amendOrderTRSRequest.setRemoteOrigOrdId(fixOrder.getRemoteOrigClOrdID());
        amendOrderTRSRequest.setTimeInForce(fixOrder.getTimeInForce());
        amendOrderTRSRequest.setUserName(fixOrder.getUserID());
        amendOrderTRSRequest.setFixVersion(fixOrder.getFixVersion());
        amendOrderTRSRequest.setSecurityIDSource(fixOrder.getSecurityIDSource());
        amendOrderTRSRequest.setBrokerFIXID(fixOrder.getBrokerFIXID());
        amendOrderTRSRequest.setTargetCompID(fixOrder.getTargetCompID());
        amendOrderTRSRequest.setTargetSubID(fixOrder.getTargetSubID());
        amendOrderTRSRequest.setSenderSubID(fixOrder.getSenderSubID());
        amendOrderTRSRequest.setOnBehalfOfCompID(fixOrder.getOnBehalfOfCompID());
        amendOrderTRSRequest.setOnBehalfOfSubID(fixOrder.getOnBehalfOfSubID());
        amendOrderTRSRequest.setMessageType(fixOrder.getMessageType().getCode());
        amendOrderTRSRequest.setConnectionSid(fixOrder.getConnectionSid());
        if (trsConnectorFactory == null) {                  //usage for unit test
            trsConnectorFactory = TrsConnectorFactory.getInstance();
        }
        trsConnectorFactory.getNormalOrderControllerInterface().processAmendOrder(amendOrderTRSRequest);
    }


    /**
     * Process the FIX Channel cancel order request
     *
     * @param fixOrder is the fix order bean
     */
    public void processCancelFixOrder(FixOrderInterface fixOrder) {
        cancelOrderTRSRequest = new CancelOrderTRSRequestBean();
        cancelOrderTRSRequest.setChannel(ClientChannel.FIX);
        cancelOrderTRSRequest.setRemoteClOrdId(fixOrder.getRemoteClOrdID());
        cancelOrderTRSRequest.setRemoteOrigOrdId(fixOrder.getRemoteOrigClOrdID());
        cancelOrderTRSRequest.setUserName(fixOrder.getUserID());
        cancelOrderTRSRequest.setFixVersion(fixOrder.getFixVersion());
        cancelOrderTRSRequest.setSecurityIDSource(fixOrder.getSecurityIDSource());
        cancelOrderTRSRequest.setBrokerFIXID(fixOrder.getBrokerFIXID());
        cancelOrderTRSRequest.setTargetCompID(fixOrder.getTargetCompID());
        cancelOrderTRSRequest.setTargetSubID(fixOrder.getTargetSubID());
        cancelOrderTRSRequest.setSenderSubID(fixOrder.getSenderSubID());
        cancelOrderTRSRequest.setOnBehalfOfCompID(fixOrder.getOnBehalfOfCompID());
        cancelOrderTRSRequest.setOnBehalfOfSubID(fixOrder.getOnBehalfOfSubID());
        cancelOrderTRSRequest.setInternalMatchStatus(IOMStatus.DEFAULT);
        cancelOrderTRSRequest.setMessageType(fixOrder.getMessageType().getCode());
        cancelOrderTRSRequest.setConnectionSid(fixOrder.getConnectionSid());
        if (trsConnectorFactory == null) {
            trsConnectorFactory = TrsConnectorFactory.getInstance();
        }
        trsConnectorFactory.getNormalOrderControllerInterface().processCancelOrder(cancelOrderTRSRequest);
    }




    /*
     * usage is only for unit test case
     */
    public NewOrderTRSRequest getNewOrderTRSRequest() {
        return newOrderTRSRequest;
    }

    /*
     * usage is only for unit test case
   */
    public void setTrsConnectorFactory(TrsConnectorFactory trsConnectorFactory) {
        this.trsConnectorFactory = trsConnectorFactory;
    }


    /*
    * usage is only for unit test case
    */
    public AmendOrderTRSRequest getAmendOrderTRSRequest() {
        return amendOrderTRSRequest;
    }

    /*
   * usage is only for unit test case
  */
    public CancelOrderTRSRequest getCancelOrderTRSRequest() {
        return cancelOrderTRSRequest;
    }


}
