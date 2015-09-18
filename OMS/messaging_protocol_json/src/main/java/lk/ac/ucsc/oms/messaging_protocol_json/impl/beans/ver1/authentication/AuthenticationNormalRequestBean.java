package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;

/**
 * @author hetti
 */
public class AuthenticationNormalRequestBean implements Message {

    @SerializedName(LOGIN_NAME)
    private String loginName = null;

    @SerializedName(REQUEST_GENERATED_TIME)
    private String requestGeneratedTime = null;

    @SerializedName(PASSWORD)
    private String password = null;

    @SerializedName(INSTITUTE_ID)
    private String instId = null;

    @SerializedName(BROKER_CODE)
    private String brkCode = null;

    @SerializedName(MASTER_ACCOUNT_NUMBER)
    private String mastAccNum;

    @SerializedName(ENCRYPTION_TYPE)
    private int encryptionType = -1;

    @SerializedName(ENCRYPTED_AUTH_REQ)
    private String encryptionCode;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(int encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getRequestGeneratedTime() {
        return requestGeneratedTime;
    }

    public void setRequestGeneratedTime(String requestGeneratedTime) {
        this.requestGeneratedTime = requestGeneratedTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getBrkCode() {
        return brkCode;
    }

    public void setBrkCode(String brkCode) {
        this.brkCode = brkCode;
    }

    public String getMastAccNum() {
        return mastAccNum;
    }

    public void setMastAccNum(String mastAccNum) {
        this.mastAccNum = mastAccNum;
    }

    public String getEncryptionCode() {
        return encryptionCode;
    }

    public void setEncryptionCode(String encryptionCode) {
        this.encryptionCode = encryptionCode;
    }
}
