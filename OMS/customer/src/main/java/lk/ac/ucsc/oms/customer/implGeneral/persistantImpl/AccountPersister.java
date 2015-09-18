package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean;

import java.util.List;

public interface AccountPersister extends CachePersister {

    void deleteFromDB(AccountBean accountBean) throws AccountManagementException;

    String getLastAccountId() throws AccountManagementException;

    List<String> getAccountNumbersByCustomerNumber(String customerNumber) throws AccountManagementException;

    String getAccountNumberByExchangeAccNumber(String exchangeAccNumber, String exchangeCode) throws AccountManagementException;

    List<String> getAccountNumbersByCashAccountNumber(String cashAccNumber) throws AccountManagementException;

    List<ExchangeAccount> getAllExchangeAccounts(String institutionList) throws AccountManagementException;


    List<Account> getAllAccounts(String institutionList) throws AccountManagementException;
}
