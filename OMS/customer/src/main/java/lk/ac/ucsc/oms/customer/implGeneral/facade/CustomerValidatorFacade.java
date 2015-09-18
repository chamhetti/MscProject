package lk.ac.ucsc.oms.customer.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.CustomerValidator;
import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.CustomerValidationReply;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerValidationException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.CustomerValidationReplyBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerValidatorFacade implements CustomerValidator {
    private static Logger logger = LogManager.getLogger(CustomerValidatorFacade.class);

    private AccountManager accountManger;
    private CashManager cashManager;


    @Override
    public CustomerValidationReply hasValidAccount(String accountNumber) throws CustomerValidationException {

        if (accountNumber == null || "".equals(accountNumber)) {
            return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.ACCOUNT_NUMBER_EMPTY);
        }
        try {
            //validate the account
            Account account = accountManger.getAccount(accountNumber);
            //account should be available
            if (account == null) {
                return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.INVALID_ACCOUNT);
            }
            //Account should be approved
            if (account.getStatus() != RecordStatus.APPROVED) {
                return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.INACTIVE_ACCOUNT);
            }
        } catch (Exception e) {
            logger.error("Account not found", e);
            throw new CustomerValidationException("System Error", e);
        }
        return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.SUCCESS, null);
    }

    @Override
    public CustomerValidationReply hasValidExchangeAccount(String accountNumber, String exchangeCode) throws CustomerValidationException {
        try {
            Account account = accountManger.getAccount(accountNumber);
            ExchangeAccount exchangeAccount = account.getExchangeAccountsList().get(exchangeCode);
            if (exchangeAccount == null || exchangeAccount.getStatus() != RecordStatus.APPROVED) {
                return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.INVALID_EXCHANGE_ACCOUNT);
            }
            if (exchangeAccount.getTradingEnable() == PropertyEnable.NO) {
                return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.TRADING_DISABLE);
            }
        } catch (Exception e) {
            logger.error("Exchange Account not found", e);
            throw new CustomerValidationException("System Error", e);
        }
        return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.SUCCESS, null);
    }

    @Override
    public CustomerValidationReply hasValidCashAccount(String cashAccountNumber) throws CustomerValidationException {
        try {
            CashAccount cashAccount = cashManager.getCashAccount(cashAccountNumber);

            if (cashAccount == null) {
                return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.INVALID_CASH_ACCOUNT);
            }
            if (cashAccount.getStatus() != RecordStatus.APPROVED) {
                return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.FAILED, CustomerStatusMessages.INVALID_CASH_ACCOUNT);

            }
        } catch (Exception e) {
            logger.error("Cash Account not found", e);
            throw new CustomerValidationException("System Error", e);
        }
        return new CustomerValidationReplyBean(CustomerValidationReply.CustomerValidationResult.SUCCESS, null);
    }

    @Override
    public boolean isShariaCompliantAccount(String accountNumber, String exchangeCode) throws CustomerValidationException {
        PropertyEnable shariaComplient = PropertyEnable.NO;
        try {
            Account account = accountManger.getAccount(accountNumber);
            if (account.getExchangeAccountsList().containsKey(exchangeCode.trim())) {
                shariaComplient = account.getExchangeAccountsList().get(exchangeCode.trim()).getShariaComplient();
            }
            return shariaComplient == PropertyEnable.YES;
        } catch (Exception e) {
            logger.error("Account not found", e);
            throw new CustomerValidationException("System Error", e);
        }
    }

    @Override
    public boolean isRestrictedSymbolForCustomer(String accountNumber, String symbolCode) throws CustomerValidationException {
        try {
            Account account = accountManger.getAccount(accountNumber);
            return account.getOrderRoutingConfig().getRestrictedSymbolList().contains(symbolCode);
        } catch (Exception e) {
            logger.error("Account not found", e);
            throw new CustomerValidationException("System Error", e);
        }
    }

    public void setCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
    }

    public void setAccountManger(AccountManager accountManger) {
        this.accountManger = accountManger;
    }
}
