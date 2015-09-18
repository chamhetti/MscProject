package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.LOGOUT_STATUS;

/**
 * @author hetti
 */
public class LogoutResponseBean implements Message {

    @SerializedName(LOGOUT_STATUS)
    private String logoutStatus = null;

    public String getLogoutStatus() {
        return logoutStatus;
    }

    public void setLogoutStatus(String logoutStatus) {
        this.logoutStatus = logoutStatus;
    }

}
