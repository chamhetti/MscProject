package lk.ac.ucsc.oms.trs_connector.api;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.CustomerRecordTrs;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.*;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.*;


public interface InquiryControllerInterface {
    CustomerRecordTrs getCustomerDetails(String customerNumber, ClientChannel channel, String dealerID, boolean isGlobalDealer) throws OMSException;

    OrderInquiryReply getOrderList(String accountNumber) throws OMSException;

    OrderInquiryReply searchOrdersWithPaging(OrderSearchTRSRequest searchRequestTRS) throws OMSException;

    OrderInquiryReply getOrderDetails(String clOrderID) throws OMSException;

    CustomerListByUserReply getCustomerListByUser(long userId) throws OMSException;
}
