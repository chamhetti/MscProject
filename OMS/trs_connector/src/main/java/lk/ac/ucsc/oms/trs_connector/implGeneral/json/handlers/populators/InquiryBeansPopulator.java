package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.MarginableBuyingPowerRecord;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.BuyingPowerTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.PortfolioDetailsTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.*;


public interface InquiryBeansPopulator {


    BuyingPowerTRSRequest populateBuyingPowerRequest(Header header, Message inquiryReq);

    PortfolioDetailsTRSRequest populatePortfolioDetailsRequest(Header header, Message inquiryReq);



}
