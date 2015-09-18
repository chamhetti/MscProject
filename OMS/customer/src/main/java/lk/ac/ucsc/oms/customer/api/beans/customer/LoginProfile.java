package lk.ac.ucsc.oms.customer.api.beans.customer;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.beans.AuthenticationType;
import lk.ac.ucsc.oms.customer.api.beans.LoginProfileStatus;

import java.util.Date;
import java.util.List;

public interface LoginProfile {

    LoginProfileStatus getStatus();

    void setStatus(LoginProfileStatus status);

    String getTradingPassword();

    void setTradingPassword(String tradingPassword);

    String getPassword();

    void setPassword(String password);

    String getLoginAlias();

    void setLoginAlias(String loginAlias);

    String getLoginName();

    void setLoginName(String loginName);

    long getCustomerId();

    void setCustomerId(long customerId);

    int getFailedAttemptCount();

    void setFailedAttemptCount(int failedAttemptCount);

    Date getPasswordExpDate();

    void setPasswordExpDate(Date passwordExpDate);

    PropertyEnable getFirstTime();

    void setFirstTime(PropertyEnable firstTime);

    Date getLastLoginDate();

    void setLastLoginDate(Date lastLoginDate);

}