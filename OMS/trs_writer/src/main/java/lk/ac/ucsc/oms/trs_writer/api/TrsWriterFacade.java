package lk.ac.ucsc.oms.trs_writer.api;


import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingLog;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;

import lk.ac.ucsc.oms.trs_writer.api.exceptions.TrsWriterException;

import java.util.List;


public interface TrsWriterFacade {

    /**
     * Write given response according to specified group
     *
     * @param group    message group
     * @param response response
     * @throws TrsWriterException
     */
    void writeResponse(int group, String response) throws TrsWriterException;

    /**
     * Writes the authentication response to TRS
     *
     * @param authResponse is the auth response
     * @throws TrsWriterException
     */
    void writeAuthResponse(String authResponse) throws TrsWriterException;

    /**
     * Writes the normal order response to the TRS
     *
     * @param order           is the order bean
     * @param allowedUserList is the allowed users list to be send order responses
     * @param customerNumber  is the the customer id
     * @param customerName    is the customer name
     */
    void writeNormalOrderResponse(Order order, List<String> allowedUserList, String customerNumber, String customerName, String execBrokerSid);

    /**
     * Writes invalid order response
     *
     * @param msgSessionID session id
     * @param channel      channel
     * @param errorCode    error code
     * @param uniqueId     unique id
     */
    void writeInvalidOrderResponse(String msgSessionID, int channel, int errorCode, String uniqueId);


    /**
     * Write the Admin message response to the TRS
     *
     * @param adminMessage is the admin message response
     */
    void writeAdminMsgResponse(String adminMessage);

    /**
     * Write the Inquiry response to the TRS
     *
     * @param inquiryResponse is the inquiry response message
     */
    void writeInquiryResponse(String inquiryResponse);

    /**
     * Write the account summary response to the customer
     *
     * @param cashAccount is the cash account of the customer
     * @param customer    is the customer
     * @param secAccNo    is the customer security account number
     */
    void writeAccountSummaryResponse(CashAccount cashAccount, Customer customer, String secAccNo, double buyingPower, double portfolioValue);


    /**
     * Writes trade responses such as internal order match response to TRS
     *
     * @param tradeResponse is the auth response
     * @throws TrsWriterException
     */
    void writeOtherTradeResponse(String tradeResponse) throws TrsWriterException;

    /**
     * Writes combined responses to TRS
     *
     * @param combinedResponse is the combined response
     */
    void writeSystemResponse(String combinedResponse);



}
