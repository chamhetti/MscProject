package com.directfn.exchange_simulator.message_processor.api;


import com.directfn.exchange_simulator.message_processor.impl.beans.FIXPropertiesBean;

import javax.jms.Message;

/**
 * User: Ruchira Ranaweera
 * Date: 4/24/14
 */

/**
 * Message processor api
 */
public interface MessageProcessor {

    /**
     * This method will process the MapMessage
     *
     * @param message
     */
    void processRequestMessageReadFromToAppia(Message message);


    /**
     * This method will generate the response messages
     *
     * @param fixPropertiesBean
     * @return
     */
    boolean generateResponseFixMessage(FIXPropertiesBean fixPropertiesBean);

    /**
     * generate trading session status map message
     *
     * @param sessionId
     * @param eventType
     * @return
     */
    boolean generateTradingSessionStatusMapMessage(String sessionId, String eventType);

    /**
     * process new order message
     *
     * @param fixPropertiesBean
     */
    void processNewOrderMessage(FIXPropertiesBean fixPropertiesBean);

    /**
     * process replace order request message
     *
     * @param fixPropertiesBean
     */
    void processReplaceMessage(FIXPropertiesBean fixPropertiesBean);

    /**
     * process cancel order request message
     *
     * @param fixPropertiesBean
     */
    void processCancelMessage(FIXPropertiesBean fixPropertiesBean);



}
