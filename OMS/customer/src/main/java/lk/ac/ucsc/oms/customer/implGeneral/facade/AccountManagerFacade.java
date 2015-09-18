package lk.ac.ucsc.oms.customer.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.HoldingManager;
import lk.ac.ucsc.oms.customer.api.beans.CustodianType;
import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.account.*;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.account.*;
import lk.ac.ucsc.oms.customer.implGeneral.facade.cache.AccountCacheFacade;
import lk.ac.ucsc.oms.customer.implGeneral.util.AccountNumberStore;
import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.SymbolPriceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AccountManagerFacade implements AccountManager {
    private static Logger logger = LogManager.getLogger(AccountManagerFacade.class);
    private AccountCacheFacade accountCacheFacade;
    private CustomerManager customerManager;
    private CashManager cashManager;
    private AbstractSequenceGenerator sequenceGenerator;

    public void setAccountCacheFacade(AccountCacheFacade accountCacheFacade) {
        this.accountCacheFacade = accountCacheFacade;
    }
    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    public void setCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void initialize() throws AccountManagementException {
        accountCacheFacade.initialize();
    }

    @Override
    public Account getAccount(String accountNumber) throws AccountManagementException {
        logger.info("Loading the Account with accountNumber -{}", accountNumber);
        if (accountNumber == null || "".equals(accountNumber)) {   //validate the account number for null and empty values
            throw new AccountManagementException("Insufficient information to find out Account");
        }
        return accountCacheFacade.getAccount(accountNumber);
    }

    @Override
    public Account getAccountForUpdate(String accountNumber) throws AccountManagementException {
        logger.info("Loading the Account with accountNumber -{}", accountNumber);
        if (accountNumber == null || "".equals(accountNumber)) {   //validate the account number for null and empty values
            throw new AccountManagementException("Insufficient information to find out Account");
        }
        return accountCacheFacade.getAccountForUpdate(accountNumber);
    }

    @Override
    public List<Account> getAllAccountsOfCustomer(String customerNumber) throws AccountManagementException {
        logger.info("Getting the customer - {} all accounts", customerNumber);
        if (customerNumber == null || "".equals(customerNumber)) {  // validate customer number for null and empty values
            throw new AccountManagementException("Insufficient information to find out Accounts of Customer");
        }

        List<String> accNumList;
        accNumList = AccountNumberStore.getAccountNumbers(customerNumber);
        if (accNumList == null || accNumList.isEmpty()) {
            logger.info("Account numbers not found in the cache. Hence use the DB query to load account of - {}", customerNumber);
            accNumList = accountCacheFacade.getAccountNumbersForCustomer(customerNumber);
            //add the loaded account numbers to the account store
            AccountNumberStore.addToAccountStore(customerNumber, accNumList);
        }
        List<Account> accountList = new ArrayList<>();

        //retrieve accounts using above account number list and return those accounts as a list
        if (!accNumList.isEmpty()) {
            for (String accNumber : accNumList) {
                accountList.add(getAccount(accNumber));
            }
        } else {
            logger.error("Accounts Not Found For Given Customer");
            throw new AccountManagementException("Accounts Not Found For Given Customer");
        }

        return accountList;
    }

    @Override
    public List<Account> getAllAccountsOfCustomerFromDb(String customerNumber) throws AccountManagementException {
        logger.info("Getting the customer - {} all accounts", customerNumber);
        if (customerNumber == null || "".equals(customerNumber)) {  // validate customer number for null and empty values
            throw new AccountManagementException("Insufficient information to find out Accounts of Customer");
        }

        //get account number list for given customer
        List<String> accNumList = accountCacheFacade.getAccountNumbersForCustomerFromDb(customerNumber);
        List<Account> accountList = new ArrayList<>();

        //retrieve accounts using above account number list and return those accounts as a list
        if (accNumList != null) {
            for (String accNumber : accNumList) {
                accountList.add(getAccount(accNumber));
            }
        } else {
            logger.error("Accounts Not Found For Given Customer");
            throw new AccountManagementException("Accounts Not Found For Given Customer");
        }
        return accountList;
    }


    @Override
    public List<Account> getAllAccountOfCashAccount(String cashAccNumber) throws AccountManagementException {
        logger.info("Getting the cash account - {} all accounts", cashAccNumber);
        if (cashAccNumber == null || "".equals(cashAccNumber)) {    //validate cash account number for null and empty values
            throw new AccountManagementException("Cash Account Number Can't be null or empty");
        }

        //get account number list for given cash account
        List<String> accNumList = accountCacheFacade.getAccountNumbersForCashAccount(cashAccNumber);
        List<Account> accountList = new ArrayList<>();

        //retrieve accounts using above account number list and return those accounts as a list
        if (accNumList != null) {
            for (String accNumber : accNumList) {
                accountList.add(getAccount(accNumber));
            }
        } else {
            logger.error("Accounts Not Found For Given Cash Account");
            throw new AccountManagementException("Accounts Not Found For Given Cash Account");
        }
        return accountList;
    }

    @Override
    public String getAccountNumberByExchangeAccount(String exchangeAccNumber, String exchangeCode) throws AccountManagementException {
        logger.info("Loading the account number using the exchangeAccNumber -{} and exchange -{}", exchangeAccNumber, exchangeCode);
        try {
            return accountCacheFacade.getAccountNumberByExchangeAccount(exchangeAccNumber, exchangeCode);
        } catch (Exception e) {
            logger.info("Error in getting account number for given exchange account", e);
            throw new AccountManagementException("Error in getting account number for given exchange account", e);
        }
    }

    @Override
    public String getAccountNumberByCustomerNumber(String customerNumber) throws AccountManagementException {
        logger.info("Getting the customer account number by customer number - {}", customerNumber);
        if (customerNumber == null || "".equals(customerNumber)) {
            logger.error("Customer Number Can't be null to find Account Number");
            throw new AccountManagementException("Customer Number Can't be null to find Account Number");
        }
        List<String> accNumList = accountCacheFacade.getAccountNumbersForCustomer(customerNumber);
        if (accNumList != null && !accNumList.isEmpty()) {
            return accNumList.get(0);
        } else {
            logger.error("Accounts Not Found For Given Customer");
            throw new AccountManagementException("Accounts Not Found For Given Customer");
        }
    }

    @Override
    public void addAccount(Account account) throws AccountManagementException {
        logger.info("Adding the account {} to cache", account);
        validateAccount(account); //validate the given account before creation
        AccountBean accountBean = (AccountBean) account;
        try {
            account.setAccId(Long.parseLong(sequenceGenerator.getSequenceNumber()));// set generated account id from sequence generator as account id of the account bean
            logger.debug("AccountManagerFacade : addAccount : accId : " + account.getAccId());
            account.getOrderRoutingConfig().setAccId(account.getAccId());
            //if no institution code is set setting Customers Institution to Account
            if (account.getInstituteCode() == null || account.getInstituteCode().isEmpty()) {
                Customer customer = customerManager.getCustomerByCustomerNumber(account.getCustomerNumber());
                account.setInstituteCode(customer.getInstitutionCode());
            }
            accountCacheFacade.putAccount(accountBean); //add account to cache
        } catch (Exception e) {
            logger.error("Problem in Adding New Account", e);
            throw new AccountManagementException("Problem in Adding New Account", e);
        }
    }

    @Override
    public void updateAccount(Account account) throws AccountManagementException {
        logger.info("Updating the account - {}", account);
        validateAccount(account);   //validate account before updating
        AccountBean accountBean = (AccountBean) account;
        accountCacheFacade.updateAccount(accountBean);
    }


    @Override
    public void closeAccount(String accountNumber) throws AccountManagementException {
        logger.info("Closing the cash account with accountNumber -{}", accountNumber);
        AccountBean accountBean = (AccountBean) getAccount(accountNumber);
        accountCacheFacade.closeAccount(accountBean);
    }

    @Override
    public String getParentAccountNumber(String customerNumber, String exchange, String execBroker, String currency) throws AccountManagementException { //TODO--dasun-- this medhod can be written  in a better way. For the moment keep it. we need to look at it again
        logger.info("Getting the parent account number for customer - {}, exec broker - {} ", customerNumber, execBroker);
        if (customerNumber == null || "".equals(customerNumber)) {
            throw new AccountManagementException("Customer Number Can't be null or empty");
        }
        String parentAccNumber = null;
        try {
            Customer customer = customerManager.getCustomerByCustomerNumber(customerNumber);
            logger.info("get parent account number : get customer by customer number : customer : {}", customer);
            if (customer != null && customer.getParentCusNumber() != null && !"".equals(customer.getParentCusNumber()) && !"-1".equals(customer.getParentCusNumber())) {
                //this account is a sub account hence need to find out the parent account.
                List<Account> accountList = getAllAccountsOfCustomer(customer.getParentCusNumber());
                //check the account list size and print the size of the parent customers account list
                if (accountList != null) {
                    logger.info("get parent account number : get all accounts of customer - {} ,  accountList size- {}", customer.getParentCusNumber(), accountList.size());
                }
                Account parentAccount = null;
                ExchangeAccount exchangeAccount;
                List<Account> parentAccList = new ArrayList<>();
                if (execBroker != null && !"".equals(execBroker) && !"-1".equals(execBroker)) {
                    //get the parent account using exec broker
                    for (Account account : accountList) {
                        logger.info("get parent account number : account : {}", account);
                        exchangeAccount = account.getExchangeAccountsList().get(exchange);
                        if (exchangeAccount != null && exchangeAccount.getExecBrokerCode() != null && !exchangeAccount.getExecBrokerCode().trim().equalsIgnoreCase("-1")
                                && exchangeAccount.getExecBrokerCode().equals(execBroker) && (exchangeAccount.getTradingEnable() == PropertyEnable.YES)) {
                            parentAccList.add(account);
                        }
                    }
                } else {
                    //Parent can't find using exec broker
                    //get the parent account having the exchange account for same exchange
                    for (Account account : accountList) {
                        logger.info("get parent account number : account : {} ", account);
                        exchangeAccount = account.getExchangeAccountsList().get(exchange);
                        if (exchangeAccount != null && exchangeAccount.getTradingEnable() == PropertyEnable.YES) {
                            parentAccList.add(account);
                        }
                    }
                }
                CashAccount cashAccount = null;
                List<Account> parentAccList2 = new ArrayList<>();
                //has multiple parent account for same exchange hence check the currency of the parent account with
                // the symbol currency of the order
                if (parentAccList.size() > 1) {
                    for (Account account : parentAccList) {
                        logger.info("get parent account number : account : {}", account);
                        cashAccount = cashManager.getCashAccount(account.getCashAccNumber());
                        if (cashAccount != null && cashAccount.getCurrency().equalsIgnoreCase(currency)) {
                            parentAccList2.add(account);
                        }
                    }
                    if (parentAccList2.size() > 1) {
                        //Has multiple parent account hence reject the order
                        logger.info("parent account list has multiple parent accounts : {}", parentAccList);
                        parentAccNumber = "#";
                    } else {
                        //If customer has one account with the symbol currency take that one
                        if (parentAccList2.size() == 1) {
                            parentAccount = parentAccList2.get(0);
                        }
                    }
                } else {
                    if (parentAccList.size() == 1) {
                        parentAccount = parentAccList.get(0);
                    }
                }
                if (parentAccount != null) {
                    return parentAccount.getAccountNumber();
                }

            } else {
                logger.info("Account is not sub account");
                throw new AccountManagementException("Account is not sub account");

            }
        } catch (Exception e) {
            logger.error("issue in getting the parent account - {}", e);
            throw new AccountManagementException("Error In getting parent account number", e);
        }
        return parentAccNumber;
    }


    @Override
    public Account getEmptyAccount(String accountNumber, String customerNumber) {
        return new AccountBean(accountNumber, customerNumber);
    }

    @Override
    public ExchangeAccount getEmptyExchangeAccount(String accountNumber, String exchangeCode) {
        return new ExchangeAccountBean(accountNumber, exchangeCode);
    }


    @Override
    public OrderRoutingConfig getEmptyOrderRoutingConfig() {
        return new OrderRoutingConfigBean();
    }

    @Override
    public List<ExchangeAccount> getAllExchangeAccounts(List<String> institutionList) throws AccountManagementException {
        logger.info("Getting the all exchange accounts");

        return accountCacheFacade.getAllExchangeAccount(institutionList);     //getting Exchange Accounts from persister if  PARTIALITY_LOADED or NOT_LOADED
    }

    @Override
    public List<Account> getAllAccounts(List<String> institutionList) throws AccountManagementException {
        logger.info("Getting all accounts");
        return accountCacheFacade.getAllAccount(institutionList);

    }

    @Override
    public boolean isCustodianAccount(String accountNumber, String exchangeCode) throws AccountManagementException {
        return false;
    }

    @Override
    public boolean isFullyDiscloseAccount(String accountNumber, String exchangeCode) throws AccountManagementException {
        int accountType = getAccount(accountNumber).getExchangeAccountsList().get(exchangeCode).getAccountType();
        return accountType == 0;
    }

    private void validateAccount(Account account) throws AccountManagementException {
        if (account == null) {
            throw new AccountManagementException("Cash Account can't be null");
        }
        if (account.getAccountNumber() == null || "".equals(account.getAccountNumber())) {
            throw new AccountManagementException(CustomerStatusMessages.ACCOUNT_NUMBER_EMPTY.name());
        }

        if (account.getCustomerNumber() == null || "".equals(account.getCustomerNumber())) {
            throw new AccountManagementException(CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY.name());
        }
    }
}
