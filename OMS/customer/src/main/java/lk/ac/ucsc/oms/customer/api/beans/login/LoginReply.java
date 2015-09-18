package lk.ac.ucsc.oms.customer.api.beans.login;

import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;


public interface LoginReply {

    LoginReplyStatus getReplyStatus();

    CustomerStatusMessages getRejectReason();

    boolean isNewlyLocked();

    void setNewlyLocked(boolean newlyLocked);

    String getSmsPin();

    void setSmsPin(String smsPin);
}
