package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request.AuthRequestNormal;


public class AuthRequestNormalBean implements AuthRequestNormal {
    private String userName;
    private int encriptionType;
    private String requestGeneratedTime;
    private String password;
    private String sessionId;
    private int clientChannel;
    private String clientVersion;
    private String institutionCode;
    private String brokerCode;
    private String masterAccountNumber;
    private String languageCode;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int getEncriptionType() {
        return encriptionType;
    }

    @Override
    public void setEncriptionType(int encriptionType) {
        this.encriptionType = encriptionType;
    }

    @Override
    public String getRequestGeneratedTime() {
        return requestGeneratedTime;
    }

    @Override
    public void setRequestGeneratedTime(String requestGeneratedTime) {
        this.requestGeneratedTime = requestGeneratedTime;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public int getClientChannel() {
        return clientChannel;
    }

    @Override
    public void setClientChannel(int clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public String getClientVersion() {
        return clientVersion;
    }

    @Override
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }

    @Override
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    @Override
    public String getBrokerCode() {
        return brokerCode;
    }

    @Override
    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    @Override
    public String getMasterAccountNumber() {
        return masterAccountNumber;
    }

    @Override
    public void setMasterAccountNumber(String masterAccountNumber) {
        this.masterAccountNumber = masterAccountNumber;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return "AuthRequestNormalBean{" +
                "userName='" + userName + '\'' +
                ", encriptionType='" + encriptionType + '\'' +
                ", requestGeneratedTime='" + requestGeneratedTime + '\'' +
                ", password='" + password + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", clientChannel=" + clientChannel +
                ", clientVersion='" + clientVersion + '\'' +
                ", institutionCode='" + institutionCode + '\'' +
                ", brokerCode='" + brokerCode + '\'' +
                ", masterAccountNumber='" + masterAccountNumber + '\'' +
                '}';
    }
}
