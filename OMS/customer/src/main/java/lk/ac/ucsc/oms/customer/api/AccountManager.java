package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.customer.api.beans.account.*;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;

import java.util.Date;
import java.util.List;


public interface AccountManager {

    void initialize() throws AccountManagementException;

    Account getAccount(String accountNumber) throws AccountManagementException;

    List<Account> getAllAccountsOfCustomer(String customerNumber) throws AccountManagementException;

    List<Account> getAllAccountsOfCustomerFromDb(String customerNumber) throws AccountManagementException;

    List<Account> getAllAccountOfCashAccount(String cashAccNumber) throws AccountManagementException;

    String getAccountNumberByExchangeAccount(String exchangeAccNumber, String exchangeCode) throws AccountManagementException;

    String getAccountNumberByCustomerNumber(String customerNumber) throws AccountManagementException;

    void addAccount(Account account) throws AccountManagementException;

    void updateAccount(Account account) throws AccountManagementException;

    void closeAccount(String accountNumber) throws AccountManagementException;

    String getParentAccountNumber(String customerNumber, String exchange, String execBroker, String currency) throws AccountManagementException;


    Account getEmptyAccount(String accountNumber, String customerNumber);

    ExchangeAccount getEmptyExchangeAccount(String accountNumber, String exchangeCode);

    OrderRoutingConfig getEmptyOrderRoutingConfig();

    List<ExchangeAccount> getAllExchangeAccounts(List<String> institutionList) throws AccountManagementException;

    List<Account> getAllAccounts(List<String> institutionList) throws AccountManagementException;

    boolean isCustodianAccount(String accountNumber, String exchangeCode) throws AccountManagementException;

    boolean isFullyDiscloseAccount(String accountNumber, String exchangeCode) throws AccountManagementException;

    Account getAccountForUpdate(String accountNumber) throws AccountManagementException;
}

