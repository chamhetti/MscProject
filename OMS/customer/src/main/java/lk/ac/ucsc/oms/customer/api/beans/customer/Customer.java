package lk.ac.ucsc.oms.customer.api.beans.customer;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.AccountType;
import lk.ac.ucsc.oms.customer.api.beans.CustomerType;
import lk.ac.ucsc.oms.customer.api.beans.OnlineRegistered;


public interface Customer {

    LoginProfile getLoginProfile();

    void setLoginProfile(LoginProfile loginProfile);

    PersonalProfile getPersonalProfile();

    void setPersonalProfile(PersonalProfile personalProfile);

    long getCustomerId();

    void setCustomerId(long customerId);

    String getPreparedLanguage();

    void setPreparedLanguage(String preparedLanguage);

    String getParentCusNumber();

    void setParentCusNumber(String parentCusNum);

    String getInstitutionCode();

    void setInstitutionCode(String institution);

    RecordStatus getStatus();

    void setStatus(RecordStatus status);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    void setAccountType(AccountType accountType);

    AccountType getAccountType();

    String getCurrency();

    void setCurrency(String currency);

}
