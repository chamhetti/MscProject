package lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply;

import java.util.Date;
import java.util.List;

/**
 * User: Hetti
 * Date: 7/16/13
 * Time: 5:36 PM
 */
public interface AuthResponseNormal {
    String getUserId();

    void setUserId(String userId);

    int getAuthenticationStatus();

    void setAuthenticationStatus(int authenticationStatus);

    String getRejectReason();

    void setRejectReason(String rejectReason);

    int getNoOfFailedAttempts();

    void setNoOfFailedAttempts(int noOfFailedAttempts);

    Date getLoginExpiryDate();

    void setLoginExpiryDate(Date loginExpiryDate);

    Date getLastLoginDateTime();

    void setLastLoginDateTime(Date lastLoginDateTime);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

    String getCustomerName();

    void setCustomerName(String customerName);

    String getLoginAlias();

    void setLoginAlias(String loginAlias);

    String getEmail();

    void setEmail(String email);


    List<PortfolioHeader> getPortfolio();

    void setPortfolio(List<PortfolioHeader> portfolio);

}
