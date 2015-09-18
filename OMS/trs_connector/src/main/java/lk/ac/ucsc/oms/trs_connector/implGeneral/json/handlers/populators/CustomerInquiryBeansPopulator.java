package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.CustomerRecordTrs;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerAccountDetailTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerDetailTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerSearchTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.CustomerListByUserReply;


public interface CustomerInquiryBeansPopulator {

    Message populateCustomerDetailResponse(CustomerRecordTrs customerRecord);


    Message populateCustomerListByUserResponse(CustomerListByUserReply replyTRS);

    CustomerDetailTRSRequest populateCustomerDetailTRSRequest(Message req, Header header);

    CustomerSearchTRSRequest populateCustomerSearchTRSRequest(Message req);

    CustomerAccountDetailTRSRequest populateCustomerAccountDetailTRSRequest(Message reqObj);


    Message populateReportUrlResponse(String reportUrl);
}
