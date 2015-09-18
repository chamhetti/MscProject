package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.AccountType;
import lk.ac.ucsc.oms.customer.api.beans.AuthenticationType;
import lk.ac.ucsc.oms.customer.api.beans.CustomerType;
import lk.ac.ucsc.oms.customer.api.beans.Gender;

import java.util.Date;

/**
 * User: Chathura
 * Rajeevan
 * Date: Jan 31, 2013
 */
public interface CustomerRecordTrs {

    long getCustomerId();

    void setCustomerId(long customerId);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    RecordStatus getStatus();

    void setStatus(RecordStatus status);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

    String getParentCusNumber();

    void setParentCusNumber(String parentCusNumber);

    CustomerType getCustomerType();

    void setCustomerType(CustomerType customerType);

    AccountType getAccountType();

    void setAccountType(AccountType accountType);

    String getPreparedLanguage();

    void setPreparedLanguage(String preparedLanguage);

    String getTitle();

    void setTitle(String title);

    String getFirstName();

    void setFirstName(String firstName);

    Gender getGender();

    void setGender(Gender gender);

    String getNationality();

    void setNationality(String nationality);

    String getOfficeTele();

    void setOfficeTele(String officeTele);

    String getMobile();

    void setMobile(String mobile);

    String getEmail();

    void setEmail(String email);

    String getLastName();

    void setLastName(String lastName);

    int getGccRegion();

    void setGccRegion(int gccRegion);

    int getProfessionalCategory();

    void setProfessionalCategory(int professionalCategory);

    String getLoginName();

    void setLoginName(String loginName);

    String getLoginAlias();

    void setLoginAlias(String loginAlias);

    String getPassword();

    void setPassword(String password);

    String getTradingPassword();

    void setTradingPassword(String tradingPassword);

    AuthenticationType getAuthType();

    void setAuthType(AuthenticationType authType);

    String getPriceUserName();

    void setPriceUserName(String priceUserName);

    String getPricePassword();

    void setPricePassword(String pricePassword);

    int getFailedAttemptCount();

    void setFailedAttemptCount(int failedAttemptCount);

    Date getPasswordExpDate();

    void setPasswordExpDate(Date passwordExpDate);

    PropertyEnable getFirstTime();

    void setFirstTime(PropertyEnable firstTime);

    String getPublicKey();

    void setPublicKey(String publicKey);

    String getSmsOpt();

    void setSmsOpt(String smsOpt);

    PropertyEnable getSmsOptEnable();

    void setSmsOptEnable(PropertyEnable smsOptEnable);

    Date getSmsOPTGenerateTime();

    void setSmsOPTGenerateTime(Date smsOPTGenerateTime);

    Date getLastLoginDate();

    void setLastLoginDate(Date lastLoginDate);

    double getTradingLimit();

    void setTradingLimit(double tradingLimit);

    String getCurrency();

    void setCurrency(String currency);

    double getPortfolioDiversifyPercentage();

    void setPortfolioDiversifyPercentage(double portfolioDiversifyPercentage);

    PropertyEnable getInternalMatchingAllow();

    void setInternalMatchingAllow(PropertyEnable internalMatchingAllow);

}
