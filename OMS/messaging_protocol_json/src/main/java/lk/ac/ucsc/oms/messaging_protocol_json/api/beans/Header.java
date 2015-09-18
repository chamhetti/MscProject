package lk.ac.ucsc.oms.messaging_protocol_json.api.beans;


public interface Header {

    String getProtocolVersion();

    void setProtocolVersion(String protocolVersion);

    int getMsgGroup();

    void setMsgGroup(int msgGroup);

    int getMsgType();

    void setMsgType(int msgReqType);

    int getChannelId();

    void setChannelId(int channelID);

    String getMsgSessionId();

    void setMsgSessionId(String msgSessionId);

    String getLoggedInUserId();

    void setLoggedInUserId(String loggedInUserId);

    int getRespStatus();

    void setRespStatus(int respStatus);

    String getRespReason();

    void setRespReason(String respReason);

    String getClientReqID();

    void setClientReqID(String clientReqID);

    String getMsgLangID();

    void setMsgLangID(String msgLangID);

    String getUniqueReqID();

    void setUniqueReqID(String uniqueReqID);

    String getClientIp();

    void setClientIp(String clientIp);

    String getClientVer();

    void setClientVer(String clientVer);

    int getErrorCode();

    void setErrorCode(int errorCode);
}
