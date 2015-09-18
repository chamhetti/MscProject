package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.ver1;



import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.common_utility.api.formatters.DateFormatterUtil;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacade;
import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.*;

import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common.TradingAccountBean;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.AuthResponseNormal;

import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request.AuthRequestNormal;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.request.*;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.AuthenticationBeansPopulator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class AuthenticationBeansPopulatorImpl implements AuthenticationBeansPopulator {

    private static Logger logger = LogManager.getLogger(AuthenticationBeansPopulatorImpl.class);
    private MessageProtocolFacade messageProtocolFacade;

    /**
     * Populates internal auth bean from the auth request from client. Also performs basic validation
     *
     * @param header        message header
     * @param clientAuthReq auth message from client
     * @return auth bean
     * @throws Exception
     */
    @Override
    public AuthRequestNormal populateAuthRequest(Header header, Message clientAuthReq) throws OMSException {
        AuthRequestNormal authReq = new AuthRequestNormalBean();

        AuthenticationNormalRequestBean authenticationNormalRequestBean = (AuthenticationNormalRequestBean) clientAuthReq;


        String loginName = authenticationNormalRequestBean.getLoginName();
        String password = authenticationNormalRequestBean.getPassword();
        int channelId = header.getChannelId();

        if (loginName == null || loginName.isEmpty()) {
            throw new OMSException("Invalid value for login name: " + loginName);
        }
        if (password == null || loginName.isEmpty()) {
            throw new OMSException("Invalid value for password: " + password);
        }
        if (channelId <= 0) {
            throw new OMSException("Invalid channel id: " + channelId);
        }

        authReq.setUserName(loginName);
        authReq.setPassword(password);

        authReq.setEncriptionType(authenticationNormalRequestBean.getEncryptionType());
        authReq.setMasterAccountNumber(authenticationNormalRequestBean.getMastAccNum());
        authReq.setInstitutionCode(authenticationNormalRequestBean.getInstId());
        authReq.setBrokerCode(authenticationNormalRequestBean.getBrkCode());
        authReq.setRequestGeneratedTime(authenticationNormalRequestBean.getRequestGeneratedTime());

        authReq.setClientChannel(channelId);
        authReq.setClientVersion(header.getClientVer());
        authReq.setSessionId(header.getMsgSessionId());
        authReq.setLanguageCode(header.getMsgLangID());

        return authReq;
    }


    /**
     * Creates login reply
     *
     * @param authReply authentication response
     * @return protocol specific authentication response
     */
    @Override
    public Message populateAuthResponse(Header header, AuthResponseNormal authReply) {
        AuthenticationNormalResponseBean authResponse = getAuthenticationNormalResponseBean();

        header.setLoggedInUserId(authReply.getUserId());
        header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);

        authResponse.setUserId(authReply.getUserId());
        authResponse.setLoginAlias(authReply.getLoginAlias());
        authResponse.setAuthStatus(authReply.getAuthenticationStatus());

        String rejectReason = authReply.getRejectReason();
        if (rejectReason != null) {
            authResponse.setRejectReason(rejectReason);
        }

        authResponse.setCustomerName(authReply.getCustomerName());
        authResponse.setLoginExpDate(DateFormatterUtil.formatDateToString(
                authReply.getLoginExpiryDate(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        authResponse.setLastLoginTime(DateFormatterUtil.formatDateToString(
                authReply.getLastLoginDateTime(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        authResponse.setFailedAttemptCount(authReply.getNoOfFailedAttempts());
        authResponse.setInstCode(authReply.getInstitutionCode()); //this is institution code (common for both front-office & back-office)


        return authResponse;
    }

    /**
     * created for testing
     *
     * @return
     */
    public AuthenticationNormalResponseBean getAuthenticationNormalResponseBean() {
        return new AuthenticationNormalResponseBean();
    }

    public List getArrayList() {
        return new ArrayList<>();
    }




    /**
     * created for testing
     *
     * @return
     */
    public TradingAccountBean getTradingAccountBean() {
        return new TradingAccountBean();
    }



    @Override
    public Message populateLogoutResponse(Header header, boolean status) {
        LogoutResponseBean logoutResponseBean = new LogoutResponseBean();
        logoutResponseBean.setLogoutStatus(String.valueOf(status));
        return logoutResponseBean;
    }


    public TrsConnectorFactory getTrsConnectorFactoryInstance() {
        return TrsConnectorFactory.getInstance();
    }

    private Long getVersionAsNumber(String version) {
        StringTokenizer st = getStringTokenizer(version, ".");
        StringBuilder sb = new StringBuilder();
        int temp;
        while (st.hasMoreTokens()) {
            temp = Integer.parseInt(st.nextToken());
            sb.append(String.format(String.format("%03d", temp)));
        }
        return Long.parseLong(sb.toString());

    }

    public StringTokenizer getStringTokenizer(String version, String s) {
        return new StringTokenizer(version, s);
    }



    public MessageProtocolFacade getMessageProtocolFacade() {
        return messageProtocolFacade;
    }

    public void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade) {
        this.messageProtocolFacade = messageProtocolFacade;
    }
}
