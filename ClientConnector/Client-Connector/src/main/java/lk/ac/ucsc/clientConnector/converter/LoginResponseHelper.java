package lk.ac.ucsc.clientConnector.converter;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacade;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacadeFactory;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.clientConnector.api.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginResponseHelper {
    private final Logger logger = LoggerFactory.getLogger(JsonConverter.class);
    private MessageProtocolFacade messageProtocolFacade = MessageProtocolFacadeFactory.getInstance().getMessageProtocolFacade();

    Map<String, String> populateAuthResponseInfo(String response) throws MessageProtocolException {
        Header header = null;
            header = messageProtocolFacade.getHeaderFromEnvelop(response);
            switch (messageProtocolFacade.parseVersion(header.getProtocolVersion())){
                case 1:
                    return populateAuthResponseInfoVersion1(response);

                default:
                    throw new MessageProtocolException("Error populating auth response");
            }

    }

    private Map<String, String> populateAuthResponseInfoVersion1(String response) {
        Map<String, String> authResponseMap = new HashMap<>();

        lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalResponseBean authResponse;
        try {
            authResponse = (lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalResponseBean)
                    messageProtocolFacade.getMessageFromEnvelop(response);


            authResponseMap.put(Converter.AUTH_STATUS, String.valueOf(authResponse.getAuthStatus()));
            authResponseMap.put(Converter.AUTH_USER_ID, authResponse.getUserId());
            authResponseMap.put(Converter.AUTH_LOGIN_ALIAS, authResponse.getLoginAlias());

            StringBuilder sb = new StringBuilder();
            String accountList = null;


            authResponseMap.put(Converter.AUTH_ACCOUNT_LIST, accountList);
        } catch (MessageProtocolException e) {
            logger.error("Error while converting auth response:", e);
        }
        return authResponseMap;
    }


    public String getLoginNameFromAuthRequest(String request) throws MessageProtocolException {
        lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalRequestBean authRequestJsonV1 =
                (lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalRequestBean) messageProtocolFacade.getMessageFromEnvelop(request);
        return authRequestJsonV1.getLoginName();

    }

    /**
     * Sets the MessageProtocolFacade
     *
     * @param messageProtocolFacade
     */
    public void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade) {
        this.messageProtocolFacade = messageProtocolFacade;
    }
}
