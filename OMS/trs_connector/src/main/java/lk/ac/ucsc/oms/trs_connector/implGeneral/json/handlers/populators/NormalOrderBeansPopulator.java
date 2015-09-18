package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.*;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;

public interface NormalOrderBeansPopulator {
    NewOrderTRSRequest populateNewOrderRequest(Header header, Message req);


    AmendOrderTRSRequest populateAmendOrderRequest(Header header, Message req);


    CancelOrderTRSRequest populateCancelOrderRequest(Header header, Message req);



    ExpireOrderTRSRequest populateExpireOrderRequest(Header header, Message req);


}
