package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CashLogPersister extends CachePersister {

    List<CashLog> findCashLogByCriteria(String orderID, String cashAccountID) throws CashManagementException;

    List<CashLog> findCashLogs(String cashAccId, String accountNumber, String orderNumber, String symbol, String cashLogCode,
                               int fromRecord, int toRecord, Date startDate, Date endDate) throws CashManagementException;

    String getLastCashLogId() throws CashManagementException;

    List<CashLog> getAllCashLog(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws CashManagementException;

    List<CashLog> getCustomerTodayCashLog(String customerNumber);
}
