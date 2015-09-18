package lk.ac.ucsc.clientConnector.session_manager.api.beans;

import java.util.Date;


public interface UserSession {

    String getSessionID();

    void setSessionID(String sessionID);


    String getTrsID();


    void setTrsID(String trsID);


    String getLoginAlias();


    void setLoginAlias(String userID);


    int getChannelID();


    void setChannelID(int channelID);


    String getUserID();


    void setUserID(String userID);


    String getAccountNumbers();


    void setAccountNumbers(String accountNumbers);

    Date getStartTime();

    void setStartTime(Date startTime);
}
