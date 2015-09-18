package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReply;


public interface CustomerLoginManager {

    void initialize();

    LoginReply loginCustomer(String loginName, String password, ClientChannel channel,
                             String version, String masterAccNumber, String institutionCode);

    CustomerStatusMessages isValidTradingPassword(String customerNumber, String tradingPassword);

    CustomerStatusMessages changePassword(String customerNumber, String oldPassword, String newPassword, String oldTradingPassword,
                                          String newTradingPassword);

    CustomerStatusMessages changeLoginPassword(String customerNumber, String oldPassword, String newPassword);


    CustomerStatusMessages changeTradingPassword(String customerNumber, String oldTradingPassword, String newTradingPassword);


    boolean logOut(String userName);
}
