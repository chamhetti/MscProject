package lk.ac.ucsc.oms.login_controller.helper.customer;


import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.CustomerLoginManager;
import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.login.LoginReply;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerLoginException;

import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SystemConfigurationFactory;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnector;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.PortfolioHeader;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.TradingAccount;
import lk.ac.ucsc.oms.login_controller.helper.util.ModuleDINames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.*;


public class CustomerOperationsHelper {
    private static Logger logger = LogManager.getLogger(CustomerOperationsHelper.class);

    private Customer customer = null;
    private AccountManager accountManager = null;
    private CashManager cashManager = null;
    private CustomerLoginManager customerLoginManager = null;
    private TrsConnector trsConnector;

    public CustomerOperationsHelper(ApplicationContext ctx, String username) throws CustomerException {
        CustomerManager customerManager = ctx.getBean(ModuleDINames.CUSTOMER_MANAGER_DI_NAME, CustomerManager.class);
        accountManager = ctx.getBean(ModuleDINames.ACCOUNT_MANAGER_DI_NAME, AccountManager.class);
        cashManager = ctx.getBean(ModuleDINames.CASH_MANAGER_DI_NAME, CashManager.class);
        customerLoginManager = ctx.getBean(ModuleDINames.CUSTOMER_LOGIN_MANAGER_DI_NAME, CustomerLoginManager.class);
        trsConnector = ctx.getBean(ModuleDINames.TRS_CONNECTOR_DI_NAME, TrsConnector.class);
        customer = customerManager.getCustomerByLoginNameOrAlias(username);
    }

    public Customer getCustomer() {
        return customer;
    }

    public LoginReply login(String password, ClientChannel channel, String version,
                            String parentAccountNumber, String institutionCode) throws CustomerLoginException {
        String username = customer.getLoginProfile().getLoginName();
        logger.info("Customer user name -{}", username);
        return customerLoginManager.loginCustomer(username, password, channel, version, parentAccountNumber, institutionCode);
    }

    public String getName() {
        return customer.getPersonalProfile().getFirstName(); // only first name is sent as requested by RIA
    }

    public String getCustomerNumber() {
        return customer.getCustomerNumber();
    }


    public String getLoginAlias() {
        return customer.getLoginProfile().getLoginAlias();
    }


    public List<PortfolioHeader> getPortfolioHeaders() throws OMSException{
        List<PortfolioHeader> portfolioHeaderList = getArrayList();
        //get the all the account of the customer
        List<Account> list = accountManager.getAllAccountsOfCustomerFromDb(customer.getCustomerNumber());
        Iterator<Account> it = list.iterator();
        //iterate the account list
        while (it.hasNext()) {
            Account acc = it.next();
            if (!(acc.getStatus() == RecordStatus.APPROVED || acc.getStatus() == RecordStatus.PENDING_APPROVAL)){
                logger.info("Ignoring unapproved portfolio for login response: {}", acc.getStatus());
                continue;     // only approved and approval pending account are sent
            }
            //get the empty portfolio header reply
            lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.PortfolioHeader header = trsConnector.getEmptyPortfolioHeader();
            //get the exchange code set of customer exchange account belong to a account
            Set<String> exchangesSet = acc.getExchangeAccountsList().keySet();
            header.setExchanges(setToStringCommaSeparated(exchangesSet));
            header.setName(acc.getAccountName());
            //set the id of the broker. that is the short code used to identify the broker. Used to show in error messages by clients
            try {
                header.setBrokerID(getSystemConfigurationFactory().getSysLevelParamManager().
                        getSysLevelParameter(SystemParameter.BROKER_ID).getParaValue());
            } catch (SysConfigException e) {
                logger.error("Error setting broker id: ", e.getMessage());
            }
            //get cash account related to this customer account
            CashAccount cashAccount = cashManager.getCashAccount(acc.getCashAccNumber());
            //set account is active or not
            header.setActivePortfolio(acc.getStatus().getCode() == 1);
            //set the account number
            header.setPortflioID(acc.getAccountNumber());
            //set the cash account number
            header.setAccountNo(acc.getCashAccNumber());
            //set the cash account currency
            header.setCashAccountCurrency(cashAccount.getCurrency());
            header.setDefault(true); //There is no default portfolio since it is just one portfolio for an account
            //setting the exchange account related data
            List<TradingAccount> tradingAccountList = new ArrayList<>();
            ExchangeAccount exchangeAccountBean;
            //iterate all the exchange accounts
            for (String key : exchangesSet) {
                //get the exchange account
                exchangeAccountBean = acc.getExchangeAccountsList().get(key);
                //get empty trading account reply
                TradingAccount tradingAccount = trsConnector.getEmptyTradingAccount();
                //set the short code of the exchange
                tradingAccount.setExchange(exchangeAccountBean.getExchangeCode());
                //set trading enable or not
                tradingAccount.setIstradingEnabled(exchangeAccountBean.getTradingEnable().getCode() == 1 ? true : false);
                //set the account number given by exchange
                tradingAccount.setTradingAcctNo(exchangeAccountBean.getExchangeAccNumber());
                tradingAccount.setTrdDisenablingReason("");
                tradingAccountList.add(tradingAccount);
            }
            header.setTradingAccountList(tradingAccountList);
            portfolioHeaderList.add(header);
        }

        return portfolioHeaderList;
    }

    public SystemConfigurationFactory getSystemConfigurationFactory() throws SysConfigException {
        return SystemConfigurationFactory.getInstance();
    }

    public List<PortfolioHeader> getArrayList() {
        return new ArrayList<>();
    }


    public Date currentDate() {
        return new Date();
    }

    private String setToStringCommaSeparated(Set<String> exchangesSet) {
        StringBuilder reply = new StringBuilder("");
        Iterator<String> it = exchangesSet.iterator();
        while (it.hasNext()) {
            reply.append(it.next());
            if (it.hasNext()) {
                reply.append(',');
            }
        }
        return reply.toString();
    }

    public boolean logOutUser() {
        return customerLoginManager.logOut(customer.getCustomerNumber());
    }

}
