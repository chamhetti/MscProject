package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate.utility;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: vimalanathanr
 * Date: 5/14/13
 * Time: 6:26 PM
 * <p/>
 * this class is for generate the hql query for given parameters
 */
public class CashLogQueryGenerator {
    /**
     * this method generates hql query for given cashAccId, accountNumber, ..etc parameters
     *
     * @param cashAccId
     * @param accountNumber
     * @param orderNumber
     * @param symbol
     * @param cashLogCode
     * @param startDate
     * @param endDate
     * @return hql
     */
    public String createQueryForFindCashLogs(String cashAccId, String accountNumber, String orderNumber, String symbol, String cashLogCode, Date startDate, Date endDate) {
        StringBuilder hql = new StringBuilder("FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean C WHERE ");

        hql.append("C.cashAccId = " + cashAccId + " ");

        if (accountNumber != null && !"".equals(accountNumber)) {
            hql.append("AND C.accountNumber = '" + accountNumber + "' ");
        }

        if (orderNumber != null && !"".equals(orderNumber)) {
            hql.append("AND C.orderNumber = '" + orderNumber + "' ");
        }

        if (symbol != null && !"".equals(symbol)) {
            hql.append("AND C.symbol = '" + symbol + "' ");
        }

        if (cashLogCode != null && !"".equals(cashLogCode)) {
            hql.append("AND C.cashLogCode = '" + cashLogCode + "' ");
        }

        if (startDate != null && endDate != null) {
            hql.append("AND C.transactionDate BETWEEN :fromDate AND :toDate");
        }

        hql.append("ORDER BY C.cashLogId");

        return hql.toString();
    }

    /**
     * create hql query for load cash log with date range and institution or filter
     *
     * @param fromDate        for orders
     * @param toDate          of orders
     * @param institutionList of orders
     * @return hqlQuery
     */
    public String createQueryToGetOrdersCashLogByDatesAndInstitutions(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) {
        StringBuilder mainHql = new StringBuilder("FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean C ");

        StringBuilder hql = new StringBuilder();
        StringBuilder institutionListStr = new StringBuilder();

        if ((institutionList != null) && !institutionList.isEmpty()) {
            for (String institution : institutionList) {
                institutionListStr.append("'" + institution + "',");
            }
            hql.append(" C.institutionCode IN (" + institutionListStr.toString().substring(0, institutionListStr.toString().length() - 1) + ") ");
        }

        //Limiting the orders depends on order created date
        if (fromDate != null && toDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.transactionDate BETWEEN :fromDate AND :toDate ");
        } else if (fromDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.transactionDate BETWEEN :fromDate AND :fromDate ");
        } else if (toDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.transactionDate BETWEEN :toDate AND :toDate ");
        }

        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if (hql.toString().isEmpty()) {
                    hql.append(" " + entry.getKey() + " = '" + entry.getValue() + "' ");
                } else {
                    hql.append(" AND " + entry.getKey() + " = '" + entry.getValue() + "' ");
                }
            }
        }
        if (!hql.toString().isEmpty()) {
            mainHql.append(" WHERE ");
            mainHql.append(hql);
        }
        return mainHql.toString();
    }
}
