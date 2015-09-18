package lk.ac.ucsc.oms.customer.implGeneral.beans.login;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.Date;


@Indexed
public class LoginHistoryBean extends CacheObject {
    @Id
    @Field
    private long id;
    @Field
    private String customerNumber;
    @Field
    private String loginName;
    @Field
    private ClientChannel channel;
    @Field
    private String version;
    @Field
    private Date loginTime;
    @Field
    private int loginStatus;

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ClientChannel getChannel() {
        return channel;
    }

    public void setChannel(ClientChannel channel) {
        this.channel = channel;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLoginStatus() {
        return loginStatus;
    }


    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public String toString() {
        return "LoginHistoryBean{" +
                "id=" + id +
                ", customerNumber='" + customerNumber + '\'' +
                ", loginName='" + loginName + '\'' +
                ", channel='" + channel + '\'' +
                ", version='" + version + '\'' +
                ", loginTime=" + loginTime +
                ", loginStatus=" + loginStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoginHistoryBean)) {
            return false;
        }

        LoginHistoryBean that = (LoginHistoryBean) o;

        if (id != that.id) {
            return false;
        }
        if (loginStatus != that.loginStatus) {
            return false;
        }
        if (channel != that.channel) {
            return false;
        }
        if (customerNumber != null ? !customerNumber.equals(that.customerNumber) : that.customerNumber != null) {
            return false;
        }
        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null) {
            return false;
        }
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (customerNumber != null ? customerNumber.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (loginName != null ? loginName.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (channel != null ? channel.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (version != null ? version.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (loginTime != null ? loginTime.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + loginStatus;
        return result;
    }
}
