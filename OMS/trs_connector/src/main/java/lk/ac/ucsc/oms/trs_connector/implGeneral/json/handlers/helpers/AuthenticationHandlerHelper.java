package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.*;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.AuthResponseNormal;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request.AuthRequestNormal;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.request.*;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.AuthenticationBeansPopulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AuthenticationHandlerHelper {
    private static Logger logger = LogManager.getLogger(AuthenticationHandlerHelper.class);
    TrsConnectorFactory trsConnectorFactory;

    /**
     * Authenticates a client
     *
     * @param header        header
     * @param clientAuthReq authentication request
     * @return authentication response
     */
    public Message authenticate(Header header, Message clientAuthReq) {
        logger.info("Processing authenticating request -{}", clientAuthReq);
        AuthRequestNormal authReq;
        if (trsConnectorFactory == null) {
            trsConnectorFactory = TrsConnectorFactory.getInstance();
        }
        try {

            AuthenticationBeansPopulator populator = trsConnectorFactory.getAuthPopulator(header.getProtocolVersion());
            authReq = populator.populateAuthRequest(header, clientAuthReq);
        } catch (Exception e) {
            logger.info("Invalid Parameters for auth request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("Invalid auth request: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
            return new MessageImpl();
        }


        try {
            AuthenticationBeansPopulator populator = trsConnectorFactory.getAuthPopulator(header.getProtocolVersion());
            AuthResponseNormal loginReply;
            ClientChannel channel = ClientChannel.getEnum(header.getChannelId());

            //Do the authenticating base on client channels
            switch (channel) {
                case TWS:
                case IPHONE:
                case IPAD:
                case ANDROID:
                case ANDROID_TAB:
                case MOBWEB:
                case WEB:
                    loginReply = trsConnectorFactory.getLoginController().doLogin(authReq);
                    break;
                default:
                    throw new OMSException("Unsupported channel: " + header.getChannelId());
            }
            logger.info("Login reply -{}", loginReply);

            //generate the protocol response
            return populator.populateAuthResponse(header, loginReply);

        } catch (Exception e) {
            logger.error("Error while processing auth request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("System Error while processing auth request: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }

    }

    /**
     * Method used to log out a customer or a dealer
     *
     * @param logoutRequest logout request
     * @return logout response
     */
    public Message logOutUser(Header header, Message logoutRequest) {
        String userName = header.getLoggedInUserId();
        logger.info("Logging out the user with user name -{}", userName);
        if (userName == null || ("").equals(userName)) {
            logger.error("User Name is Null or Empty");
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            return new MessageImpl();
        }
        if (trsConnectorFactory == null) {
            trsConnectorFactory = TrsConnectorFactory.getInstance();
        }
        try {

            boolean result;
            int channel = header.getChannelId();
            switch (ClientChannel.getEnum(channel)) {
                case TWS:
                case IPHONE:
                case IPAD:
                case ANDROID:
                case MOBWEB:
                case WEB:
                default:
                    result = trsConnectorFactory.getLoginController().logOut(userName);
                    break;
            }
            AuthenticationBeansPopulator populator = trsConnectorFactory.getAuthPopulator(header.getProtocolVersion());
            Message response = populator.populateLogoutResponse(header, result);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return response;

        } catch (Exception e) {
            logger.error("Error while processing logout request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("System Error while processing logout request: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }

    }

    /**
     * Method used to changes the password of a customer or a dealer
     *
     * @return change password response
     */
    public Message changePasswords(Header header, Message request) {
        // moved to back-office
        return null;
    }


    /*
      *usage is only for unit test
     */
    public void setTrsConnectorFactory(TrsConnectorFactory trsConnectorFactory) {
        this.trsConnectorFactory = trsConnectorFactory;
    }


 }
