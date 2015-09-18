package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean;

import java.util.List;

public interface CashAccountPersister extends CachePersister {

    void deleteFromDB(CashAccountBean cashAccountBean) throws CashManagementException;

    List<String> getCashAccountNumbersByCustomerNumber(String customerNumber) throws CashManagementException;

    String getLastCashAccountId() throws CashManagementException;

    List<CacheObject> getAllCashAccountForInstitutions(String institutionList) throws CashManagementException;

    boolean updateDailyODLimit() throws CashManagementException;

    void updateDayMarginLimit() throws CashManagementException;

    void updateDayMarginDue() throws CashManagementException;

    void updatePendingSettle() throws CashManagementException;

}
