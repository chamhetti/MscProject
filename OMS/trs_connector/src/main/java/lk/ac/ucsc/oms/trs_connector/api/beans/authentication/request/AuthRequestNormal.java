package lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request;

/**
 * User: Hetti
 * Date: 7/16/13
 * Time: 2:51 PM
 */
public interface AuthRequestNormal {

    String getUserName();

    void setUserName(String userName);

    int getEncriptionType();

    void setEncriptionType(int encriptionType);

    String getRequestGeneratedTime();

    void setRequestGeneratedTime(String requestGeneratedTime);

    String getPassword();

    void setPassword(String password);

    String getSessionId();

    void setSessionId(String sessionId);

    int getClientChannel();

    void setClientChannel(int clientChannel);

    String getClientVersion();

    void setClientVersion(String clientVersion);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

    String getBrokerCode();

    void setBrokerCode(String brokerCode);

    String getMasterAccountNumber();

    void setMasterAccountNumber(String masterAccountNumber);

    String getLanguageCode();

    void setLanguageCode(String languageCode);
}
