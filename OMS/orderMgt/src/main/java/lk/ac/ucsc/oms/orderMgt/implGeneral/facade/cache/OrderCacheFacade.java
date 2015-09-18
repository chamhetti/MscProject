package lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.beans.SearchReply;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.orderMgt.api.OrderSearchType;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderNotFoundException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderKeyBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.OrderHistoryPersister;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.OrderPersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class OrderCacheFacade {
    private static Logger logger = LogManager.getLogger(OrderCacheFacade.class);
    private SysLevelParaManager sysLevelParaManager;
    private CacheControllerInterface orderCacheController;
    private CacheControllerInterface orderKeyCacheController;
    private OrderPersister orderPersister;
    private OrderHistoryPersister orderHistoryPersister;
    private CacheLoadedState orderCacheLoadedState;
    private AbstractSequenceGenerator sequenceGenerator;

    /**
     * Method to set order cache controller for cache operations
     *
     * @param orderCacheController
     */
    public void setOrderCacheController(CacheControllerInterface orderCacheController) {
        this.orderCacheController = orderCacheController;
    }

    /**
     * Method to set order key cache controller for cache operations
     *
     * @param orderKeyCacheController
     */
    public void setOrderKeyCacheController(CacheControllerInterface orderKeyCacheController) {
        this.orderKeyCacheController = orderKeyCacheController;
    }

    /**
     * Method to set order persister to DB operations
     *
     * @param orderPersister
     */
    public void setOrderPersister(OrderPersister orderPersister) {
        this.orderPersister = orderPersister;
    }

    /**
     * Method to set order history persister to DB operations
     *
     * @param orderHistoryPersister
     */
    public void setOrderHistoryPersister(OrderHistoryPersister orderHistoryPersister) {
        this.orderHistoryPersister = orderHistoryPersister;
    }

    /**
     * Method to set order cache load status. It can be partially loaded or fully loaded
     *
     * @param orderCacheLoadedState available in spring configuration file.
     */
    public void setOrderCacheLoadedState(CacheLoadedState orderCacheLoadedState) {
        this.orderCacheLoadedState = orderCacheLoadedState;
    }



    /**
     * Method to initialize order cache.
     * Setting the last order Id
     * Loading orders to cache from DB depends on CacheLoadedState
     */
    public void initialize() throws OrderException {
        //get the last id of the customer table and set it to sequence generator for id generation
        sequenceGenerator.setSequenceNumber(orderPersister.getLastOrderId());
        //load all orders for cache base on cache level configuration
        loadAllOrders();

        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                orderCacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    /**
     * Method to load all orders into cache
     */
    private void loadAllOrders() throws OrderException {
        logger.info("Loading all orders into cache");
        try {
            if (orderCacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                orderCacheController.populateFullCache();
            } else {
                populateOrdersPreviousDay();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all orders: ", e);
            throw new OrderException("Issue occurred in loading all ordes", e);
        }
    }

    /**
     * Method to load all previous day orders into order key cache and order cache.
     */
    private void populateOrdersPreviousDay() throws OrderException {
        //getting all previous day orders using DB layer
        List<Order> orderList = orderPersister.preLoad();

        //add all previous day orders into order key cache
        try {
            addToOrderKeyCache(orderList);
        } catch (InvalidOrderException e) {
            logger.error("Error in adding order list to order key cache", e);
            throw new OrderException("Error in adding order list to order key cache", e);
        }
    }

    /**
     * add Order to the Cache
     *
     * @param ord order reference
     * @throws InvalidOrderException
     */
    public void createOrder(Order ord) throws OrderException {
        logger.info("Add given Order to the Cache, Order : " + ord);
        if (ord == null) {
            throw new InvalidOrderException("Order cannot be null");
        }
        if (ord.getClOrdID() == null || "".equals(ord.getClOrdID())) {
            throw new InvalidOrderException("Client Order ID cannot be null or empty");
        }
        logger.debug("Validation for the Order successful, Order :{}", ord.getClOrdID());
        OrderBean ob = (OrderBean) ord;
        String clOrdID = ob.getClOrdID();
        ob.setPrimaryKeyObject(clOrdID);
        try {

            orderCacheController.addToCache(ob);

        } catch (CacheNotFoundException e) {
            logger.error("Error in adding order to cache", e);
            throw new OrderException("Error in adding order to cache", e);
        }

    }

    /**
     * update Order in the Cache
     *
     * @param ord order reference
     * @throws InvalidOrderException
     */
    public void updateOrder(Order ord) throws OrderException {
        logger.info("Update given Order in the cache, Order : {}", ord);
        if (ord == null) {
            throw new InvalidOrderException("Order cannot be null");
        }
        if (ord.getClOrdID() == null || "".equals(ord.getClOrdID())) {
            throw new InvalidOrderException("Client Order ID cannot be null or empty");
        }
        logger.debug("Validation for the Order successful, Order : {}", ord);
        OrderBean ob = (OrderBean) ord;
        ob.setPrimaryKeyObject(ord.getClOrdID());
        try {
            orderCacheController.modifyInCache(ob);


            logger.info("Order successfully updated in the cache, Order : {}", ord.getClOrdID());
        } catch (CacheNotFoundException e) {
            logger.error("Error in updating order in the cache", e);
            throw new OrderException("Error in updating order in the cache", e);
        }

    }

    /**
     * find Order by Client Order ID
     *
     * @param clOrdId client order id
     * @return Order bean
     * @throws OrderNotFoundException,OrderException
     */
    public Order findOrder(String clOrdId) throws OrderException {
        logger.info("Finding OrderBean for the given ID : {}", clOrdId);
        if (clOrdId == null || "".equals(clOrdId)) {
            throw new InvalidOrderException("Primary Key(Client Order ID) cannot be null or empty");
        }
        logger.debug("Validation for the ID successful, ID : {}", clOrdId);
        OrderBean ob = new OrderBean();
        ob.setPrimaryKeyObject(clOrdId);
        OrderBean cob;
        try {
            cob = (OrderBean) orderCacheController.readObjectFromCache(ob);
        } catch (OMSException e) {
            logger.error("Error findOrder", e);
            throw new OrderNotFoundException("Cannot findOrder order with id:" + clOrdId, e);
        }
        logger.info("Finding Order for given ID successful, Order : {}", cob);
        return cob;
    }




    /**
     * get the client order id from order key cache when the remote client order id is given
     *
     * @param remoteClOrdID for which clOrdId should be retrieved
     * @return clOrdId
     * @throws InvalidOrderException
     */
    public String getClOrdIdByRemoteClOrderId(String remoteClOrdID) throws OrderException {
        logger.info("Find front office cl order id by remote clOrder id: ", remoteClOrdID);
        if (remoteClOrdID == null || "".equals(remoteClOrdID)) {
            throw new OrderException("Remote Client Order ID cannot be null or empty");
        }
        OrderKeyBean orderKey = new OrderKeyBean();
        orderKey.setPrimaryKeyObject(remoteClOrdID);
        OrderKeyBean orderKeyBean = null;
        String clOrdId;
        try {
            orderKeyBean = (OrderKeyBean) orderKeyCacheController.readObjectFromCache(orderKey);
        } catch (OMSException e) {
            logger.info("Error in getting order by remote clOrdId from cache Error Message -{}, Remote Order ID -{} ", e.getMessage(), remoteClOrdID);
        }
        if (orderKeyBean != null) {
            clOrdId = orderKeyBean.getClOrdID();
        } else {
            logger.info("Order Key Bean not found hence search from the database. Remote ClOrder ID - {}", remoteClOrdID);
            clOrdId = orderPersister.findClOrdIdByRemoteClOrdId(remoteClOrdID);
            logger.info("Front office ClOrder ID found - {}", clOrdId);
            //if the remote cl order id is not null then need to add to the order key cache
            if (clOrdId != null) {
                addToOrderKeyCache(clOrdId, remoteClOrdID);
            } else {
                logger.error("The Order Not Found with given Remote ClOrdId - {}", remoteClOrdID);
                throw new OrderException("The Order Not Found with given Remote ClOrdId");
            }
        }
        return clOrdId;
    }



    /**
     * get Order for given remote clOrdId
     *
     * @param remoteClOrdId for which Order should be retrieved
     * @return Order
     * @throws OrderException
     */
    public Order getOrderByRemoteClOrdId(String remoteClOrdId) throws OrderException {
        String clOrdID = getClOrdIdByRemoteClOrderId(remoteClOrdId);
        return findOrder(clOrdID);
    }



    /**
     * get the list of order for IOM which has given input parameters in method argument
     *
     * @param symbol
     * @param exchange
     * @param price
     * @param side
     * @return Order List
     */
    public List<Order> getOrdersForIOM(String symbol, String exchange, double price, OrderSide side, String routingAcc, String securityAccount) throws OrderException {
        persistAsBulk();
        return orderPersister.getOrdersForIOM(symbol, exchange, price, side, routingAcc, securityAccount);
    }

    /**
     * search orders for given primary and secondary search values in given order group
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @param secondarySearchFilter second search option
     * @param secondarySearchValue  value corresponding to above field
     * @param orderGroup            Order Group (Executed, Cancelled, Rejected, Open, etc.)
     * @param searchMode            whether include invalidated orders or not
     * @return fully populated Order list
     * @throws OrderException
     */
    public List<Order> searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter,
                                    String secondarySearchValue, Date fromDate, Date toDate, int orderGroup, int searchMode) throws OrderException {
        persistAsBulk();
        return orderPersister.searchOrders(primarySearchFilter, primarySearchValue, secondarySearchFilter, secondarySearchValue,
                fromDate, toDate, orderGroup, searchMode);
    }

    /**
     * search orders for given primary search values for XT
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @return fully populated Order list
     * @throws OrderException
     */
    public SearchReply searchOrdersXt(String execBrokerCode, int primarySearchFilter, String primarySearchValue,Date fromDate, Date toDate, boolean open, int startingSeqNumber, int maxPageWidth) throws OrderException {
        persistAsBulk();
        return orderPersister.searchOrdersXt(execBrokerCode, primarySearchFilter, primarySearchValue, fromDate, toDate, open, startingSeqNumber, maxPageWidth);
    }

    /**
     * search orders for given primary and secondary search values in given order group with dealer specific filtering and pagination
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @param secondarySearchFilter second search option
     * @param secondarySearchValue  value corresponding to above field
     * @param orderGroup            Order Group (Executed, Cancelled, Rejected, Open, etc.)
     * @param searchMode            whether include invalidated orders or not
     * @param dealerId
     * @param isGlobalDealer
     * @param startingSeqNumber
     * @param maxPageWidth
     * @return fully populated Order list
     * @throws OrderException
     */
    public SearchReply searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                                    Date fromDate, Date toDate, int orderGroup, int searchMode, String dealerId, boolean isGlobalDealer,
                                    int startingSeqNumber, int maxPageWidth) throws OrderException {
        persistAsBulk();
        return orderPersister.searchOrders(primarySearchFilter, primarySearchValue, secondarySearchFilter, secondarySearchValue,
                fromDate, toDate, orderGroup, searchMode, dealerId, isGlobalDealer, startingSeqNumber, maxPageWidth);
    }

    /**
     * search waiting for approval orders for given primary and secondary search values in given order group with dealer specific filtering and pagination
     *
     * @param fromDate              orders created on or after this date
     * @param toDate                orders created on or before this date
     * @param primarySearchFilter   first search option
     * @param primarySearchValue    value corresponding to above field
     * @param secondarySearchFilter second search option
     * @param secondarySearchValue  value corresponding to above field
     * @param dealerId
     * @param isGlobalDealer
     * @param startingSeqNumber
     * @param maxPageWidth
     * @return fully populated Order list
     * @throws OrderException
     */
    public SearchReply searchWaitingForApprovalOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter,
                                                      String secondarySearchValue, Date fromDate, Date toDate, String dealerId,
                                                      boolean isGlobalDealer, int startingSeqNumber, int maxPageWidth) throws OrderException {
        persistAsBulk();
        return orderPersister.searchWaitingForApprovalOrders(primarySearchFilter, primarySearchValue, secondarySearchFilter, secondarySearchValue,
                fromDate, toDate, dealerId, isGlobalDealer, startingSeqNumber, maxPageWidth);
    }

    /**
     * search parked orders for given primary and secondary search values in given order group with dealer specific filtering and pagination
     *
     * @param fromDate          orders created on or after this date
     * @param toDate            orders created on or before this date
     * @param dealerId
     * @param startingSeqNumber
     * @param maxPageWidth
     * @return fully populated Order list
     * @throws OrderException
     */
    public SearchReply searchParkedOrders(String dealerId, int startingSeqNumber, int maxPageWidth, Date fromDate, Date toDate) throws OrderException {
        persistAsBulk();
        return orderPersister.searchParkedOrders(dealerId, startingSeqNumber, maxPageWidth, fromDate, toDate);
    }

    /**
     * search order history for given mubasher order number
     *
     * @return fully populated Order list
     * @throws OrderException
     */
    public SearchReply searchOrderHistory(String dealerId, String orderNumber) throws OrderException {
        persistAsBulk();
        return orderPersister.searchOrderHistory(dealerId, orderNumber);
    }


    /**
     * search conflicting orders for given primary and secondary search values in given order group with dealer specific filtering and pagination
     *
     * @param startingSeqNumber
     * @param maxPageWidth
     * @param clOrdId
     * @return fully populated Order list
     * @throws OrderException
     */
    public SearchReply searchConflictingOrders(int startingSeqNumber, int maxPageWidth, String clOrdId) throws OrderException {
        persistAsBulk();
        return orderPersister.searchConflictingOrders(startingSeqNumber, maxPageWidth, clOrdId);
    }



    /**
     * get the pending order list for a given customer security account
     *
     * @param accountNumber of the security account used for search
     * @return list {@inheritDoc}
     * @throws OrderException
     */
    public List<Order> findPendingOrders(String accountNumber) throws OrderException {
        persistAsBulk();
        return orderPersister.findPendingOrders(accountNumber);
    }

    /**
     * Gets the list of all orders.
     *
     * @param fromDate        child orders created on or after this date
     * @param toDate          child orders created on or before this date
     * @param institutionList institution List
     * @return List of all orders.
     * @throws OrderException
     */
    public List<Order> getOrderList(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws OrderException {
        persistAsBulk();
        return orderPersister.getAllOrders(fromDate, toDate, institutionList, filters);
    }

    /**
     * Gets the list of all orders for specific broker.
     *
     * @param fromDate child orders created on or after this date
     * @param toDate   child orders created on or before this date
     * @param brokerId brokerId
     * @return List of all orders.
     * @throws OrderException
     */
    public List<Order> getOrderListForBroker(Date fromDate, Date toDate, Map<String, String> filters, String brokerId, String statusFilter) throws OrderException {
        persistAsBulk();
        return orderPersister.getAllOrdersForBroker(fromDate, toDate, filters, brokerId, statusFilter);
    }

    /**
     * Get the cancelled orders for the eod process
     *
     * @param exchange   is the exchange
     * @param marketCode is the market code
     * @return the cancelled order list
     * @throws OrderException
     */
    public List<Order> findCancelledOrders(String exchange, String marketCode) throws OrderException {
        persistAsBulk();
        return orderPersister.findCancelledOrders(exchange, marketCode);
    }

    /**
     * Get the exec broker wise cancelled orders for the eod process
     *
     * @param exchange   is the exchange
     * @param marketCode is the market code
     * @return the cancelled order list
     * @throws OrderException
     */
    public List<Order> findExeBrkWiseCancelledOrders(String exchange, String marketCode, String execBrokerId) throws OrderException {
        persistAsBulk();
        return orderPersister.findExecBrkWiseCancelledOrders(exchange, marketCode, execBrokerId);
    }

    /**
     * Find the list of Desk Child orders
     *
     * @param deskOrderRef of the order
     * @return list {@inheritDoc}
     * @throws OrderException
     */
    public List<Order> findOrderListWithDeskOrderReference(String deskOrderRef) throws OrderException {
        persistAsBulk();
        return orderPersister.findOrderListWithDeskOrderReference(deskOrderRef);
    }

    /**
     * Find the order list of conditional triggered orders
     *
     * @param conditionalOrderRef of the order
     * @return list {@inheritDoc}
     * @throws OrderException
     */
    public List<Order> findOrderListWithConditionalOrderReference(String conditionalOrderRef) throws OrderException {
        persistAsBulk();
        return orderPersister.findOrderListWithConditionalOrderReference(conditionalOrderRef);
    }

    /**
     * Find the list of Desk Child orders from DB to update the Desk order reference when amending master order
     *
     * @param deskOrderRef of the order
     * @param orderSide    of the order
     * @return list {@inheritDoc}
     * @throws OrderException
     */
    public List<Order> findOpenOrderListWithDeskOrderReference(String deskOrderRef, OrderSide orderSide) throws OrderException {
        persistAsBulk();
        return orderPersister.findOpenOrderListWithDeskOrderReference(deskOrderRef, orderSide);
    }

    /**
     * Calculates cumulative net settle value of day orders for mark for delivery calculation
     *
     * @param accountNumber is the customer account number
     * @param exchange      exchange
     * @param symbol        symbol
     * @return calculated value
     * @throws OrderException
     */
    public double calculateDayCumOrderNetSettle(String accountNumber, String exchange, String symbol) throws OrderException {
        persistAsBulk();
        return orderPersister.calculateDayCumOrderNetSettle(accountNumber, exchange, symbol);
    }

    /**
     * search orders for given field values, account number is a must to search order details
     * other parameters can be specified or can be null or empty
     *
     * @param accountNumber for search
     * @param clOrdID       of the order
     * @param symbol        of the order
     * @param orderSide     of the order
     * @param orderStatus   of the order
     * @param startDate     of the order
     * @param endDate       of the order
     * @param exchange      of the order
     * @return fully populated Order list
     * @throws OrderException
     */
    public List<Order> searchOrders(String accountNumber, String clOrdID, String symbol, String orderSide, String orderStatus,
                                    Date startDate, Date endDate, String exchange) throws OrderException {
        persistAsBulk();
        return orderPersister.searchOrders(accountNumber, clOrdID, symbol, orderSide, orderStatus, startDate, endDate, exchange);
    }

    /**
     * Get the order list for pre open process
     *
     * @param exchange   is the exchange
     * @param marketCode is the market code
     * @return the oms accepted order to send in the pre open process
     * @throws OrderException
     */
    public List<Order> findPreOpenOrderList(String exchange, String marketCode) throws OrderException {
        persistAsBulk();
        return orderPersister.findPreOpenOrderList(exchange, marketCode);
    }

    /**
     * Get the all the open orders
     *
     * @param exchange   is the exchange
     * @param marketCode is the market code
     * @return the open orders
     * @throws OrderException
     */
    public List<Order> findOpenOrders(String exchange, String marketCode) throws OrderException {
        persistAsBulk();
        return orderPersister.findOpenOrders(exchange, marketCode);
    }


    /**
     * Get the all the open orders exec broker wise
     *
     * @param exchange   is the exchange
     * @param marketCode is the market code
     * @return the open orders
     * @throws OrderException
     */
    public List<Order> findExecBrkWiseOpenOrders(String exchange, String marketCode, String execBrokerId) throws OrderException {
        persistAsBulk();
        return orderPersister.findExecBrkWiseOpenOrders(exchange, marketCode, execBrokerId);
    }

    /**
     * search From order History table for given value equal to given search type
     *
     * @param searchType field to be searched
     * @param value      value of the field
     * @return History Order List
     */
    public List<Order> filterOrderHistoryByFieldAndValue(OrderSearchType searchType, String value) throws OrderException {
        return orderHistoryPersister.filterOrdersByFieldAndValue(searchType, value);
    }

    /**
     * search From order History table  for given value equal to given  all search types  set in Map
     * Based on SearchType1=value1 && SearchType2=value2 && SearchType3=value3 &&....
     *
     * @param searchTypeValue map for search
     * @return History Order List
     */
    public List<Order> filterOrderHistoryForAllGivenTypeValues(Map<OrderSearchType, String> searchTypeValue) throws OrderException {
        return orderHistoryPersister.filterOrdersForAllGivenTypeValues(searchTypeValue);
    }

    /**
     * search From order History table  for given  either value equal to given  search types  set in Map
     * Based on SearchType1=value1 || SearchType2=value2 || SearchType3=value3 ||...   search types and values set in map
     *
     * @param searchTypeValue map for search
     * @return History Order List
     */
    public List<Order> filterOrderHistoryForEitherGivenTypeValues(Map<OrderSearchType, String> searchTypeValue) throws OrderException {
        return orderHistoryPersister.filterOrdersForEitherGivenTypeValues(searchTypeValue);
    }

    /**
     * method to invoke bulk persist method of cache controller to persist cache entries as a bulk
     */
    public void persistAsBulk() throws OrderException {
        logger.debug("Bulk persist of order cache is temporary disabled");
    }

    /**
     * add retrieved order data from database into the cache
     *
     * @param orderList to be added to order key cache
     */
    private void addToOrderKeyCache(List<Order> orderList) throws OrderException {
        logger.info("Add the order list to Order Key cache, list - ", orderList);
        if (orderList == null) {
            throw new InvalidOrderException("order list cannot be null");
        }

    }

    /**
     * add client order id and remote client order id to the order key cache as order key bean
     *
     * @param clOrdId
     * @param remoteClOrdId
     */
    private void addToOrderKeyCache(String clOrdId, String remoteClOrdId) throws OrderException {
        if (remoteClOrdId == null || "".equalsIgnoreCase(remoteClOrdId) || "-1".equalsIgnoreCase(remoteClOrdId) || clOrdId == null || "".equalsIgnoreCase(clOrdId) || "-1".equalsIgnoreCase(clOrdId)) {
            logger.error("OrderKey bean clOrdId or remoteClOrdId is null or empty or invalid");
            throw new OrderException("OrderKey bean clOrdId or remoteClOrdId is null or empty or invalid");
        }
        OrderKeyBean orderKey = new OrderKeyBean();
        orderKey.setPrimaryKeyObject(remoteClOrdId);
        orderKey.setClOrdID(clOrdId);
        orderKey.setRemoteClOrdID(remoteClOrdId);
        try {
            orderKeyCacheController.addToCache(orderKey);
        } catch (CacheNotFoundException e) {
            logger.error("Error in adding Order Key to the cache: ", e);
            throw new OrderException("Error in adding Order Key to the cache: ", e);
        }
        logger.info("Order Key Successfully add to the cache: ", orderKey.getClOrdID());
    }




    /**
     * Method will check the remote client order id is exist in the system
     * First remote order id will be search in the order key cache, if not found there then it will check in the
     * database as well. If the remote clord id not found return status will be false
     *
     * @param remoteClOrdId is the remote client order id
     * @throws OrderException
     */
    private boolean isRemoteClOrdIDExist(String remoteClOrdId) throws OrderException {
        logger.info("Check Remote ClOrder ID  exist -{}", remoteClOrdId);
        if (remoteClOrdId == null || "".equals(remoteClOrdId)) {
            throw new OrderException("Remote Client Order ID cannot be null or empty");
        }
        boolean status = false;

        OrderKeyBean orderKey = new OrderKeyBean();
        orderKey.setPrimaryKeyObject(remoteClOrdId);
        OrderKeyBean orderKeyBean = null;
        String clOrdId;
        try {
            orderKeyBean = (OrderKeyBean) orderKeyCacheController.readObjectFromCache(orderKey);
        } catch (OMSException e) {
            logger.info("Error in getting order by remote clOrdId from cache. Remote order id not exist in the cache");
        }
        if (orderKeyBean != null) {
            logger.debug("Remote ClOrd ID Already Exist in the System. Order request has to be reject");
            return true;
        } else {
            clOrdId = orderPersister.findClOrdIdByRemoteClOrdId(remoteClOrdId);
            //if the remote cl order id is not null then need to add to the order key cache
            if (clOrdId != null) {
                logger.debug("Remote ClOrd ID Already Exist in the System. Order request has to be reject");
                return true;
            }
        }
        return status;
    }

    /**
     * Method will find latest order by the order number
     *
     * @param orderNo is the order number
     * @return the Order
     * @throws OrderException
     */
    public Order findOrderByOrderNo(String orderNo) throws OrderException {
        persistAsBulk();
        return orderPersister.findOrderByOrderNo(orderNo);
    }

    /**
     * Method will find the order using the broker fix id (fix client id)  and the Remote Clord ID
     *
     * @param brokerFIXID   is the fix client id
     * @param remoteClordID is the original remote clord id
     * @return Order
     * @throws OrderException
     */
    public Order findOrderByBrokerFIXID(String brokerFIXID, String remoteClordID) throws OrderException {
        persistAsBulk();
        return orderPersister.findOrderByBrokerFIXID(brokerFIXID, remoteClordID);
    }


    /**
     * This method is used to get Client Order Ids of child orders for given desk order
     *
     * @param deskOrdReference
     * @throws OrderException
     */
    public List<String> getClOrdIdListByDeskOrdRef(String deskOrdReference) throws OrderException {
        persistAsBulk();
        return orderPersister.findClOrdIdListByDeskOrdRef(deskOrdReference);
    }

    public List<Order> getPreOpenOrderListByExecutionBrokerId(String exchange, String marketCode, String executionBrokerId) throws OrderException {
        persistAsBulk();
        return orderPersister.getPreOpenOrderListByExecutionBrokerId(exchange, marketCode, executionBrokerId);
    }

    /**
     * Method to get eod portfolio number list
     *
     * @param eodDate
     * @return
     * @throws OrderException
     */
    public List<String> getEODTPlusPortfolioList(String eodDate) throws OrderException {
        persistAsBulk();
        return orderPersister.getEODTPlusPortfolioList(eodDate);
    }

    /**
     * Method to get eod symbol list for each account
     *
     * @param accountNumber
     * @param eodDate
     * @return
     * @throws OrderException
     */
    public List<String> getEODTPlusSymbolList(String accountNumber, String eodDate) throws OrderException {
        persistAsBulk();
        return orderPersister.getEODTPlusSymbolList(accountNumber, eodDate);
    }

    /**
     * Method to get eod t plus order list for given acc and symbol
     *
     * @param accountNumber
     * @param symbol
     * @param eodDate
     * @return
     * @throws OrderException
     */
    public List<Order> getEODTPlusOrderList(String accountNumber, String symbol, String eodDate) throws OrderException {
        persistAsBulk();
        return orderPersister.getEODTPlusOrderList(accountNumber, symbol, eodDate);
    }

    /**
     * This method return the cumulative day order net settle value
     *
     * @param securityAccId is the security account id
     * @param exchange      is the security exchange
     * @param symbol        is the symbol
     * @return calculated value
     */
    public double getDayCumOrdNetSettle(String securityAccId, String exchange, String symbol) {
        return orderPersister.getDayCumOrdNetSettle(securityAccId, exchange, symbol);
    }


    /**
     * Method to get multi NIN child orders by MultiNIN Order Reference
     *
     * @param multiNINOrderRef
     * @return
     * @throws OrderException
     */
    public List<Order> getChildOrdListByMultiNINOrdRef(String multiNINOrderRef) throws OrderException {
        persistAsBulk();
        return orderPersister.findOpenOrderListWithMultiNINOrderReference(multiNINOrderRef);
    }

    /**
     * Method to get time trigger order list to send to the MRE
     *
     * @return
     * @throws OrderException
     */
    public List<Order> getTimeTriggerEnableOrderList() throws OrderException {
        persistAsBulk();
        return orderPersister.findTimeTriggerOrderList();
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
}
