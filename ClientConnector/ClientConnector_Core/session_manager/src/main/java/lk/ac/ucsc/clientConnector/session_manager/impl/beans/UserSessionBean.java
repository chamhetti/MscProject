package lk.ac.ucsc.clientConnector.session_manager.impl.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.Date;

/**
 * {@inheritDoc}
 */
@Indexed
public class UserSessionBean extends CacheObject implements UserSession {
    private static final int HASHCODE_CONST = 31;

    @Id
    @Field
    private String sessionID;
    @Field
    private String loginAlias;
    @Field
    private String userID;
    @Field
    private String trsID;
    @Field
    private int channelID;
    @Field
    private String accountNumbers;

    private Date startTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSessionID() {
        return sessionID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTrsID() {
        return trsID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTrsID(String trsID) {
        this.trsID = trsID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLoginAlias() {
        return loginAlias;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLoginAlias(String appServerID) {
        this.loginAlias = appServerID;
    }

    /**
     * @return the client channel
     */
    @Override
    public int getChannelID() {
        return channelID;
    }

    /**
     * @param channelID is the client channel
     */
    @Override
    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    /**
     * @return the user id
     */
    @Override
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID is the user id
     */
    @Override
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the account number list
     */
    @Override
    public String getAccountNumbers() {
        return accountNumbers;
    }

    /**
     * @param accountNumbers is the account numbers list
     */
    @Override
    public void setAccountNumbers(String accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSessionBean)) {
            return false;
        }

        UserSessionBean that = (UserSessionBean) o;

        return checkBeanEquality(that);

    }

    private boolean checkBeanEquality(UserSessionBean that) {
        if (channelID != that.channelID) {
            return false;
        }
        if (sessionID != null) {
            if (!sessionID.equals(that.sessionID)) {
                return false;
            }
        } else {
            if (that.sessionID != null) {
                return false;
            }
        }
        if (trsID != null) {
            if (!trsID.equals(that.trsID)) {
                return false;
            }
        } else {
            if (that.trsID != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        if (loginAlias != null) {
            result = loginAlias.hashCode();
        } else {
            result = 0;
        }
        if (sessionID != null) {
            result = HASHCODE_CONST * result + sessionID.hashCode();
        } else {
            result = HASHCODE_CONST * result;
        }
        if (trsID != null) {
            result = HASHCODE_CONST * result + trsID.hashCode();
        } else {
            result = HASHCODE_CONST * result;
        }
        result = HASHCODE_CONST * result + channelID;
        if (userID != null) {
            result = HASHCODE_CONST * result + userID.hashCode();
        } else {
            result = HASHCODE_CONST * result;
        }
        return result;
    }

    @Override
    public String toString() {
        return "UserSessionBean{" +
                "loginAlias='" + loginAlias + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", trsID='" + trsID + '\'' +
                ", channelID=" + channelID +
                ", userID=" + userID +
                '}';
    }
}
