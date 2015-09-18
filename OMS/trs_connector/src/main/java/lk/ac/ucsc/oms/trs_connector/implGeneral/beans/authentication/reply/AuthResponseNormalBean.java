package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.reply;

import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.AuthResponseNormal;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.PortfolioHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AuthResponseNormalBean implements AuthResponseNormal {
    private String userId;
    private int authenticationStatus = -1;
    private String rejectReason;
    private int noOfFailedAttempts;
    private Date loginExpiryDate;
    private Date lastLoginDateTime;
    private String institutionCode;
    private String customerName;
    private String loginAlias;
    private String email;


    private List<PortfolioHeader> portfolio = new ArrayList<PortfolioHeader>();

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    @Override
    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    @Override
    public String getRejectReason() {
        return rejectReason;
    }

    @Override
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Override
    public int getNoOfFailedAttempts() {
        return noOfFailedAttempts;
    }

    @Override
    public void setNoOfFailedAttempts(int noOfFailedAttempts) {
        this.noOfFailedAttempts = noOfFailedAttempts;
    }

    @Override
    public Date getLoginExpiryDate() {
        return loginExpiryDate;
    }

    @Override
    public void setLoginExpiryDate(Date loginExpiryDate) {
        this.loginExpiryDate = loginExpiryDate;
    }

    @Override
    public Date getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    @Override
    public void setLastLoginDateTime(Date lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
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
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public List<PortfolioHeader> getPortfolio() {
        return portfolio;
    }

    @Override
    public void setPortfolio(List<PortfolioHeader> portfolio) {
        this.portfolio = portfolio;
    }


}
