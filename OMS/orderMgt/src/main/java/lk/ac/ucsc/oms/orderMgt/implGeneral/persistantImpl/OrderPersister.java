package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.common_utility.api.beans.SearchReply;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface OrderPersister extends CachePersister {
    /**
     * get the last Client Order ID from the Order Table
     *
     * @return max ClordID
     */
    String getLastOrderId();

    /**
     * load all the previous day orders from the database
     *
     * @return Order list
     */
    List<Order> preLoad() throws OrderException;

    /**
     * search orders for given two criteria from the database
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @param secondarySearchFilter second search option
     * @param secondarySearchValue  value corresponding to above field
     * @return order list
     */
    List<Order> searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                             Date fromDate, Date toDate, int orderGroup, int searchMode) throws OrderException;


    /**
     * search orders for given two criteria from the database
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @return order list
     */
    SearchReply searchOrdersXt(String execBrokerCode, int primarySearchFilter, String primarySearchValue, Date fromDate, Date toDate, boolean open, int startingSeqNumber, int maxPageWidth) throws OrderException;

    /**
     * search orders for given two criteria from the database
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @param secondarySearchFilter second search option
     * @param secondarySearchValue  value corresponding to above field
     * @return order list
     */
    SearchReply searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                             Date fromDate, Date toDate, int orderGroup, int searchMode, String dealerId, boolean isGlobalDealer,
                             int startingSeqNumber, int maxPageWidth) throws OrderException;

    /**
     * search waiting for approval orders for given two criteria from the database
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @param secondarySearchFilter second search option
     * @param secondarySearchValue  value corresponding to above field
     * @return order list
     */
    SearchReply searchWaitingForApprovalOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter,
                                               String secondarySearchValue, Date fromDate, Date toDate, String dealerId,
                                               boolean globalDealer, int startingSeqNumber, int maxPageWidth) throws OrderException;


    /**
     * retrieve pending orders for given security account
     *
     * @param accountNumber to be search
     * @return order list
     */
    List<Order> findPendingOrders(String accountNumber) throws OrderException;

    /**
     * load orders from database which has given accountNumber, clOrdID, symbol, orderSide, ... etc as a list
     *
     * @param accountNumber for orders
     * @param clOrdID       of orders
     * @param symbol        of orders
     * @param orderSide     of orders
     * @param orderStatus   of orders
     * @param startDate     for search criteria
     * @param endDate       for search criteria
     * @param exchange      of orders
     * @return list of orders
     */
    List<Order> searchOrders(String accountNumber, String clOrdID, String symbol, String orderSide, String orderStatus, Date startDate, Date endDate, String exchange) throws OrderException;

    /**
     * find orders which are oms accepted and of given exchange, given market code and with channelCode manual
     *
     * @param exchange   for which orders should be searched
     * @param marketCode for which orders should be searched
     * @return list of orders
     */
    List<Order> findPreOpenOrderList(String exchange, String marketCode) throws OrderException;

    /**
     * find open orders which have below properties
     * <p/>
     * year of expire time = current year
     * month of expire time = current month
     * day of expire time = current day
     * order status is any of '0','5','R','9','L','1','Q','O','p' and time in force <> 1
     * order status is 'c'
     * <p/>
     * and is of given exchange and with given market code
     *
     * @param exchange   for search criteria
     * @param marketCode for search criteria
     * @return list of orders
     */
    List<Order> findOpenOrders(String exchange, String marketCode) throws OrderException;

    /**
     * find open orders which have below properties
     * <p/>
     * year of expire time = current year
     * month of expire time = current month
     * day of expire time = current day
     * execBrokerId = sell side broker of the order
     * order status is any of '0','5','R','9','L','1','Q','O','p' and time in force <> 1
     * order status is 'c'
     * <p/>
     * and is of given exchange and with given market code
     *
     * @param exchange   for search criteria
     * @param marketCode for search criteria
     * @return list of orders
     */
    List<Order> findExecBrkWiseOpenOrders(String exchange, String marketCode, String execBrokerId) throws OrderException;

    /**
     * find cancel orders with below given criteria
     * <p/>
     * year of expire time = current year
     * month of expire time = current month
     * day of expire time = current day
     * order status = '4'
     * cumulative quantity > 0
     * <p/>
     * and is of given exchange with given market code
     *
     * @param exchange   for search
     * @param marketCode for search
     * @return order list
     */
    List<Order> findCancelledOrders(String exchange, String marketCode) throws OrderException;

    /**
     * find cancel orders with below given criteria
     * <p/>
     * year of expire time = current year
     * month of expire time = current month
     * day of expire time = current day
     * order status = '4'
     * execBrokerId =10
     * cumulative quantity > 0
     * <p/>
     * and is of given exchange with given market code
     *
     * @param exchange     for search
     * @param marketCode   for search
     * @param execBrokerId for search
     * @return order list
     */
    List<Order> findExecBrkWiseCancelledOrders(String exchange, String marketCode, String execBrokerId) throws OrderException;

    /**
     * Find the desk child orders for given desk order reference
     *
     * @param deskOrderRef for search
     * @return list of orders
     */
    List<Order> findOrderListWithDeskOrderReference(String deskOrderRef) throws OrderException;

    /**
     * Find the conditional triggered orders for given conditional order reference
     *
     * @param conditionalOrderRef for search
     * @return list of orders
     */
    List<Order> findOrderListWithConditionalOrderReference(String conditionalOrderRef) throws OrderException;

    /**
     * Find the Desk Child orders to update the desk order reference
     *
     * @param deskOrderRef for search
     * @param orderSide    for search
     * @return list of orders
     */
    List<Order> findOpenOrderListWithDeskOrderReference(String deskOrderRef, OrderSide orderSide) throws OrderException;

    /**
     * get orders with given symbol, exchange, price,..... etc with order status of NEW,PARTIALLY_FILLED,REPLACED,OMS_ACCEPTED or
     * PROCESSING
     *
     * @param symbol
     * @param exchange
     * @param price
     * @param side
     * @param routingAcc
     * @param securityAccount
     * @return list {@link}
     */
    List<Order> getOrdersForIOM(String symbol, String exchange, double price, OrderSide side, String routingAcc, String securityAccount) throws OrderException;

    /**
     * method to find client order id when remote client order id is provided
     *
     * @param remoteClOrdId
     * @return clOrdId
     */
    String findClOrdIdByRemoteClOrdId(String remoteClOrdId) throws OrderException;

    /**
     * method to find client order id when back office order id is provided
     *
     * @param backOfficeOrderId
     * @return clOrdId
     */
    String findClOrdIdByBackOfficeOrderId(String backOfficeOrderId) throws OrderException;

    /**
     * method to calculate day cumulative order net settle
     *
     * @param customerNumber
     * @param exchange
     * @param symbol
     * @return double
     */
    double calculateDayCumOrderNetSettle(String customerNumber, String exchange, String symbol) throws OrderException;


    /**
     * load orders from database filtering for provided from date ,to date and institution list.
     * if both dates are null date filter won't be applied
     *
     * @param fromDate        for orders
     * @param toDate          of orders
     * @param institutionList of orders
     * @return list of orders
     */
    List<Order> getAllOrders(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws OrderException;

    /**
     * load orders from database filtering for provided broker ,from date ,to date and institution list .
     * if both dates are null date filter won't be applied
     *
     * @param fromDate for orders
     * @param toDate   of orders
     * @param brokerId of orders
     * @return list of orders
     */
    List<Order> getAllOrdersForBroker(Date fromDate, Date toDate, Map<String, String> filters, String brokerId, String statusFilter) throws OrderException;

    /**
     * find clOrdId by master order id
     *
     * @param masterOrdId
     * @return
     * @throws OrderException
     */
    List<String> findClOrdIdListByMasterOrdId(String masterOrdId) throws OrderException;

    /**
     * Find the order by order number
     *
     * @param orderNo is the order number
     * @return Order
     * @throws OrderException
     */
    Order findOrderByOrderNo(String orderNo) throws OrderException;

    /**
     * find clOrdIds by desk order reference
     *
     * @param deskOrdReference
     * @throws OrderException
     */
    List<String> findClOrdIdListByDeskOrdRef(String deskOrdReference) throws OrderException;

    /**
     * Method will find the order using the broker fix id (fix client id)  and the Remote Clord ID
     *
     * @param brokerFIXID   is the fix client id
     * @param remoteClordID is the original remote clord id
     * @return Order
     * @throws OrderException
     */
    Order findOrderByBrokerFIXID(String brokerFIXID, String remoteClordID) throws OrderException;

    /**
     * This method return the cumulative day order net settle value
     *
     * @param securityAccId is the security account id
     * @param exchange      is the security exchange
     * @param symbol        is the symbol
     * @return calculated value
     */
    double getDayCumOrdNetSettle(String securityAccId, String exchange, String symbol);

    /**
     * search parked orders for given criteria from the database
     *
     * @param fromDate orders created on or after this date
     * @param toDate   orders created on or before this date
     * @return order list
     */
    SearchReply searchParkedOrders(String dealerId, int startingSeqNumber, int maxPageWidth, Date fromDate, Date toDate) throws OrderException;

    /**
     * search order history for given mubasher order number
     *
     * @return fully populated Order list
     * @throws OrderException
     */
    SearchReply searchOrderHistory(String dealerId, String orderNumber) throws OrderException;

    /**
     * search conflicting orders for given criteria from the database
     *
     * @return order list
     */
    SearchReply searchConflictingOrders(int startingSeqNumber, int maxPageWidth, String clOrdId) throws OrderException;

    /**
     * get pre open order list by execution broker id
     *
     * @param executionBrokerId
     * @return
     * @throws OrderException
     */
    List<Order> getPreOpenOrderListByExecutionBrokerId(String exchange, String marketCode, String executionBrokerId) throws OrderException;

    /**
     * get eod portfoliio list from data source
     *
     * @param eodDate
     * @return
     */
    List<String> getEODTPlusPortfolioList(String eodDate) throws OrderException;

    /**
     * get eod symbol list for each given portfolio
     *
     * @param portfolioNumber
     * @param eodDate
     * @return
     */
    List<String> getEODTPlusSymbolList(String portfolioNumber, String eodDate) throws OrderException;

    /**
     * Method to get t plus order list for acc num and symbol
     *
     * @param portfolioNumber
     * @param symbol
     * @param eodDate
     * @return
     */
    List<Order> getEODTPlusOrderList(String portfolioNumber, String symbol, String eodDate) throws OrderException;

    /**
     * Find the MultiNIN Child orders with multiNIN Order reference
     *
     * @param multiNINOrderRef for search
     * @return list of orders
     */
    List<Order> findOpenOrderListWithMultiNINOrderReference(String multiNINOrderRef) throws OrderException;

    /**
     * Find the time trigger order list to send to the MRE
     *
     * @return list of orders
     */
    List<Order> findTimeTriggerOrderList() throws OrderException;

}
