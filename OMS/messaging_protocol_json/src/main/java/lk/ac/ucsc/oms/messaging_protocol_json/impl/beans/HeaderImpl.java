package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;

/**
 * @author hetti
 */
public class HeaderImpl implements Header {

    @SerializedName(PROTOCOL_VERSION)
    private String protocolVersion = "DFN_JSON_1.0";


    @SerializedName(MESSAGE_GROUP)
    private int msgGroup = -1;

    @SerializedName(MESSAGE_TYPE)
    private int msgType = -1;

    @SerializedName(CHANNEL_ID)
    private int channelId = -1;


    @SerializedName(SESSION_ID)
    private String msgSessionId = null;

    @SerializedName(USER_ID)
    private String loggedInUserId = null;


    @SerializedName(RESPONSE_STATUS)
    private int respStatus;

    @SerializedName(RESPONSE_REASON)
    private String respReason = null;

    @SerializedName(ERROR_CODE)
    private int errorCode;


    @SerializedName(CLIENT_REQUEST_ID)
    private String clientReqID = null;   // this is to be used instead of sequence number in body

    @SerializedName(LANGUAGE_ID)
    private String msgLangID = null;


    @SerializedName(UNIQUE_REQUEST_ID)
    private String uniqueReqID = null;

    @SerializedName(CLIENT_IP)
    private String clientIp = null;


    @SerializedName(CLIENT_VERSION)
    private String clientVer = null;


    @Override
    public String getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public int getMsgGroup() {
        return msgGroup;
    }

    @Override
    public void setMsgGroup(int msgGroup) {
        this.msgGroup = msgGroup;
    }

    @Override
    public int getMsgType() {
        return msgType;
    }

    @Override
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    @Override
    public int getChannelId() {
        return channelId;
    }

    @Override
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Override
    public String getMsgSessionId() {
        return msgSessionId;
    }

    @Override
    public void setMsgSessionId(String msgSessionId) {
        this.msgSessionId = msgSessionId;
    }

    @Override
    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    @Override
    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    @Override
    public int getRespStatus() {
        return respStatus;
    }

    @Override
    public void setRespStatus(int respStatus) {
        this.respStatus = respStatus;
    }

    @Override
    public String getRespReason() {
        return respReason;
    }

    @Override
    public void setRespReason(String respReason) {
        this.respReason = respReason;
    }

    @Override
    public String getClientReqID() {
        return clientReqID;
    }

    @Override
    public void setClientReqID(String clientReqID) {
        this.clientReqID = clientReqID;
    }

    @Override
    public String getMsgLangID() {
        return msgLangID;
    }

    @Override
    public void setMsgLangID(String msgLangID) {
        this.msgLangID = msgLangID;
    }

    @Override
    public String getUniqueReqID() {
        return uniqueReqID;
    }

    @Override
    public void setUniqueReqID(String uniqueReqID) {
        this.uniqueReqID = uniqueReqID;
    }

    @Override
    public String getClientIp() {
        return clientIp;
    }

    @Override
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String getClientVer() {
        return clientVer;
    }

    @Override
    public void setClientVer(String clientVer) {
        this.clientVer = clientVer;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
