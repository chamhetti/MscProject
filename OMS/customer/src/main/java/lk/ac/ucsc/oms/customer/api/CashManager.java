package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.customer.api.beans.cash.BalanceAndDues;
import lk.ac.ucsc.oms.customer.api.beans.cash.BlockAmount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CashManager {

    void initialize() throws CashManagementException;

    void blockCashOrMargin(String cashAccountNumber, BlockAmount blockAmount) throws CashManagementException;

    void updateBalanceAndDues(String cashAccountNumber, BalanceAndDues balanceAndDues, CashLog cashLog) throws CashManagementException;

    CashAccount getCashAccount(String cashAccountNumber) throws CashManagementException;

    void addCashAccount(CashAccount cashAccount) throws CashManagementException;

    void updateCashAccount(CashAccount cashAccount) throws CashManagementException;

    void closeCashAccount(String cashAccountNumber) throws CashManagementException;

    void addCashLog(CashLog cashLog) throws CashManagementException;

    List<CashLog> getCashLogs(String orderID, String accountNumber) throws CashManagementException;

    CashLog getEmptyCashLog();

    CashAccount getEmptyCashAccount(String cashAccountNumber);

    void persistCashLogAsBulk() throws CashManagementException;

    void persistCashAccountDetailsAsBulk() throws CashManagementException;

    List<CashAccount> getAllCashAccount(List<String> institutionList) throws CashManagementException;

    List<CashLog> getAllCashLog(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws CashManagementException;

    BlockAmount getEmptyBlockAmountBean();

    BalanceAndDues getEmptyBalanceAndDuesBean();

    double getBuyingPower(String cashAccountNumber) throws CashManagementException;

    double getODLimit(String cashAccountNumber) throws CashManagementException;

    CashAccount getCashAccountForUpdate(String cashAccountNumber) throws CashManagementException;

}
