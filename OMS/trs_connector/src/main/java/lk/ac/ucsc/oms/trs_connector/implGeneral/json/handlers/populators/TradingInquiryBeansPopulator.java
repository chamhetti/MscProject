package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators;


import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.*;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.OrderInquiryReply;


public interface TradingInquiryBeansPopulator {

    OrderListTRSRequest populateOrderListRequest(Message reqObj);


    OrderSearchTRSRequest populateOrderSearchRequest(Message reqObj);


    OrderDetailTRSRequest populateOrderDetailRequest(Message reqObj);


    Message populateOrderListResponse(OrderInquiryReply trsResp, OrderListTRSRequest trsReq);


    Message populateOrderSearchResponse(OrderInquiryReply trsResp, OrderSearchTRSRequest trsReq);


    Message populateOrderDetailsResponse(OrderInquiryReply trsResp);


}
