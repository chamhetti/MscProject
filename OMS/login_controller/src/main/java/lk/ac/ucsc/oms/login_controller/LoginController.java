package lk.ac.ucsc.oms.login_controller;


import lk.ac.ucsc.oms.common_utility.api.enums.*;


import lk.ac.ucsc.oms.customer.api.beans.login.LoginReply;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReplyStatus;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginReplyBean;

import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemConfigurationFactory;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import lk.ac.ucsc.oms.trs_connector.api.LoginControllerInterface;

import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.AuthResponseNormal;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request.AuthRequestNormal;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.reply.*;


import lk.ac.ucsc.oms.login_controller.helper.customer.CustomerOperationsHelper;

import lk.ac.ucsc.oms.login_controller.helper.util.ModuleDINames;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class LoginController implements LoginControllerInterface {

    private static Logger logger = LogManager.getLogger(LoginController.class);
    private static ApplicationContext ctx;

    public LoginController(String configFile) {
        synchronized (this) {
            ctx = new ClassPathXmlApplicationContext(configFile);
        }
    }
   
    public AuthResponseNormal doLogin(AuthRequestNormal authRequestNormal) {
        logger.info("Authenticating a customer -{}", authRequestNormal);
        AuthResponseNormal loginReply = getAuthResponseNormalBean();
        //check the validity of the request
        if (authRequestNormal == null) {
            loginReply.setAuthenticationStatus(LoginReplyStatus.LOGIN_FAILED.getCode());
            logger.error("Error authenticating: request is null");
            String errorMessage = "Error authenticating: request is null";
            loginReply.setRejectReason(errorMessage);
            return loginReply;
        }
        CustomerOperationsHelper customerOperationsHelper;
        try {
            customerOperationsHelper = getCustomerOperationsHelper(ctx, authRequestNormal.getUserName());
        } catch (Exception e) {
            logger.info("Unable to find a customer number for the given login.");
            loginReply.setAuthenticationStatus(LoginReplyStatus.LOGIN_FAILED.getCode());
            String errorMessage = "Invalid username or password."; // generic default error message to avoid exposing system details
            loginReply.setRejectReason(errorMessage);
            return loginReply;
        }

        LoginReply loginResult = getLoginReplyBean();  //default status is LOGIN_FAILED
        try {
            //Authenticating the customer
            loginResult = customerOperationsHelper.login(authRequestNormal.getPassword(), ClientChannel.getEnum(authRequestNormal.getClientChannel()),
                    authRequestNormal.getClientVersion(), authRequestNormal.getMasterAccountNumber(), authRequestNormal.getInstitutionCode());
            logger.info("Login Result >>" + loginResult);
            if (loginResult.getReplyStatus() == LoginReplyStatus.LOGIN_SUCCESS
                    || loginResult.getReplyStatus() == LoginReplyStatus.IS_FIRST_TIME
                    || loginResult.getReplyStatus() == LoginReplyStatus.SMS_OPT_REQUIRED) {
                return processLoginSuccess(authRequestNormal, customerOperationsHelper, loginResult);
            } else {
                logger.info("Login Failed for the customer -{}. Login status -{}", authRequestNormal.getUserName(), loginResult.getReplyStatus().toString());
            }

        } catch (Exception e) {
            logger.error("Login error", e);
        }

        // by now login has failed
        logger.info("login failed hence send the reply to customer");
        //set the authenticating status (failed / password-expired)
        loginReply.setAuthenticationStatus(loginResult.getReplyStatus().getCode());
        //get Error message for login failure
        String errorMessage = "Error authenticating: " + loginResult.getRejectReason();
        loginReply.setRejectReason(errorMessage);
        return loginReply;
    }

    public LoginReply getLoginReplyBean() {
        return new LoginReplyBean();
    }

    public CustomerOperationsHelper getCustomerOperationsHelper(ApplicationContext ctx, String userName) throws CustomerException {
        return new CustomerOperationsHelper(ctx, userName);
    }

    public AuthResponseNormal getAuthResponseNormalBean() {
        return new AuthResponseNormalBean();
    }

    private AuthResponseNormal processLoginSuccess(AuthRequestNormal authRequestNormal, CustomerOperationsHelper customerOperationsHelper, LoginReply loginResult) {
        AuthResponseNormal loginReply = getAuthResponseNormalBean();
        // this status can be login-success, first-time-login or pin-validation-required
        loginReply.setAuthenticationStatus(loginResult.getReplyStatus().getCode());
        String customerNumber = customerOperationsHelper.getCustomerNumber();
        loginReply.setUserId(customerNumber);
        //set the email address of the customers
        loginReply.setEmail(customerOperationsHelper.getCustomer().getPersonalProfile().getEmail());
        Date lastLoginDate = customerOperationsHelper.getCustomer().getLoginProfile().getLastLoginDate();
        if (lastLoginDate != null) {
            loginReply.setLastLoginDateTime(lastLoginDate);
        }
        //set the login alias of the customer. that is the user name customer created that can be used instead of customer number
        loginReply.setLoginAlias(customerOperationsHelper.getLoginAlias());
        //setting the login expiry date for the customer
        Date expDate = customerOperationsHelper.getCustomer().getLoginProfile().getPasswordExpDate();
        if (expDate != null) {
            loginReply.setLoginExpiryDate(expDate);
        }
        //set the number of unsuccessful login attempt made by customer
        loginReply.setNoOfFailedAttempts(customerOperationsHelper.getCustomer().getLoginProfile().getFailedAttemptCount());
        //set customer name
        loginReply.setCustomerName(customerOperationsHelper.getName());
        //get portfolio headers. This set the customer all portfolio and trading account information
        try {
            loginReply.setPortfolio(customerOperationsHelper.getPortfolioHeaders());
        } catch (Exception e) {
            logger.error("Error setting portfolio in login reply: {}", e);
        }
        return loginReply;
    }

    @Override
    public boolean logOut(String userName) {
        try {
            CustomerOperationsHelper customerOperationsHelper = getCustomerOperationsHelper(ctx, userName);
            return customerOperationsHelper.logOutUser();
        } catch (Exception e) {
            return false;
        }
    }


}
