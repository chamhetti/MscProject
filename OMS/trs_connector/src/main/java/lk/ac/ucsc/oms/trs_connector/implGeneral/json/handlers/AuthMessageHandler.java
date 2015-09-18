package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers.AuthenticationHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AuthMessageHandler implements MessageHandler {
    private static Logger logger = LogManager.getLogger(AuthMessageHandler.class);

    private AuthenticationHandlerHelper authenticationHandlerHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope processMessage(Header header, Message message) throws NoSuchProcessException {
        Message response = null;
        logger.info("New Auth Request: " + header.getMsgType());

        switch (header.getMsgType()) {
            case MessageConstants.REQUEST_TYPE_AUTH_NORMAL:
                response = authenticationHandlerHelper.authenticate(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_AUTH_NORMAL);
                break;
            case MessageConstants.REQUEST_TYPE_CHANG_PWD:
                response = authenticationHandlerHelper.changePasswords(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_CHANG_PWD);
                break;
            case MessageConstants.REQUEST_TYPE_LOGOUT:
                response = authenticationHandlerHelper.logOutUser(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_LOGOUT);
                break;

            default:
                throw new NoSuchProcessException("No such request type in authentication group");
        }
        //write the message to trs
        return new EnvelopeImpl((HeaderImpl) header, response);
    }

    /**
     * method to set authentication handler helper to the class
     *
     * @param authenticationHandlerHelper
     */
    public void setAuthenticationHandlerHelper(AuthenticationHandlerHelper authenticationHandlerHelper) {
        this.authenticationHandlerHelper = authenticationHandlerHelper;
    }
}
