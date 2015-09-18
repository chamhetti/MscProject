package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate.utility;


import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static lk.ac.ucsc.oms.common_utility.api.constants.OrderSearchFilter.*;

/**
 * User: vimalanathanr
 * Date: 4/22/13
 * Time: 2:03 PM
 * <p/>
 * this class is used to generate hql queries for order persister
 */
public class OrderSearchHQLGenerator {

    private static final String WILD_CARD_TEXT = "%";

    /**
     * create hql query for find orders by two given criteria and filter with dates
     *
     * @param fromDate              for search
     * @param toDate                for search
     * @param primarySearchFilter   search field 1
     * @param primarySearchValue    value for search field 1
     * @param secondarySearchFilter search field 2
     * @param secondarySearchValue  value for search field 2
     * @return hqlQuery
     */
    public String createQueryForSearchOrders(Date fromDate, Date toDate, int primarySearchFilter, String primarySearchValue,
                                             int secondarySearchFilter, String secondarySearchValue, int orderGroup, int searchMode,
                                             long dealerId, boolean isGlobalDealer) throws OrderException {

        StringBuilder hql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C ");
        boolean result = false;

        //Query for primary search option
        if (primarySearchFilter >= 1) {
            result = true;
            switch (primarySearchFilter) {
                case ACCOUNT_NUMBER:
                    hql.append("WHERE C.securityAccount LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case SYMBOL:
                    hql.append("WHERE UPPER(C.symbol) LIKE UPPER('" + primarySearchValue + WILD_CARD_TEXT + "') ");
                    break;
                case STATUS:
                    hql.append("WHERE C.orderStatus LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CLIENT_ORDER_ID:
                    hql.append("WHERE C.clOrdID LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CUSTOMER_NUMBER:
                    hql.append("WHERE C.customerNumber LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case INSTITUTION:
                    hql.append("WHERE C.institutionCode LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case ORDER_NUMBER:
                    hql.append("WHERE C.orderNo LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case REMOTE_CLIENT_ORDER_ID:
                    hql.append("WHERE C.remoteClOrdId LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case REMOTE_ORIGINAL_CLIENT_ORDER_ID:
                    hql.append("WHERE C.remoteOrigOrdId LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case BROKER_SID:
                    hql.append("WHERE C.execBrokerId LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case EXCHANGE:
                    hql.append("WHERE C.exchange LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case DEALER_NAME:
                    hql.append("WHERE UPPER(C.userName) LIKE UPPER('" + primarySearchValue + WILD_CARD_TEXT + "') ");
                    break;
                default:
                    throw new OrderException("Not Implemented Order Search Criteria Found");
            }

        }

        //Query for secondary search option
        if (secondarySearchFilter >= 1) {
            result = true;
            switch (secondarySearchFilter) {
                case ACCOUNT_NUMBER:
                    hql.append("AND C.securityAccount LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case SYMBOL:
                    hql.append("AND UPPER(C.symbol) LIKE UPPER('" + secondarySearchValue + WILD_CARD_TEXT + "') ");
                    break;
                case STATUS:
                    hql.append("AND C.orderStatus LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CLIENT_ORDER_ID:
                    hql.append("AND C.clOrdID LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CUSTOMER_NUMBER:
                    hql.append("AND C.customerNumber LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                default:
                    throw new OrderException("Not Implemented Order Search Criteria Found");

            }
        }



        //Query for filtering invalidated orders
        if (searchMode == 2) {
            hql.append((result ? "AND " : "WHERE ") + "C.orderStatus NOT IN('f','I','J','e','m') ");
            result = true;
        }

        //Limiting the orders depends on order created date
        if (fromDate != null && toDate != null) {
            hql.append((result ? "AND " : "WHERE ") + "C.createDate BETWEEN :fromDate AND :toDate ");
        }

        //Query for filtering orders for dealer
        if (!isGlobalDealer) {
            //Hibernate query for filtering customers for given dealer
            String hqlDealerFilter = "( " +
                    "C.institutionCode IN (:INSTITUTIONS) " +
                    "OR C.customerNumber IN " +
                    "(SELECT DISTINCT IC.customerNo FROM lk.ac.ucsc.oms.user.implGeneral.beans.CustomerRepresentativeBean IC WHERE IC.allowedCustomer = 1 AND IC.userID = " + dealerId + ") " +
                    ") " +
                    "AND C.customerNumber NOT IN " +
                    "(SELECT DISTINCT EC.customerNo FROM lk.ac.ucsc.oms.user.implGeneral.beans.CustomerRepresentativeBean EC WHERE EC.allowedCustomer = 0 AND EC.userID = " + dealerId + ") ";
            hql.append((result ? "AND " : "WHERE ") + hqlDealerFilter);
        }

        //Query for ordering records by order Id
        hql.append("ORDER BY C.id DESC");

        return hql.toString();
    }
    /**
     * create hql query for find orders by two given criteria and filter with dates
     *
     * @param fromDate              for search
     * @param toDate                for search
     * @param primarySearchFilter   search field 1
     * @param primarySearchValue    value for search field 1
     * @return hqlQuery
     */
    public String createQueryForSearchOrdersXt(String execBrokerCode, Date fromDate, Date toDate, int primarySearchFilter, String primarySearchValue,
                                             boolean open) throws OrderException {

        StringBuilder hql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C ");
        boolean result = false;

        //Query for primary search option
        if (primarySearchFilter >= 1) {
            result = true;
            switch (primarySearchFilter) {
                case ACCOUNT_NUMBER:
                    hql.append("WHERE C.securityAccount LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case SYMBOL:
                    hql.append("WHERE UPPER(C.symbol) LIKE UPPER('" + primarySearchValue + WILD_CARD_TEXT + "') ");
                    break;
                case STATUS:
                    hql.append("WHERE C.orderStatus LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CLIENT_ORDER_ID:
                    hql.append("WHERE C.clOrdID LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CUSTOMER_NUMBER:
                    hql.append("WHERE C.customerNumber LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case ORDER_NUMBER:
                    hql.append("WHERE C.orderNo LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case EXCHANGE:
                    hql.append("WHERE C.exchange LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                default:
                    throw new OrderException("Not Implemented Order Search Criteria Found");
            }

        }

        hql.append((result ? "AND " : "WHERE ") + "C.execBrokerId = '" + execBrokerCode + "' ");

        //Query for filtering orders for different order groups
        if (open == true) {
            hql.append(("AND ") + "C.orderStatus IN('0','5','R','9','L','1','Q','O','p') AND C.timeInForce<>1 ");
        }

        //Limiting the orders depends on order created date
        if (fromDate != null && toDate != null) {
            hql.append(("AND ") + "C.createDate BETWEEN :fromDate AND :toDate ");
        }

        //Query for ordering records by order Id
        hql.append("ORDER BY C.id DESC");

        return hql.toString();
    }


    /**
     * create hql query for find orders by two given criteria and filter with dates
     *
     * @param fromDate              for search
     * @param toDate                for search
     * @param primarySearchFilter   search field 1
     * @param primarySearchValue    value for search field 1
     * @param secondarySearchFilter search field 2
     * @param secondarySearchValue  value for search field 2
     * @return hqlQuery
     */
    public String createQueryForSearchWaitingForApprovalOrders(Date fromDate, Date toDate, int primarySearchFilter, String primarySearchValue,
                                                               int secondarySearchFilter, String secondarySearchValue, long dealerId,
                                                               boolean isGlobalDealer) throws OrderException {

        StringBuilder hql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.orderStatus = '" + OrderStatus.WAITING_FOR_APPROVAL.getCode() + "' ");

        //Query for primary search option
        if (primarySearchFilter >= 1) {
            switch (primarySearchFilter) {
                case ACCOUNT_NUMBER:
                    hql.append("AND C.securityAccount LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case SYMBOL:
                    hql.append("AND UPPER(C.symbol) LIKE UPPER('" + primarySearchValue + WILD_CARD_TEXT + "') ");
                    break;
                case STATUS:
                    hql.append("AND C.orderStatus LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CLIENT_ORDER_ID:
                    hql.append("AND C.clOrdID LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CUSTOMER_NUMBER:
                    hql.append("AND C.customerNumber LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case INSTITUTION:
                    hql.append("AND C.institutionID LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case ORDER_NUMBER:
                    hql.append("AND C.orderNo LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case REMOTE_CLIENT_ORDER_ID:
                    hql.append("AND C.remoteClOrdId LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case REMOTE_ORIGINAL_CLIENT_ORDER_ID:
                    hql.append("AND C.remoteOrigOrdId LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case BROKER_SID:
                    hql.append("AND C.execBrokerId LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case EXCHANGE:
                    hql.append("AND C.exchange LIKE '" + primarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                default:
                    throw new OrderException("Not Implemented Order Search Criteria Found");
            }

        }

        //Query for secondary search option
        if (secondarySearchFilter >= 1) {
            switch (secondarySearchFilter) {
                case ACCOUNT_NUMBER:
                    hql.append("AND C.securityAccount LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case SYMBOL:
                    hql.append("AND UPPER(C.symbol) LIKE UPPER('" + secondarySearchValue + WILD_CARD_TEXT + "') ");
                    break;
                case STATUS:
                    hql.append("AND C.orderStatus LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CLIENT_ORDER_ID:
                    hql.append("AND C.clOrdID LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                case CUSTOMER_NUMBER:
                    hql.append("AND C.customerNumber LIKE '" + secondarySearchValue + WILD_CARD_TEXT + "' ");
                    break;
                default:
                    throw new OrderException("Not Implemented Order Search Criteria Found");

            }
        }

        //Limiting the orders depends on order created date
        if (fromDate != null && toDate != null) {
            hql.append("AND C.createDate BETWEEN :fromDate AND :toDate ");
        }

        //Query for filtering orders for dealer
        if (!isGlobalDealer) {
            //Hibernate query for filtering customers for given dealer
            String hqlDealerFilter = "( " +
                    "C.institutionCode IN (:INSTITUTIONS) " +
                    "OR C.customerNumber IN " +
                    "(SELECT DISTINCT IC.customerNo FROM lk.ac.ucsc.oms.user.implGeneral.beans.CustomerRepresentativeBean IC WHERE IC.allowedCustomer = 1 AND IC.userID = " + dealerId + ") " +
                    ") " +
                    "AND C.customerNumber NOT IN " +
                    "(SELECT DISTINCT EC.customerNo FROM lk.ac.ucsc.oms.user.implGeneral.beans.CustomerRepresentativeBean EC WHERE EC.allowedCustomer = 0 AND EC.userID = " + dealerId + ") ";
            hql.append("AND " + hqlDealerFilter);
        }

        //Query for ordering records by order Id
        hql.append("ORDER BY C.id DESC");

        return hql.toString();
    }

    /**
     * create hql query for load orders with given accountNumber, clOrdID, symbol,.... etc
     *
     * @param accountNumber for search
     * @param clOrdID       for search
     * @param symbol        for search
     * @param orderSide     for search
     * @param orderStatus   for search
     * @param startDate     for search
     * @param endDate       for search
     * @param exchange      for search
     * @return hqlQuery
     */
    public String createQueryForSearchOrders(String accountNumber, String clOrdID, String symbol, String orderSide, String orderStatus, Date startDate, Date endDate, String exchange) {
        StringBuilder hql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE ");

        hql.append("C.securityAccount = '" + accountNumber + "' ");

        if (clOrdID != null && !"".equals(clOrdID)) {
            hql.append("AND C.clOrdID = '" + clOrdID + "' ");
        }

        if (symbol != null && !"".equals(symbol)) {
            hql.append("AND C.symbol = '" + symbol + "' ");
        }

        if (orderSide != null && !"".equals(orderSide) && !"-1".equals(orderSide)) {
            hql.append("AND C.orderSide = '" + orderSide + "' ");
        }

        if (orderStatus != null && !"".equals(orderStatus)) {
            hql.append("AND C.orderStatus = '" + orderStatus + "' ");
        }

        if (exchange != null && !"".equals(exchange)) {
            hql.append("AND C.exchange = '" + exchange + "' ");
        }

        if (startDate != null && endDate != null) {
            hql.append("AND C.createDate BETWEEN :fromDate AND :toDate ");
        }

        hql.append("ORDER BY C.id ASC");

        return hql.toString();
    }


    /**
     * create hql query for load orders with given date range and institution or filter
     *
     * @param fromDate        for orders
     * @param toDate          of orders
     * @param institutionList of orders
     * @return hqlQuery
     */
    public String createQueryToGetOrdersByDatesAndInstitutions(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) {
        StringBuilder mainHql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C ");
        StringBuilder hql = new StringBuilder();
        StringBuilder institutionListStr = new StringBuilder();

        if ((institutionList != null) && (!institutionList.isEmpty())) {
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
            hql.append(" C.createDate BETWEEN :fromDate AND :toDate ");
        } else if (fromDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.createDate BETWEEN :fromDate AND :fromDate ");
        } else if (toDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.createDate BETWEEN :toDate AND :toDate ");
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

    /**
     * create hql query for load orders with given date range and institution or filter
     *
     * @param fromDate for orders
     * @param toDate   of orders
     * @param brokerId of orders
     * @return hqlQuery
     */
    public String createQueryToGetOrdersByBrokerDatesAndInstitutions(Date fromDate, Date toDate, Map<String, String> filters, String brokerId, String statusFilter) {
        StringBuilder mainHql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C ");
        StringBuilder hql = new StringBuilder();

        if (brokerId != null && !brokerId.isEmpty()) {
            hql.append(" C.execBrokerId = " + brokerId + " ");
        }
        if (statusFilter != null && !statusFilter.isEmpty()) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.orderStatus IN ( " + statusFilter + " ) ");
        }

        //Limiting the orders depends on order created date
        if (fromDate != null && toDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.createDate BETWEEN :fromDate AND :toDate ");
        } else if (fromDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.createDate BETWEEN :fromDate AND :fromDate ");
        } else if (toDate != null) {
            if (!hql.toString().isEmpty()) {
                hql.append(" AND ");
            }
            hql.append(" C.createDate BETWEEN :toDate AND :toDate ");
        }

        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if (hql.toString().isEmpty()) {
                    hql.append(" " + entry.getKey() + " = '" + entry.getValue() + "' ");
                } else {
                    hql.append(" OR " + entry.getKey() + " = '" + entry.getValue() + "' ");
                }
            }
        }
        if (!hql.toString().isEmpty()) {
            mainHql.append(" WHERE ");
            mainHql.append(hql);
        }
        return mainHql.toString();
    }

    public String createQueryForSearchParkedOrders(long dealer, Date fromDate, Date toDate) {
        StringBuilder hql = new StringBuilder("FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C ");
        hql.append("WHERE C.internalMatchStatus > 0 AND C.orderStatus IN('1','2','5','p') ");

        //Limiting the orders depends on order created date
        if (fromDate != null && toDate != null) {
            hql.append("AND C.createDate BETWEEN :fromDate AND :toDate ");
        }

        //Query for ordering records by order Id
        hql.append("ORDER BY C.id DESC");

        return hql.toString();
    }
}
