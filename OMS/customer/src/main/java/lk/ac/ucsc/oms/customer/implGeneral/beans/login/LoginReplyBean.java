package lk.ac.ucsc.oms.customer.implGeneral.beans.login;

import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReply;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReplyStatus;


public class LoginReplyBean implements LoginReply{
    private LoginReplyStatus replyStatus =LoginReplyStatus.LOGIN_FAILED;
    private CustomerStatusMessages rejectReason;
    private String preferredLanguage;
    private boolean newlyLocked = false;
    private String smsPin;

    @Override
    public LoginReplyStatus getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(LoginReplyStatus replyStatus) {
        this.replyStatus = replyStatus;
    }

    @Override
    public CustomerStatusMessages getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(CustomerStatusMessages reason) {
        this.rejectReason = reason;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    @Override
    public boolean isNewlyLocked() {
        return newlyLocked;
    }

    @Override
    public void setNewlyLocked(boolean newlyLocked) {
        this.newlyLocked = newlyLocked;
    }

    @Override
    public String getSmsPin() {
        return smsPin;
    }

    @Override
    public void setSmsPin(String smsPin) {
        this.smsPin = smsPin;
    }

    @Override
    public String toString() {
        return "LoginReplyBean{" +
                "replyStatus=" + replyStatus +
                ", reason='" + rejectReason + '\'' +
                '}';
    }
}
