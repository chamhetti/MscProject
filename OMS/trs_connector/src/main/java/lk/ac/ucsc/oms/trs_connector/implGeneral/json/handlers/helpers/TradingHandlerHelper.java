package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers;


import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.messaging_protocol_json.api.InvalidVersionException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.*;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;

import lk.ac.ucsc.oms.trs_connector.api.exceptions.RequestMappingException;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.ExpireOrderTRSRequestBean;

import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TradingHandlerHelper {
    private static Logger logger = LogManager.getLogger(TradingHandlerHelper.class);
    TrsConnectorFactory trsConnectorFactory;

    /**
     * Handle the new order request from the client
     *
     * @param header             json message header
     * @param newOrderFromClient is the new order json request
     */
    public void processNewOrder(Header header, Message newOrderFromClient) throws InvalidVersionException {
        trsConnectorFactory = getTrsConnectorFactoryInstance();
        NormalOrderBeansPopulator populator = trsConnectorFactory.getNormalOrderPopulator(header.getProtocolVersion());
        //populate the new order request from the json message
        NewOrderTRSRequest newOrderTRSRequest = populator.populateNewOrderRequest(header, newOrderFromClient);
        //at this level check the order type. if the order type is STOP or STOP LIMIT then need to process this as condition order
          trsConnectorFactory.getNormalOrderControllerInterface().processNewOrderFromTrs(newOrderTRSRequest);

        //no need to send replies since reply message will be push to the client side at the controller level
    }

    /**
     * Handle amend order request from the client
     *
     * @param header               message header
     * @param amendOrderFromClient is the amend order json request
     */
    public void processAmendOrder(Header header, Message amendOrderFromClient) throws InvalidVersionException {

        trsConnectorFactory = getTrsConnectorFactoryInstance();
        NormalOrderBeansPopulator populator = trsConnectorFactory.getNormalOrderPopulator(header.getProtocolVersion());
        //populate the amend order request from the client
        AmendOrderTRSRequest amendOrderTRSRequest = populator.populateAmendOrderRequest(header,
                amendOrderFromClient);
        //send the amend order request to the trade controller
        trsConnectorFactory.getNormalOrderControllerInterface().processAmendOrder(amendOrderTRSRequest);
        //no need to send replies since reply message will be push to the client side at the controller level
    }

    /**
     * Handle the cancel order request from the client
     *
     * @param header                json message header
     * @param header                json message header
     * @param cancelOrderFromClient is the cancel order json request
     */
    public void processCancelOrder(Header header, Message cancelOrderFromClient) throws InvalidVersionException {
        trsConnectorFactory = getTrsConnectorFactoryInstance();
        NormalOrderBeansPopulator populator = trsConnectorFactory.getNormalOrderPopulator(header.getProtocolVersion());
        //populate the cancel order request from the client
        CancelOrderTRSRequest cancelOrderTRSRequest = populator.populateCancelOrderRequest(header,
                cancelOrderFromClient);
        //send the cancel order request to the trade controller
        trsConnectorFactory.getNormalOrderControllerInterface().processCancelOrder(cancelOrderTRSRequest);
        //no need to send replies since reply message will be push to the client side at the controller level
    }



    /**
     * Handle the expire order request from the client
     *
     * @param header                json message header
     * @param expireOrderFromClient is the expire order mix request
     */
    public void processExpireOrder(Header header, Message expireOrderFromClient) throws InvalidVersionException {
        trsConnectorFactory = getTrsConnectorFactoryInstance();
        NormalOrderBeansPopulator populator = trsConnectorFactory.getNormalOrderPopulator(header.getProtocolVersion());
        ExpireOrderTRSRequest expireOrderRequest = new ExpireOrderTRSRequestBean();
        //populate the expire order request
        expireOrderRequest = populator.populateExpireOrderRequest(header,
                expireOrderFromClient);
        //send the reeverse order request to the trade controller
        trsConnectorFactory.getNormalOrderControllerInterface().processExpireOrder(expireOrderRequest);
    }

    /**
     * In order to make the code testable getter method is added
     *
     * @return
     */
    public TrsConnectorFactory getTrsConnectorFactoryInstance() {
        return TrsConnectorFactory.getInstance();
    }




}
