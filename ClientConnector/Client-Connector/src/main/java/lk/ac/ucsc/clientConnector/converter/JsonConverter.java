package lk.ac.ucsc.clientConnector.converter;

import lk.ac.ucsc.oms.messaging_protocol_json.api.*;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalRequestBean;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalResponseBean;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.LogoutResponseBean;

import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;


public class JsonConverter implements Converter {
    public static final int RESPONSE_CONST = 100;
    private static Logger logger = LoggerFactory.getLogger(JsonConverter.class);
    LoginResponseHelper loginResponseHelper = new LoginResponseHelper();
    //TrsResponseHelper trsResponseHelper = new TrsResponseHelper();
    private MessageProtocolFacade messageProtocolFacade = MessageProtocolFacadeFactory.getInstance().getMessageProtocolFacade();

    /**
     * {@inheritDoc}
     */
    @Override
    public TrsMessage convertToTrsMessage(String request) {
        TrsMessage trsMessage = new TrsMessage();
        trsMessage.setOriginalMessage(request);
        try {
            Envelope envelope = messageProtocolFacade.getEnvelope(request);
            Header header = envelope.getHeader();
            trsMessage.setGroup(header.getMsgGroup());
            trsMessage.setReqType(header.getMsgType());
            trsMessage.setMsgSessionId(header.getMsgSessionId());
            trsMessage.setChannelId(header.getChannelId());
            trsMessage.setClientIp(header.getClientIp());
            trsMessage.setUserId(header.getLoggedInUserId());
            trsMessage.setUniqueReqId(header.getUniqueReqID());
            trsMessage.setClientReqId(header.getClientReqID());
            trsMessage.setClientVersion(header.getClientVer());
            trsMessage.setErrorCode(header.getErrorCode());
            trsMessage.setMessageObject(envelope);
            trsMessage.setProcessingStatus(ProcessingStatus.VALID);
        } catch (Exception e) {
            trsMessage.setProcessingStatus(ProcessingStatus.PARSE_ERROR);
            trsMessage.setInvalidReason("Error parsing message. Please verify if the message is correct");
        }
        return trsMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToString(TrsMessage message) {
        Envelope messageEnvelop = (Envelope) message.getMessageObject();
        Header header = messageEnvelop.getHeader();
        header.setMsgSessionId(message.getMsgSessionId());
        header.setUniqueReqID(message.getUniqueReqId());
        header.setClientIp(message.getClientIp());
        header.setErrorCode(message.getErrorCode());
        header.setLoggedInUserId(message.getUserId());
        header.setChannelId(message.getChannelId());
        return messageProtocolFacade.getJSonString(messageEnvelop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPulseMessage(String sessionID) throws ClientConnectorException {
        Envelope pulseResponseEnvelope = new EnvelopeImpl();
        HeaderImpl header = new HeaderImpl();
        header.setMsgGroup(MessageConstants.GROUP_SYSTEM);
        header.setMsgType(MessageConstants.RESPONSE_TYPE_PULSE);
        header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
        header.setMsgSessionId(sessionID);
        pulseResponseEnvelope.setHeader(header);
        pulseResponseEnvelope.setBody(new MessageImpl());
        try {
            return messageProtocolFacade.getJSonString(pulseResponseEnvelope);
        } catch (Exception e) {
            throw new ClientConnectorException("Error converting pulse message to string", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrsMessage getInvalidRequestMessage(int errorCode, TrsMessage message) {
        message.setGroup(MessageConstants.GROUP_ERROR_MESSAGES);
        message.setReqType(MessageConstants.RESPONSE_TYPE_ERROR_INVALID_REQUEST);
        message.setTimeStamp(new Date());
        HeaderImpl header = new HeaderImpl();
        header.setMsgGroup(message.getGroup());
        header.setMsgType(message.getReqType());
        header.setRespStatus(0);
        header.setRespReason(message.getInvalidReason());
        header.setErrorCode(errorCode);
        try {
            message.setOriginalMessage(messageProtocolFacade.getJSonString(header, new MessageImpl()));  // empty body is sent
        } catch (Exception e) {
            message.setOriginalMessage("Invalid Request");
        }
        return message;
    }

    @Override
    public TrsMessage getErrorMessage(TrsMessage message) {
        HeaderImpl header = new HeaderImpl();
        int group = message.getGroup();
        if (group <= 0) {
            group = GroupConstants.GROUP_SYSTEM;
        }
        int type = message.getReqType();

        if (type <= 0) {
            type = MessageConstants.RESPONSE_TYPE_ERROR_SYSTEM;
        } else {
            type = type + 100;
        }
        message.setGroup(group);
        message.setReqType(type);
        message.setTimeStamp(new Date());
        header.setMsgGroup(group);
        header.setMsgType(type);
        header.setClientReqID(message.getClientReqId());
        header.setRespStatus(0);
        header.setRespReason(message.getInvalidReason());
        header.setErrorCode(message.getErrorCode());
        try {
            message.setOriginalMessage(messageProtocolFacade.getJSonString(header, new MessageImpl()));  // empty body is sent
        } catch (Exception e) {
            message.setOriginalMessage("Error!");
        }
        return message;
    }

    @Override
    public TrsMessage getVersionNotSupportedAuthErrorMessage(TrsMessage message) {
        HeaderImpl header = new HeaderImpl();
        message.setGroup(MessageConstants.GROUP_AUTHENTICATION);
        message.setReqType(MessageConstants.RESPONSE_TYPE_AUTH_NORMAL);
        message.setTimeStamp(new Date());
        header.setMsgGroup(MessageConstants.GROUP_AUTHENTICATION);
        header.setMsgType(MessageConstants.RESPONSE_TYPE_AUTH_NORMAL);
        header.setClientReqID(message.getClientReqId());
        header.setUniqueReqID(message.getUniqueReqId());
        header.setRespStatus(1);
        AuthenticationNormalResponseBean body = new AuthenticationNormalResponseBean();
        body.setAuthStatus(0);
        body.setRejectReason(message.getInvalidReason());
        try {
            message.setOriginalMessage(messageProtocolFacade.getJSonString(header, body));  // empty body is sent
        } catch (Exception e) {
            message.setOriginalMessage("Error!");
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogOutMessage(String userID, String sessionID) {
        Message logoutMessage = new LogoutResponseBean();
        HeaderImpl header = new HeaderImpl();
        header.setMsgGroup(MessageConstants.GROUP_AUTHENTICATION);
        header.setMsgType(MessageConstants.RESPONSE_TYPE_LOGOUT);
        header.setRespStatus(1);
        header.setLoggedInUserId(userID);
        header.setMsgSessionId(sessionID);
        return messageProtocolFacade.getJSonString(header, logoutMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrsMessage createTrsMessageFromAuthRequest(String authRequest) {
        TrsMessage trsMessage = new TrsMessage();
        trsMessage.setOriginalMessage(authRequest);
        try {
            Envelope envelope = messageProtocolFacade.getEnvelope(authRequest);
            Header header = messageProtocolFacade.getHeaderFromEnvelop(authRequest);
            trsMessage.setGroup(header.getMsgGroup());
            trsMessage.setReqType(header.getMsgType());
            trsMessage.setMsgSessionId(header.getMsgSessionId());
            trsMessage.setChannelId(header.getChannelId());
            trsMessage.setClientIp(header.getClientIp());
            trsMessage.setClientVersion(header.getClientVer());
            trsMessage.setClientReqId(header.getClientReqID());
            trsMessage.setUniqueReqId(header.getUniqueReqID());
            trsMessage.setMessageObject(envelope);
            trsMessage.setProcessingStatus(ProcessingStatus.VALID);
        } catch (Exception e) {
            trsMessage.setProcessingStatus(ProcessingStatus.PARSE_ERROR);
            trsMessage.setInvalidReason("Invalid message for authentication: Parse error");
        }
        try {
            String loginName = loginResponseHelper.getLoginNameFromAuthRequest(authRequest);
            trsMessage.setUserId(loginName);
        } catch (Exception e) {
            logger.info("Error getting login name from request message");
        }
        return trsMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> processAuthResponseInfo(String response) throws ClientConnectorException {
        try {
            return loginResponseHelper.populateAuthResponseInfo(response);
        } catch (MessageProtocolException e) {
            throw new ClientConnectorException("Error processing auth response info", e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAuthenticationMessage(int group, int type) {
        return (group == MessageConstants.GROUP_AUTHENTICATION) && (type == MessageConstants.REQUEST_TYPE_AUTH_NORMAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPulseMessage(int group, int type) {
        return (group == MessageConstants.GROUP_SYSTEM) && (type == MessageConstants.REQUEST_TYPE_PULSE);
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateLogin(String authRequest, String username, String password) {
        try {
            Header header = messageProtocolFacade.getHeaderFromEnvelop(authRequest);

            if (!isAuthenticationMessage(header.getMsgGroup(), header.getMsgType())) {
                return false;

            } else {
                AuthenticationNormalRequestBean authRequestJson = (AuthenticationNormalRequestBean) messageProtocolFacade.getMessageFromEnvelop(authRequest);
                String requestUserName = authRequestJson.getLoginName();
                String requestPassword = authRequestJson.getPassword();
                if (username == null || password == null || requestPassword == null || requestUserName == null) {
                    return false;
                }

                return username.equals(requestUserName) && password.equals(requestPassword);
            }
        } catch (Exception e) {
            return false;
        }
    }




    /**
     * Sets the MessageProtocolFacade
     *
     * @param messageProtocolFacade
     */
    public void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade) {
        this.messageProtocolFacade = messageProtocolFacade;
    }

    /**
     * Sets the LoginResponseHelper
     *
     * @param loginResponseHelper
     */
    public void setLoginResponseHelper(LoginResponseHelper loginResponseHelper) {
        this.loginResponseHelper = loginResponseHelper;
    }


}
