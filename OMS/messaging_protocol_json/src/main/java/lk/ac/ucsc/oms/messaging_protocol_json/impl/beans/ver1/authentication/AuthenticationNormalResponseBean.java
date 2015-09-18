package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;

/**
 * @author hetti
 */
public class AuthenticationNormalResponseBean implements Message {

    @SerializedName(USER_ID)
    private String userId = null;       // customer number or dealer Id
    @SerializedName(AUTHENTICATION_STATUS)
    private int authStatus = -1;
    @SerializedName(REJECT_REASON)
    private String rejectReason = null;
    @SerializedName(FAILED_ATTEMPTS)
    private int failedAttemptCount = 0;
    @SerializedName(LOGIN_EXPIRY_DATE)
    private String loginExpDate = null;
    @SerializedName(LAST_LOGIN_TIME)
    private String lastLoginTime = null;
    @SerializedName(INSTITUTE_ID)
    private String instCode = null;
    @SerializedName(CUSTOMER_NAME)
    private String customerName = null;
    @SerializedName(LOGIN_ALIAS)
    private String loginAlias;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getFailedAttemptCount() {
        return failedAttemptCount;
    }

    public void setFailedAttemptCount(int failedAttemptCount) {
        this.failedAttemptCount = failedAttemptCount;
    }

    public String getLoginExpDate() {
        return loginExpDate;
    }

    public void setLoginExpDate(String loginExpDate) {
        this.loginExpDate = loginExpDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getLoginAlias() {
        return loginAlias;
    }

    public void setLoginAlias(String loginAlias) {
        this.loginAlias = loginAlias;
    }



}
