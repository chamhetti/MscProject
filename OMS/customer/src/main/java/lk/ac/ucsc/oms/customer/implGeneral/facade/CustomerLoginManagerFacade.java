package lk.ac.ucsc.oms.customer.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.CustomerLoginManager;
import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.LoginProfileStatus;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.customer.LoginProfile;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReply;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReplyStatus;
import lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginHistoryBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginReplyBean;
import lk.ac.ucsc.oms.customer.implGeneral.facade.cache.LoginHistoryCacheFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class CustomerLoginManagerFacade implements CustomerLoginManager {
    private static Logger logger = LogManager.getLogger(CustomerLoginManagerFacade.class);

    private static long count = 0;

    private CustomerManager customerManagerFacade;
    private int allowedFailedAttempt;
    private LoginHistoryCacheFacade loginHistoryCacheFacade;


    @Override
    public void initialize() {
        loginHistoryCacheFacade.initialize();
    }


    public void setCustomerManagerFacade(CustomerManager customerManagerFacade) {
        this.customerManagerFacade = customerManagerFacade;
    }

    public void setAllowedFailedAttempt(int allowedFailedAttempt) {
        this.allowedFailedAttempt = allowedFailedAttempt;
    }


    public void setLoginHistoryCacheFacade(LoginHistoryCacheFacade loginHistoryCacheFacade) {
        this.loginHistoryCacheFacade = loginHistoryCacheFacade;
    }

    @Override
    public LoginReply loginCustomer(String loginName, String password, ClientChannel channel,
                                    String version, String masterAccNumber, String institutionCode) {
        logger.info("Login the customer with loginName -{} password -{} channel -{} version -{}");
        LoginReplyBean reply = new LoginReplyBean();

        //validating loginName, password, channel, version for null and empty
        if (loginName == null || "".equals(loginName) || password == null || "".equals(password) ||
                channel == null || version == null || "".equals(version)) {
            logger.info("Login failed due to insufficient login information");
            reply.setReplyStatus(LoginReplyStatus.LOGIN_FAILED);
            reply.setRejectReason(CustomerStatusMessages.INVALID_INPUT_PARAMETER);
            return reply;
        }
        logger.debug("Input parameter validation finished");
        String customerNumber = "";
        try {
            Customer customer = customerManagerFacade.getCustomerByCustomerNumber(loginName);
            logger.debug("loginCustomer : customer : ", customer);
            if (customer == null) {
                logger.warn("Not able to find a customer number for the given alias. Check whether customer number appear at the" +
                        "alias column for the customer without separate login alias");
                reply.setReplyStatus(LoginReplyStatus.LOGIN_FAILED);
                reply.setRejectReason(CustomerStatusMessages.INVALID_LOGIN_NAME);
                return reply;
            }


            customerNumber = customer.getCustomerNumber();
            LoginProfile loginProfile = customer.getLoginProfile();
            String preferredLanguage = customer.getPreparedLanguage();
            reply.setPreferredLanguage(preferredLanguage);

            logger.debug("Check the expiry date of the password");
            if (loginProfile.getPasswordExpDate() != null && loginProfile.getPasswordExpDate().before(new Date())) {
                logger.info("Password has expired");
                reply.setReplyStatus(LoginReplyStatus.PASSWORD_EXPIRED);
                reply.setRejectReason(CustomerStatusMessages.PASSWORD_EXPIRED);
                return reply;
            }
            logger.debug("check the status of the login profile");
            if (loginProfile.getStatus() != LoginProfileStatus.ACTIVE) {
                logger.info("customer account has " + loginProfile.getStatus());
                reply.setReplyStatus(LoginReplyStatus.LOGIN_FAILED);
                if (loginProfile.getFailedAttemptCount() >= allowedFailedAttempt) {
                    reply.setRejectReason(CustomerStatusMessages.MAXIMUM_ATTEMPTS_EXCEEDED);
                } else {
                    reply.setRejectReason(CustomerStatusMessages.ACCOUNT_INACTIVE);
                }
                return reply;
            }
            logger.debug("validate the password");
            if (!validatePassword(loginProfile.getPassword(), password)) {
                logger.info("password does not match");
                loginProfile.setFailedAttemptCount(loginProfile.getFailedAttemptCount() + 1);
                if (loginProfile.getFailedAttemptCount() >= allowedFailedAttempt) {
                    logger.info("Maximum failed count exceed hence lock the account");
                    loginProfile.setFailedAttemptCount(allowedFailedAttempt);
                    loginProfile.setStatus(LoginProfileStatus.LOCKED);
                    reply.setNewlyLocked(true);
                    reply.setRejectReason(CustomerStatusMessages.MAXIMUM_ATTEMPTS_EXCEEDED);
                } else {
                    reply.setRejectReason(CustomerStatusMessages.INVALID_PASSWORD);
                }
                customer.setLoginProfile(loginProfile);
                customerManagerFacade.updateCustomer(customer);
                logger.debug("loginCustomer : customer : ", customer);
                reply.setReplyStatus(LoginReplyStatus.LOGIN_FAILED);
                return reply;
            } else {
                logger.info("Login success");
                loginProfile.setFailedAttemptCount(0);
                loginProfile.setLastLoginDate(new Date());


                reply.setReplyStatus(LoginReplyStatus.LOGIN_SUCCESS);

                customer.setLoginProfile(loginProfile);
                customerManagerFacade.updateCustomer(customer);
            }
        } catch (Exception e) {
            logger.warn("problem in loading customer login profile", e);
            reply.setReplyStatus(LoginReplyStatus.LOGIN_FAILED);
            reply.setRejectReason(CustomerStatusMessages.CUSTOMER_NOT_FOUND);
            return reply;
        } finally {
            logger.info("Store the login request with the status to DB");
            LoginHistoryBean loginHistoryBean = new LoginHistoryBean();
            loginHistoryBean.setChannel(channel);
            loginHistoryBean.setCustomerNumber(customerNumber);
            loginHistoryBean.setLoginName(loginName);
            loginHistoryBean.setVersion(version);
            loginHistoryBean.setLoginTime(new Date());
            loginHistoryBean.setLoginStatus(reply.getReplyStatus().getCode());
            loginHistoryBean.setId(System.currentTimeMillis() + count++);
            loginHistoryBean.setPrimaryKeyObject(loginHistoryBean.getId());
            logger.debug("loginCustomer : loginHistoryBean : ", loginHistoryBean);
            try {
                loginHistoryCacheFacade.putLoginHistory(loginHistoryBean);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return reply;
    }


    @Override
    public CustomerStatusMessages isValidTradingPassword(String customerNumber, String tradingPassword) {
        logger.info("validate the customer -{} trading password ");
        CustomerStatusMessages result = CustomerStatusMessages.FAIL;
        if (customerNumber == null || "".equals(customerNumber) || tradingPassword == null) {
            return CustomerStatusMessages.INVALID_INPUT_PARAMETER;
        }
        try {
            Customer customer = customerManagerFacade.getCustomerByCustomerNumber(customerNumber);
            logger.debug("isValidTradingPassword : customer : ", customer);
            if (customer == null) {
                logger.debug("Can't find the customer got the given customer number");
                return CustomerStatusMessages.CUSTOMER_NOT_FOUND;
            }
            LoginProfile loginProfile = customer.getLoginProfile();
            if (loginProfile.getTradingPassword() != null && validatePassword(loginProfile.getTradingPassword().trim(), tradingPassword)) {
                logger.debug("trading password is valid");
                return CustomerStatusMessages.SUCCESS;
            } else {
                logger.debug("trading password is valid");
                return CustomerStatusMessages.INVALID_TRADING_PASSWORD;
            }
        } catch (Exception e) {
            logger.error("Problem in loading the customer", e);
            return result;
        }
    }

    @Override
    public CustomerStatusMessages changePassword(String customerNumber, String oldPassword, String newPassword, String oldTradingPassword,
                                                 String newTradingPassword) {


        //below is to validate the customer change both login and trading passwords
        if (customerNumber == null || "".equals(customerNumber)) {    //check whether customerNumber is null or empty
            return CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY;
        }
        if (oldPassword == null || "".equals(oldPassword)) { //validate oldPassword for null or empty
            return CustomerStatusMessages.PASSWORD_EMPTY;
        }
        if (newPassword == null || "".equals(newPassword)) { //validate newPassword for null or empty
            return CustomerStatusMessages.PASSWORD_EMPTY;
        }
        if (oldTradingPassword == null || "".equals(oldTradingPassword)) {  //validate oldTradingPassword for null or empty
            return CustomerStatusMessages.TRADING_PASSWORD_EMPTY;
        }
        if (newTradingPassword == null || "".equals(newTradingPassword)) {  //validate newTradingPassword for null or empty
            return CustomerStatusMessages.TRADING_PASSWORD_EMPTY;
        }
        if (oldPassword.equals(newPassword)) {  //check whether oldPassword equals newPassword
            return CustomerStatusMessages.NEW_AND_OLD_PASSWORD_SAME;
        }
        try {
            Customer customer = customerManagerFacade.getCustomerByCustomerNumber(customerNumber);
            logger.debug("changePassword : customer : ", customer);
            if (customer == null) {
                return CustomerStatusMessages.CUSTOMER_NOT_FOUND;
            }
            LoginProfile loginProfile = customer.getLoginProfile();
            logger.debug("changePassword : loginProfile : ", loginProfile);
            if (!validatePassword(loginProfile.getPassword(), oldPassword)) {
                logger.debug("changePassword : invalid password");
                return CustomerStatusMessages.INVALID_PASSWORD;
            }
            if (!validatePassword(loginProfile.getTradingPassword(), oldTradingPassword)) {
                logger.debug("changePassword : invalid trading password");
                return CustomerStatusMessages.INVALID_TRADING_PASSWORD;
            }
            loginProfile.setPassword(newPassword);
            loginProfile.setTradingPassword(newTradingPassword);
            loginProfile.setFirstTime(PropertyEnable.NO);
            customer.setLoginProfile(loginProfile);
            customerManagerFacade.updateCustomer(customer);
            return CustomerStatusMessages.SUCCESS;
        } catch (Exception e) {
            logger.warn("Customer not found with the given data");
            return CustomerStatusMessages.FAIL;
        }

    }


    @Override
    public CustomerStatusMessages changeLoginPassword(String customerNumber, String oldPassword, String newPassword) {
        if (customerNumber == null || "".equals(customerNumber)) {    //check whether customerNumber is null or empty
            return CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY;
        }
        if (oldPassword == null || "".equals(oldPassword)) { //validate oldPassword for null or empty
            return CustomerStatusMessages.PASSWORD_EMPTY;
        }
        if (newPassword == null || "".equals(newPassword)) { //validate newPassword for null or empty
            return CustomerStatusMessages.PASSWORD_EMPTY;
        }
        if (oldPassword.equals(newPassword)) {  //check whether oldPassword equals newPassword
            return CustomerStatusMessages.NEW_AND_OLD_PASSWORD_SAME;
        }
        try {
            Customer customer = customerManagerFacade.getCustomerByCustomerNumber(customerNumber);
            logger.debug("Change Login Password : customer : ", customer);
            if (customer == null) {
                return CustomerStatusMessages.CUSTOMER_NOT_FOUND;
            }
            LoginProfile loginProfile = customer.getLoginProfile();
            logger.debug("changePassword : loginProfile : ", loginProfile);
            if (!validatePassword(loginProfile.getPassword(), oldPassword)) {
                logger.debug("changePassword : invalid password");
                return CustomerStatusMessages.INVALID_PASSWORD;
            }
            loginProfile.setPassword(newPassword);
            loginProfile.setFirstTime(PropertyEnable.NO);
            customer.setLoginProfile(loginProfile);
            customerManagerFacade.updateCustomer(customer);
            return CustomerStatusMessages.SUCCESS;
        } catch (Exception e) {
            logger.warn("Customer not found with the given data");
            return CustomerStatusMessages.FAIL;
        }

    }


    @Override
    public CustomerStatusMessages changeTradingPassword(String customerNumber, String oldTradingPassword, String newTradingPassword) {
        if (customerNumber == null || "".equals(customerNumber)) {    //check whether customerNumber is null or empty
            return CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY;
        }
        if (oldTradingPassword == null || "".equals(oldTradingPassword)) {  //validate oldTradingPassword for null or empty
            return CustomerStatusMessages.TRADING_PASSWORD_EMPTY;
        }
        if (newTradingPassword == null || "".equals(newTradingPassword)) {  //validate newTradingPassword for null or empty
            return CustomerStatusMessages.TRADING_PASSWORD_EMPTY;
        }
        try {
            Customer customer = customerManagerFacade.getCustomerByCustomerNumber(customerNumber);
            logger.debug("Change Trading Password : customer : ", customer);
            if (customer == null) {
                return CustomerStatusMessages.CUSTOMER_NOT_FOUND;
            }
            LoginProfile loginProfile = customer.getLoginProfile();
            logger.debug("changePassword : loginProfile : ", loginProfile);
            if (!validatePassword(loginProfile.getTradingPassword(), oldTradingPassword)) {
                logger.debug("changePassword : invalid trading password");
                return CustomerStatusMessages.INVALID_TRADING_PASSWORD;
            }
            loginProfile.setTradingPassword(newTradingPassword);
            customer.setLoginProfile(loginProfile);
            customerManagerFacade.updateCustomer(customer);
            return CustomerStatusMessages.SUCCESS;
        } catch (Exception e) {
            logger.warn("Customer not found with the given data");
            return CustomerStatusMessages.FAIL;
        }
    }


    @Override
    public boolean logOut(String userName) {
        logger.info("Logging out the user with userName -{}", userName);
        if (userName == null || "".equals(userName)) {
            return false;
        }
        try {
            Customer customer = customerManagerFacade.getCustomerByCustomerNumber(userName);
            if (customer == null) {
                return false;
            }
            LoginHistoryBean loginHistoryBean = new LoginHistoryBean();
            loginHistoryBean.setCustomerNumber(customer.getCustomerNumber());
            loginHistoryBean.setLoginName(userName);
            loginHistoryBean.setVersion("");
            loginHistoryBean.setLoginTime(new Date());
            loginHistoryBean.setLoginStatus(LoginReplyStatus.LOG_OUT.getCode());
            loginHistoryBean.setId(System.currentTimeMillis() + count++);
            loginHistoryBean.setPrimaryKeyObject(loginHistoryBean.getId());
            logger.debug("logout : loginHistoryBean : ", loginHistoryBean);
            try {
                loginHistoryCacheFacade.putLoginHistory(loginHistoryBean);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validatePassword(String ourPassword, String theirPassword) {
        boolean result = ourPassword.equalsIgnoreCase(theirPassword);
        return result;
    }

}
