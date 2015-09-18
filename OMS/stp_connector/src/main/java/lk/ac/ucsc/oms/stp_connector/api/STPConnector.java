package lk.ac.ucsc.oms.stp_connector.api;


import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.stp_connector.api.bean.STPValidationReply;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPConnectException;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPSenderException;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPValidationException;

import javax.jms.Message;


public interface STPConnector {


    void processMessage(FixOrderInterface fixOrder, String sessionId) throws STPConnectException;


    Order sendQueueMessage(FixOrderInterface.MessageType messageType, Order order, String fixMessage, String sessionQualifier)
            throws STPSenderException;

    Order isValidExchangeConnection(Order order) throws STPValidationException;


    STPValidationReply isValidFixConnection(Order order) throws STPValidationException;


    STPValidationReply isValidFixStatus(Order order) throws STPConnectException;


    void connectSession(String sessionId) throws STPConnectException;


    void disconnectSession(String sessionId) throws STPConnectException;


    void processMessage(Message message);

    void initialize() throws STPConnectException;

}
