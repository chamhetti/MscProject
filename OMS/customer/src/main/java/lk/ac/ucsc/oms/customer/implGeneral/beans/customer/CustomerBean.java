package lk.ac.ucsc.oms.customer.implGeneral.beans.customer;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.AccountType;
import lk.ac.ucsc.oms.customer.api.beans.CustomerType;
import lk.ac.ucsc.oms.customer.api.beans.OnlineRegistered;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.customer.LoginProfile;
import lk.ac.ucsc.oms.customer.api.beans.customer.PersonalProfile;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;

@Indexed
public class CustomerBean extends CacheObject implements Customer {
    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;
    @Id
    @Field
    private long customerId;
    @Field
    private String customerNumber;
    @Field
    private RecordStatus status;
    @Field
    private String institutionCode;
    @Field
    private String parentCusNumber;
    @Field
    private AccountType accountType;
    @Field
    private String preparedLanguage;
    @IndexedEmbedded
    private PersonalProfileBean personalProfile = new PersonalProfileBean();
    @IndexedEmbedded
    private LoginProfileBean loginProfile = new LoginProfileBean();

    @Field
    private String currency;

    public CustomerBean() {
    }


    public CustomerBean(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public LoginProfile getLoginProfile() {
        return loginProfile;
    }

    @Override
    public void setLoginProfile(LoginProfile loginProfile) {
        this.loginProfile = (LoginProfileBean) loginProfile;
    }

    @Override
    public PersonalProfile getPersonalProfile() {
        return personalProfile;
    }

    @Override
    public void setPersonalProfile(PersonalProfile personalProfile) {
        this.personalProfile = (PersonalProfileBean) personalProfile;
    }

    @Override
    public String getPreparedLanguage() {
        return preparedLanguage;
    }

    @Override
    public void setPreparedLanguage(String preparedLanguage) {
        this.preparedLanguage = preparedLanguage;
    }

    @Override
    public String getParentCusNumber() {
        return parentCusNumber;
    }

    @Override
    public void setParentCusNumber(String parentCusNum) {
        this.parentCusNumber = parentCusNum;
    }

    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }

    @Override
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    @Override
    public RecordStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    @Override
    public String getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
