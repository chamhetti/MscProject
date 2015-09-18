package lk.ac.ucsc.oms.customer.implGeneral.beans.customer;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.beans.AuthenticationType;
import lk.ac.ucsc.oms.customer.api.beans.LoginProfileStatus;
import lk.ac.ucsc.oms.customer.api.beans.customer.LoginProfile;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LoginProfileBean extends CacheObject implements LoginProfile {

    @Id
    @Field
    private long customerId;
    @Field
    private String loginName;
    @Field
    private String loginAlias;
    @Field
    private String password;
    @Field
    private String tradingPassword;
    @Field
    private LoginProfileStatus status;
    @Field
    private int failedAttemptCount;
    @Field
    private Date passwordExpDate;
    @Field
    private PropertyEnable firstTime;
    @Field
    private Date lastLoginDate;


    @Override
    public LoginProfileStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(LoginProfileStatus status) {
        this.status = status;
    }

    @Override
    public String getTradingPassword() {
        return tradingPassword;
    }

    @Override
    public void setTradingPassword(String tradingPassword) {
        this.tradingPassword = tradingPassword;
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
    public String getLoginAlias() {
        return loginAlias;
    }

    @Override
    public void setLoginAlias(String loginAlias) {
        this.loginAlias = loginAlias;
    }

    @Override
    public String getLoginName() {
        return loginName;
    }

    @Override
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public long getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int getFailedAttemptCount() {
        return failedAttemptCount;
    }

    @Override
    public void setFailedAttemptCount(int failedAttemptCount) {
        this.failedAttemptCount = failedAttemptCount;
    }

    @Override
    public Date getPasswordExpDate() {
        return passwordExpDate;
    }

    @Override
    public void setPasswordExpDate(Date passwordExpDate) {
        this.passwordExpDate = passwordExpDate;
    }

    @Override
    public PropertyEnable getFirstTime() {
        return firstTime;
    }

    @Override
    public void setFirstTime(PropertyEnable firstTime) {
        this.firstTime = firstTime;
    }

    @Override
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    @Override
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

}
