package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.*;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.AuthResponseNormal;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request.AuthRequestNormal;


public interface AuthenticationBeansPopulator {


    AuthRequestNormal populateAuthRequest(Header header, Message clientAuthReq) throws OMSException;

    Message populateAuthResponse(Header header, AuthResponseNormal authReply);



    Message populateLogoutResponse(Header header, boolean status);


    MessageProtocolFacade getMessageProtocolFacade() ;

    void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade);

}
