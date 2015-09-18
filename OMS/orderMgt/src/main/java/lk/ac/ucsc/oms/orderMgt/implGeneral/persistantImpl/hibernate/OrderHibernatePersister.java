package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.beans.SearchReply;
import lk.ac.ucsc.oms.common_utility.api.constants.OrderSearchFilter;
import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.common_utility.implGeneral.beans.SearchReplyBean;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.OrderPersister;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate.utility.OrderSearchHQLGenerator;
import lk.ac.ucsc.oms.symbol.api.SymbolPriceManager;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolPriceData;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import com.google.gson.Gson;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

public class OrderHibernatePersister extends AbstractCachePersister implements OrderPersister {
    public static final String RETRIEVING_ORDERS_SUCCESSFUL = "Retrieving orders successful";
    public static final String EXPIRETIME_HQL_QUARY = "AND year(C.expireTime) = year(current_date) AND month(C.expireTime) =  month(current_date) AND day(C.expireTime) <=  day(current_date)";
    private static final int BATCH_SIZE = 20;
    private static final String ORDER_NOT_FOUND_ERROR = "Order Not Found";
    private static Logger logger = LogManager.getLogger(OrderHibernatePersister.class);
    private static Date lastUpdateTime;
    private SessionFactory searchSessionFactory;
    private OrderSearchHQLGenerator orderSearchHQLGenerator;
    private SymbolPriceManager symbolPriceManager;
    private ErrorOrderPersister errorOrderPersister;

    /**
     * set the real time and history session factories
     *
     * @param realTime session factory
     * @param history  session factory
     */
    public OrderHibernatePersister(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }



    /**
     * set order query generator
     *
     * @param orderSearchHQLGenerator for persister
     */
    public void setOrderSearchHQLGenerator(OrderSearchHQLGenerator orderSearchHQLGenerator) {
        this.orderSearchHQLGenerator = orderSearchHQLGenerator;
    }

    /**
     * set SessionFactory for dealer related order search
     *
     * @param searchSessionFactory for persister
     */
    public void setSearchSessionFactory(SessionFactory searchSessionFactory) {
        this.searchSessionFactory = searchSessionFactory;
    }

    /**
     * set SymbolPriceManager
     *
     * @param symbolPriceManager for persister
     */
    public void setSymbolPriceManager(SymbolPriceManager symbolPriceManager) {
        this.symbolPriceManager = symbolPriceManager;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(CacheObject co) throws OrderException {
        if (co == null) {
            throw new OrderException("The Order to update is null");
        }
        logger.info("Update OrderBean in database: {}", ((OrderBean) co).getClOrdID());
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.info("Updating the Database successful, OrderBean : " + co.getPrimaryKeyObject());
        } catch (ConstraintViolationException ce) {
            logger.error("Constraint violation error - {}", ce);
            OrderBean ob = (OrderBean) co;
            logger.error("Constraint violation exception occurred in update of order - {}. Hence Update the order bean.", ob.getClOrdID());
            updateOrderOnDB(ob);
        } catch (Exception e) {
            handleFailedCacheObject(co);
            logger.error("Order Bean Can't Update, Error : " + e.getMessage(), e);
            throw new OrderException("Order bean can't update", e);
        } finally {
            session.close();
        }
    }

    public Order updateOrder(Order order) throws OrderException {
        if (order == null) {
            throw new OrderException("The Order to update is null");
        }
        OrderBean ob = (OrderBean) order;
        logger.info("Update OrderBean in database: {}, Order Status - {}, Order Unique Id - {}", ob.getClOrdID(), ob.getStatus().getCode(), ob.getId());
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(order);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("Updating the Database successful, OrderBean : " + order.getClOrdID());
        } catch (ConstraintViolationException ce) {
            logger.error("Constraint violation error - {}", ce);
            logger.error("Constraint violation exception occurred in update of order - {}. Hence Update the order bean.", ob.getClOrdID());
            return updateOrderOnDB(ob);
        } catch (Exception e) {
            logger.error("Error in updating the order in database : {}, Error - {}", ob.getClOrdID(), e);
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws OrderException {
        if (co == null) {
            throw new OrderException("The Order to insert is null");
        }
        logger.debug("Insert Order to the database: " + ((OrderBean) co).getClOrdID());
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            OrderBean ob = (OrderBean) co;
            tx = session.beginTransaction();
            session.save(ob);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("Inserting to the Database successful, OrderBean : " + ob.getClOrdID());
        } catch (ConstraintViolationException ce) {
            logger.error("Constraint violation error - {}", ce);
            OrderBean ob = (OrderBean) co;
            logger.error("Constraint violation exception occurred in insert of order - {}. Hence Update the order bean.", ob.getClOrdID());
            updateOrderOnDB(ob);
            //persist the order to the error order table
            try {
                errorOrderPersister.insert(ob);
            } catch (OMSException e) {
                logger.error("error while persisting order to the error table - {}", e);
            }
        } catch (Exception e) {
            handleFailedCacheObject(co);
            logger.error("problem in adding order", e);
            throw new OrderException("problem in adding order", e);
        } finally {
            session.close();
        }
    }

    /**
     * This method is to update the contraint violated orders to the cache
     *
     * @param ob
     * @throws OrderException
     */
    private Order updateOrderOnDB(OrderBean ob) throws OrderException {
        logger.info("Updating the constraint violated order in the DB: {}", ob.getClOrdID());
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        OrderBean updatedOrder = null;
        try {
            tx = session.beginTransaction();
            OrderBean orderBean = (OrderBean) load(ob);
            if (orderBean != null) {
                updatedOrder = populateOrderFromNew(orderBean, ob);
                session.update(updatedOrder);
            } else {
                session.update(ob);
            }
            tx.commit();
            logger.info("Updating the constraint violated order in the Database successful, OrderBean : " + ob.getPrimaryKeyObject());
        } catch (ConstraintViolationException ce) {
            logger.error("Constraint violation error in updateOrderOnDB - {}", ce);
        } catch (Exception e) {
            handleFailedCacheObject(ob);
            logger.error("Order Bean Can't Update, Error : " + e.getMessage(), e);
            throw new OrderException("Order bean can't update", e);
        } finally {
            session.close();
        }
        return updatedOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws OrderException {
        logger.info("Get given OrderBean from the Database");
        if (co == null || co.getPrimaryKeyObject() == null || "".equals(co.getPrimaryKeyObject())) {
            logger.debug("Validation fails OrderBean");
            throw new OrderException("The Order to load is null");
        }
        OrderBean result = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.clOrdID = :Clord_id";
            Query query = session.createQuery(obhql);
            query.setParameter("Clord_id", co.getPrimaryKeyObject().toString());
            result = (OrderBean) query.uniqueResult();
            if (result != null) {
                result.setPrimaryKeyObject(result.getClOrdID());
            }
            logger.info("Retrieving Order successful, Client Order ID : " + co.getPrimaryKeyObject());
        } catch (Exception e) {
            logger.debug(ORDER_NOT_FOUND_ERROR, e);
            throw new OrderException("Order Not Found", e);
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Load all Orders from the Database");
        List<CacheObject> cashObjectLst = new ArrayList<CacheObject>();
        Session session = realTimeSessionFactory.openSession();
        String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean";
        Query query = session.createQuery(obhql);
        List results = query.list();
        try {
            for (Object ob : results) {
                OrderBean order = (OrderBean) ob;
                cashObjectLst.add(order);
            }
        } catch (Exception e) {
            logger.error("problem in loading all orders- {}", e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(CacheObject co) throws OMSException {
        logger.info("Remove OrderBean from the Database");
        if (co == null || co.getPrimaryKeyObject() == null || "".equals(co.getPrimaryKeyObject())) {
            logger.debug("Validation for given OrderBean fails");
            throw new OrderException("The order to remove can't be null");
        }
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.clOrdID = :clOrd_id";
            Query query = session.createQuery(hql);
            query.setParameter("clOrd_id", co.getPrimaryKeyObject().toString());
            OrderBean ob = (OrderBean) query.list().get(0);
            session.delete(ob);
            tx.commit();
            logger.debug("OrderBean successfully removed");
        } catch (Exception e) {
            logger.error("Error in remove order", e);
            throw new OrderException("Error in remove order", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Order> preLoad() throws OrderException {
        logger.info("Load all Orders of previous Day from the Database");
        Session session = realTimeSessionFactory.openSession();
        final int dayDif = -1;
        Calendar cal = Calendar.getInstance();
        Date endDate = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, dayDif);
        Date startDate = cal.getTime();
        List<Order> cashObjectLst = new ArrayList<Order>();

        try {
            Criteria criteria = session.createCriteria(OrderBean.class)
                    .add(Restrictions.between("createDate", startDate, endDate));
            List results = criteria.list();

            for (Object ob : results) {
                OrderBean order = (OrderBean) ob;
                cashObjectLst.add(order);
            }
        } catch (Exception e) {
            logger.error("Error in loading previous day orders" + e.getMessage(), e);
            throw new OrderException("Error in loading previous day orders", e);
        } finally {
            session.close();
        }

        return cashObjectLst;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter,
                                    String secondarySearchValue, Date fromDate, Date toDate, int orderGroup, int searchMode) throws OrderException {
        logger.info("Load Orders filtered from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        List<Order> orderLst = new ArrayList<Order>();
        hqlQuery = orderSearchHQLGenerator.createQueryForSearchOrders(fromDate, toDate, primarySearchFilter, primarySearchValue,
                secondarySearchFilter, secondarySearchValue, orderGroup, searchMode, -1, true);
        try {
            Query query = session.createQuery(hqlQuery);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }

            List results = query.list();

            for (Object ob : results) {
                OrderBean order = (OrderBean) ob;
                orderLst.add(order);
            }
        } catch (Exception e) {
            logger.error("Error in searching orders", e);
            throw new OrderException("Error in searching orders", e);
        } finally {
            session.close();
        }
        return orderLst;
    }

    @Override
    public SearchReply searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                                    Date fromDate, Date toDate, int orderGroup, int searchMode, String dealerId, boolean isGlobalDealer, int startingSeqNumber,
                                    int maxPageWidth) throws OrderException {
        logger.info("Load Orders filtered from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        Query queryInst;
        List dealerInstitutions;
        List<Order> orders;
        int totalRecords;
        long dealer = Long.parseLong(dealerId);
        if (primarySearchFilter == OrderSearchFilter.INSTITUTION) {
            String sql = "select c.inst_code from core_imm_institution c where c.REMOTE_INST_ID='" + primarySearchValue + "'";
            Query query = session.createSQLQuery(sql);
            List results = query.list();
            if (results != null && !results.isEmpty()) {
                primarySearchValue = (String) results.get(0);
            }
            logger.info("Selected institution code: {}", primarySearchValue);
        }
        hqlQuery = orderSearchHQLGenerator.createQueryForSearchOrders(fromDate, toDate, primarySearchFilter, primarySearchValue,
                secondarySearchFilter, secondarySearchValue, orderGroup, searchMode, dealer, isGlobalDealer);
        try {
            Query query = session.createQuery(hqlQuery);

            if (!isGlobalDealer) {
                //Hibernate query for getting dealer institutions for given dealer
                String hqlDealerInstitutions = "SELECT DISTINCT DI.institutionCode FROM lk.ac.ucsc.oms.user.implGeneral.beans.InstitutionRepresentativeBean DI WHERE DI.userID = " + dealerId;
                queryInst = session.createQuery(hqlDealerInstitutions);
                dealerInstitutions = queryInst.list();
                if (!dealerInstitutions.isEmpty()) {
                    query.setParameterList("INSTITUTIONS", dealerInstitutions);
                } else {
                    query.setParameter("INSTITUTIONS", "");
                }
            }

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }

            //Getting total number of records for given search conditions
            ScrollableResults results = query.scroll();
            results.last();
            totalRecords = results.getRowNumber() + 1;
            results.close();

            //Hibernate pagination - It starts with index 0
            int startingSequence = startingSeqNumber;
            query.setFirstResult(--startingSequence);
            query.setMaxResults(maxPageWidth);

            orders = query.list();

        } catch (Exception e) {
            logger.error("Error in searching orders", e);
            throw new OrderException("Error in searching orders", e);
        } finally {
            session.close();
        }

        SearchReply reply = new SearchReplyBean();
        reply.setResultObjects(orders);
        reply.setTotalNumberOfRecords(totalRecords);

        return reply;
    }

    @Override
    public SearchReply searchOrdersXt(String execBrokerCode, int primarySearchFilter, String primarySearchValue,
                                    Date fromDate, Date toDate, boolean open, int startingSeqNumber, int maxPageWidth) throws OrderException {
        logger.info("Load Orders filtered from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        List<Order> orders;
        int totalRecords;

        hqlQuery = orderSearchHQLGenerator.createQueryForSearchOrdersXt(execBrokerCode, fromDate, toDate,
                primarySearchFilter, primarySearchValue, open);
        try {
            Query query = session.createQuery(hqlQuery);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }

            //Getting total number of records for given search conditions
            ScrollableResults results = query.scroll();
            results.last();
            totalRecords = results.getRowNumber() + 1;
            results.close();

            //Hibernate pagination - It starts with index 0
            int startingSequence = startingSeqNumber;
            query.setFirstResult(--startingSequence);
            query.setMaxResults(maxPageWidth);

            orders = query.list();

        } catch (Exception e) {
            logger.error("Error in searching orders", e);
            throw new OrderException("Error in searching orders", e);
        } finally {
            session.close();
        }

        SearchReply reply = new SearchReplyBean();
        reply.setResultObjects(orders);
        reply.setTotalNumberOfRecords(totalRecords);

        return reply;
    }

    @Override
    public SearchReply searchWaitingForApprovalOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue, Date fromDate, Date toDate, String dealerId, boolean isGlobalDealer, int startingSeqNumber, int maxPageWidth) throws OrderException {
        logger.info("Load Orders filtered from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        Query queryInst;
        List dealerInstitutions;
        List<Order> orders;
        int totalRecords;
        long dealer = Long.parseLong(dealerId);
        hqlQuery = orderSearchHQLGenerator.createQueryForSearchWaitingForApprovalOrders(fromDate, toDate, primarySearchFilter, primarySearchValue,
                secondarySearchFilter, secondarySearchValue, dealer, isGlobalDealer);
        try {
            Query query = session.createQuery(hqlQuery);

            if (!isGlobalDealer) {
                //Hibernate query for getting dealer institutions for given dealer
                String hqlDealerInstitutions = "SELECT DISTINCT DI.institutionCode FROM lk.ac.ucsc.oms.user.implGeneral.beans.InstitutionRepresentativeBean DI WHERE DI.userID = " + dealerId;
                queryInst = session.createQuery(hqlDealerInstitutions);
                dealerInstitutions = queryInst.list();
                query.setParameterList("INSTITUTIONS", dealerInstitutions);
            }

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }

            //Getting total number of records for given search conditions
            ScrollableResults results = query.scroll();
            results.last();
            totalRecords = results.getRowNumber() + 1;
            results.close();

            //Hibernate pagination - It starts with index 0
            int startingSequence = startingSeqNumber;
            query.setFirstResult(--startingSequence);
            query.setMaxResults(maxPageWidth);

            orders = query.list();

        } catch (Exception e) {
            logger.error("Error in searching waiting for approval orders", e);
            throw new OrderException("Error in searching waiting for approval orders", e);
        } finally {
            session.close();
        }

        SearchReply reply = new SearchReplyBean();
        reply.setResultObjects(orders);
        reply.setTotalNumberOfRecords(totalRecords);

        return reply;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchReply searchParkedOrders(String dealerId, int startingSeqNumber, int maxPageWidth, Date fromDate, Date toDate) throws OrderException {
        logger.info("Load Parked Orders filtered from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        List<Order> orders;
        int totalRecords;
        long dealer = Long.parseLong(dealerId);

        hqlQuery = orderSearchHQLGenerator.createQueryForSearchParkedOrders(dealer, fromDate, toDate);
        try {
            Query query = session.createQuery(hqlQuery);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }

            //Getting total number of records for given search conditions
            ScrollableResults results = query.scroll();
            results.last();
            totalRecords = results.getRowNumber() + 1;
            results.close();

            //Hibernate pagination - It starts with index 0
            int startingSequence = startingSeqNumber;
            query.setFirstResult(--startingSequence);
            query.setMaxResults(maxPageWidth);

            orders = query.list();

        } catch (Exception e) {
            logger.error("Error in searching parked orders", e);
            throw new OrderException("Error in searching parked orders", e);
        } finally {
            session.close();
        }

        SearchReply reply = new SearchReplyBean();
        reply.setResultObjects(orders);
        reply.setTotalNumberOfRecords(totalRecords);

        return reply;
    }

    @Override
    public SearchReply searchOrderHistory(String dealerId, String orderNumber) throws OrderException {
        logger.info("Load order history from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        List<Order> orders;

        hqlQuery = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C " +
                "WHERE C.orderNo=:orderNo";

        try {
            Query query = session.createQuery(hqlQuery);
            query.setParameter("orderNo", orderNumber);

            orders = query.list();

        } catch (Exception e) {
            logger.error("Error in searching order history", e);
            throw new OrderException("Error in searching order history", e);
        } finally {
            session.close();
        }

        SearchReply reply = new SearchReplyBean();
        reply.setResultObjects(orders);

        return reply;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchReply searchConflictingOrders(int startingSeqNumber, int maxPageWidth, String clOrdId) throws OrderException {
        logger.info("Load Conflicting Orders filtered from the Database");
        Session session = searchSessionFactory.openSession();
        String hqlQuery;
        Order order;
        List<Order> orderList;
        List<Order> resultOrders = new ArrayList<>();

        if (clOrdId != null && !"".equals(clOrdId)) {

            hqlQuery = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.clOrdID = :clOrd_id";

            try {
                Query query = session.createQuery(hqlQuery);
                query.setParameter("clOrd_id", clOrdId);

                order = (Order) query.uniqueResult();
            } catch (Exception e) {
                logger.error("Error in searching conflicting orders", e);
                throw new OrderException("Error in searching conflicting orders", e);
            }

            if (order == null) {
                throw new OrderException("Invalid Request");
            }

            SymbolPriceData priceData;
            try {
                priceData = symbolPriceManager.getPriceDataForSymbol(order.getSymbol(), order.getExchange());
            } catch (SymbolPriceException e) {
                logger.error("Error in getting price data for symbol", e);
                throw new OrderException("Error in getting price data for symbol", e);
            }

            //Determine the price for internal match
            double price;
            if (order.getType() == OrderType.MARKET) {
                if (order.getSide() == OrderSide.BUY) {
                    price = priceData.getBestBidPrice();
                } else {
                    price = priceData.getBestAskPrice();
                }
            } else {
                price = order.getPrice();
            }

            //Determine the order side for internal match
            String orderSide = null;
            if (order.getSide() == OrderSide.BUY) {
                orderSide = OrderSide.SELL.getCode();
            } else if (order.getSide() == OrderSide.SELL) {
                orderSide = OrderSide.BUY.getCode();
            }

            try {
                //Getting the conflicting order
                hqlQuery = "SELECT C FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C," +
                        "lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean D " +
                        "WHERE " +
                        "C.orderStatus IN('0','5','1') AND " +
                        "C.internalMatchStatus = 0 AND " +
                        "C.orderSide = :ORDER_SIDE AND " +
                        "C.exchange = :EXCHANGE AND " +
                        "C.symbol = :SYMBOL AND " +
                        "C.routingAccount = :EXCHANGE_ACCOUNT AND " +
                        "C.customerNumber = D.customerNumber AND " +
                        "D.internalMatchingAllow = 1";

                Query query = session.createQuery(hqlQuery);

                query.setParameter("ORDER_SIDE", orderSide);
                query.setParameter("EXCHANGE", order.getExchange());
                query.setParameter("SYMBOL", order.getSymbol());
                query.setParameter("EXCHANGE_ACCOUNT", order.getRoutingAccount());

                orderList = query.list();
            } catch (Exception e) {
                logger.error("Error in searching conflicting orders", e);
                throw new OrderException("Error in searching conflicting orders", e);
            } finally {
                session.close();
            }

            for (Order orderTemp : orderList) {
                //Determine the price for internal match
                double priceTemp;
                if (orderTemp.getType() == OrderType.MARKET) {
                    if (orderTemp.getSide() == OrderSide.BUY) {
                        priceTemp = priceData.getBestBidPrice();
                    } else {
                        priceTemp = priceData.getBestAskPrice();
                    }
                } else {
                    priceTemp = orderTemp.getPrice();
                }

                if (priceTemp == price) {
                    resultOrders.add(orderTemp);
                }
            }
        } else {
            //Getting the conflicting order
            try {
                hqlQuery = "SELECT L.clOrd_ID as Id, L.OrderStatus as status, L.ROUTING_ACCOUNT_NUMBER as account, L.Exchange as exchange, L.Symbol as symbol, L.OrderSide as side, L.OrderType, CASE WHEN L.OrderType = '1' THEN (CASE WHEN L.OrderSide = '1' THEN M.bestbidprice ELSE M.bestaskprice END) ELSE L.Price END as price FROM core_omm_order L,esp_todays_snapshots M WHERE L.Symbol = M.symbol AND L.Exchange = M.exchangecode AND L.INTERNAL_MATCH_STATUS = 0 AND L.OrderStatus IN ('0','5','1')";
                Query query3 = session.createSQLQuery(hqlQuery);
                query3.list();

                hqlQuery = "SELECT L.clOrd_ID as Id, L.OrderStatus as status, L.ROUTING_ACCOUNT_NUMBER as account, L.Exchange as exchange, L.Symbol as symbol, L.OrderSide as side, L.OrderType, CASE WHEN L.OrderType = '1' THEN (CASE WHEN L.OrderSide = '1' THEN M.bestbidprice ELSE M.bestaskprice END) ELSE L.Price END as price FROM core_omm_order L,esp_todays_snapshots M WHERE L.Symbol = M.symbol AND L.Exchange = M.exchangecode AND L.INTERNAL_MATCH_STATUS <> 0 AND L.OrderStatus IN ('p','1')";
                Query query4 = session.createSQLQuery(hqlQuery);
                query4.list();

                //Getting the conflicting order
                hqlQuery = "SELECT C.* FROM core_omm_order C," +
                        "core_cusmm_customer D " +
                        "WHERE " +
                        "C.CustomerNumber = D.CUSTOMER_NUMBER AND " +
                        "D.INTERNAL_MATCH_ALLOW = 1 AND " +
                        "C.clOrd_ID IN " +
                        "(" +
                        "SELECT A.Id FROM " +
                        "(SELECT L.clOrd_ID as Id, L.OrderStatus as status, L.ROUTING_ACCOUNT_NUMBER as account, L.Exchange as exchange, L.Symbol as symbol, L.OrderSide as side, L.OrderType, CASE WHEN L.OrderType = '1' THEN (CASE WHEN L.OrderSide = '1' THEN M.bestbidprice ELSE M.bestaskprice END) ELSE L.Price END as price FROM core_omm_order L,esp_todays_snapshots M WHERE L.Symbol = M.symbol AND L.Exchange = M.exchangecode AND L.INTERNAL_MATCH_STATUS = 0 AND L.OrderStatus IN ('0','5','1'))A," +
                        "(SELECT L.clOrd_ID as Id, L.OrderStatus as status, L.ROUTING_ACCOUNT_NUMBER as account, L.Exchange as exchange, L.Symbol as symbol, L.OrderSide as side, L.OrderType, CASE WHEN L.OrderType = '1' THEN (CASE WHEN L.OrderSide = '1' THEN M.bestbidprice ELSE M.bestaskprice END) ELSE L.Price END as price FROM core_omm_order L,esp_todays_snapshots M WHERE L.Symbol = M.symbol AND L.Exchange = M.exchangecode AND L.INTERNAL_MATCH_STATUS <> 0 AND L.OrderStatus IN ('p','1'))B " +
                        "WHERE " +
                        "A.symbol = B.symbol AND " +
                        "A.exchange = B.exchange AND " +
                        "A.price = B.price AND " +
                        "A.account = B.account AND " +
                        "A.side <> B.side" +
                        ")";

                SQLQuery query = session.createSQLQuery(hqlQuery).addEntity(OrderBean.class);
                resultOrders = query.list();
            } catch (HibernateException e) {
                logger.error("Error in searching conflicting orders", e);
                throw new OrderException("Error in searching conflicting orders", e);
            } finally {
                session.close();
            }
        }

        SearchReply reply = new SearchReplyBean();
        reply.setResultObjects(resultOrders);
        reply.setTotalNumberOfRecords(resultOrders.size());

        return reply;
    }

    @Override
    public List<Order> getPreOpenOrderListByExecutionBrokerId(String exchange, String marketCode, String executionBrokerId) throws OrderException {
        logger.info("Get oms accepted orders from the database by execution broker id");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.execBrokerId=:executionBrokerId and C.exchange=:exchange and C.marketCode=:marketCode and C.orderStatus='O' AND C.channelCode!= :channelCode ORDER BY C.clOrdID ASC ";
            Query query = session.createQuery(obhql);
            query.setParameter("executionBrokerId", executionBrokerId);
            query.setParameter("exchange", exchange);
            query.setParameter("marketCode", marketCode);
            query.setParameter("channelCode", ClientChannel.MANUAL.getCode());
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in finding pre open orders by execution broker id", e);
            throw new OrderException("Error in finding pre open orders by execution broker id", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public List<String> getEODTPlusPortfolioList(String eodDate) throws OrderException {

        logger.info("Load portfolio list for EOD process TPlus from the Database - eodDate : ", eodDate);
        Session session = searchSessionFactory.openSession();
        String sqlQuery;
        List<String> portfolioList = new ArrayList<>(0);

        try {
            if (eodDate != null && !"".equals(eodDate)) {

                sqlQuery = "select DISTINCT securityaccount,routing_acc_ref from core_omm_order o " +
                        "where trunc(o.lastupdatedate) = trunc(" + eodDate + ") and o.exchange=:exchange and " +
                        "o.settlementtype <= :settlementType and o.institution_id in(select inst_id from core_imm_inst_ord_level_cfg " +
                        "where tplus_settlement_enable = :tPlusEnable) and o.orderno in (select orderno from core_omm_order_execution e " +
                        "where trunc(e.date_time) = trunc(" + eodDate + ")) and o.securityaccount in(" +
                        "select account_number from core_cusmm_exchangeaccount where account_type = :accountType and exchange_code=:exchange)";

                SQLQuery query = session.createSQLQuery(sqlQuery);
                query.setParameter("exchange", "CASE");
                query.setParameter("settlementType", 0);
                query.setParameter("tPlusEnable", 1);
                query.setParameter("accountType", 2);
                List<Object> resultList = query.list();

                if (resultList != null && !resultList.isEmpty()) {
                    for (Object portNumArray : resultList) {
                        Object[] dataList = (Object[]) portNumArray;
                        if (dataList.length > 0 && dataList[0] != null && !"".equals(dataList[0].toString())) {
                            if (dataList[1] != null && !"".equals(dataList[1].toString()) && dataList[1].toString().startsWith("S00")) {
                                portfolioList.add(dataList[1].toString());
                            } else {
                                portfolioList.add(dataList[0].toString());
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            logger.error("Error in finding portfolio list for EOD process TPLUS ", e);
            throw new OrderException("Error in portfolio list for EOD process TPLUS ", e);
        } finally {
            session.close();
        }
        return portfolioList;
    }

    @Override
    public List<String> getEODTPlusSymbolList(String portfolioNumber, String eodDate) throws OrderException {

        logger.info("Load portfolio list for EOD process TPlus from the Database");
        Session session = searchSessionFactory.openSession();
        String sqlQuery;
        List<String> symbolList = new ArrayList<>(0);

        try {
            if (portfolioNumber != null && !"".equals(portfolioNumber)) {

                sqlQuery = "select distinct symbol from core_omm_order o where trunc(o.lastupdatedate) = trunc(" + eodDate + ") " +
                        "and o.exchange=:exchange and o.settlementtype <= :settlementType and o.institution_id in(select inst_id from core_imm_inst_ord_level_cfg " +
                        "where tplus_settlement_enable = :tPlusEnable) and o.orderno in (select orderno from core_omm_order_execution e " +
                        "where trunc(e.date_time) = trunc(" + eodDate + ")) and o.securityaccount = :portfolioNumber or o.routing_acc_ref = :portfolioNumber";

                SQLQuery query = session.createSQLQuery(sqlQuery);
                query.setParameter("exchange", "CASE");
                query.setParameter("settlementType", 0);
                query.setParameter("tPlusEnable", 1);
                query.setParameter("portfolioNumber", portfolioNumber);
                symbolList = query.list();

            }
        } catch (Exception e) {
            logger.error("Error in finding symbol list for EOD process TPLUS ", e);
            throw new OrderException("Error in symbol list for EOD process TPLUS ", e);
        } finally {
            session.close();
        }

        return symbolList;
    }

    @Override
    public List<Order> getEODTPlusOrderList(String portfolioNumber, String symbol, String eodDate) throws OrderException {

        logger.info("Load portfolio list for EOD process TPlus from the Database");
        Session session = searchSessionFactory.openSession();
        String sqlQuery;
        List<Order> orderList = new ArrayList<>(0);

        try {
            if (portfolioNumber != null && !"".equals(portfolioNumber)) {

                sqlQuery = "select * from core_omm_order o where trunc(o.lastupdatedate) = trunc(" + eodDate + ") and " +
                        "o.settlementtype <= :settlementType and o.exchange=:exchange and o.institution_id in (select inst_id from core_imm_inst_ord_level_cfg " +
                        "where tplus_settlement_enable = :tPlusEnable) and o.orderno in (select orderno from core_omm_order_execution e " +
                        "where trunc(e.date_time) = trunc(" + eodDate + ")) and (o.securityaccount = :portfolioNumber or o.routing_acc_ref = :portfolioNumber) and " +
                        "o.orderside=1 and o.orderstatus in ('2','1','4','C') and o.symbol = :symbol order by o.avgprice";

                SQLQuery query = session.createSQLQuery(sqlQuery).addEntity(lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean.class);
                query.setParameter("exchange", "CASE");
                query.setParameter("settlementType", 0);
                query.setParameter("tPlusEnable", 1);
                query.setParameter("portfolioNumber", portfolioNumber);
                query.setParameter("symbol", symbol);
                orderList = query.list();

            }
        } catch (Exception e) {
            logger.error("Error in finding order list for EOD process TPLUS ", e);
            throw new OrderException("Error in order list for EOD process TPLUS ", e);
        } finally {
            session.close();
        }

        return orderList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findPendingOrders(String accountNumber) throws OrderException {
        logger.info("Get pending orders from the Database");
        List<Order> resultList = new ArrayList<Order>();
        Session session = realTimeSessionFactory.openSession();
        try {
            //The below HQL query will work properly only if the OrderStatus column in DB marked as case sensitive (ex. utf8_bin Collation) - Rajeevan
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.securityAccount = :securityAccount " +
                    "AND ( ( C.orderStatus NOT IN('f', 'm') AND year(C.lastUpdateDate) = year(current_date) AND month(C.lastUpdateDate) =  month(current_date) AND day(C.lastUpdateDate) =  day(current_date) ) " +
                    "OR C.orderStatus IN('A','0','O','1','R','5','9') ) ORDER BY C.id ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("securityAccount", accountNumber);
            resultList = query.list();
            logger.debug("Retrieving pending orders successful");
        } catch (Exception e) {
            logger.error("Error in finding pending orders - {}", e);
            throw new OrderException("Error in finding pending orders", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> searchOrders(String accountNumber, String clOrdID, String symbol, String orderSide, String orderStatus, Date startDate, Date endDate, String exchange) throws OrderException {
        logger.info("Load all Orders filtered from the Database");
        List<Order> orderLst;
        String hqlQuery = orderSearchHQLGenerator.createQueryForSearchOrders(accountNumber, clOrdID, symbol, orderSide, orderStatus, startDate, endDate, exchange);
        Session session = realTimeSessionFactory.openSession();
        try {
            Query query = session.createQuery(hqlQuery);

            if (startDate != null && endDate != null) {
                query.setParameter("fromDate", startDate);
                query.setParameter("toDate", endDate);
            }
            orderLst = query.list();

        } catch (Exception e) {
            logger.error("Error in searching orders", e);
            throw new OrderException("Error in searching orders", e);
        } finally {
            session.close();
        }
        return orderLst;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findPreOpenOrderList(String exchange, String marketCode) throws OrderException {
        logger.info("Get oms accepted orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.exchange = :exchange AND C.marketCode=:marketCode " +
                    "AND C.orderStatus='O' AND C.channelCode!= :channelCode ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("exchange", exchange);
            query.setParameter("marketCode", marketCode);
            query.setParameter("channelCode", ClientChannel.MANUAL.getCode());
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in finding pre open orders", e);
            throw new OrderException("Error in finding pre open orders", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findOpenOrders(String exchange, String marketCode) throws OrderException {
        logger.info("Get all open orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.exchange = :exchange AND C.marketCode=:marketCode " +
                    EXPIRETIME_HQL_QUARY +
                    " AND C.orderStatus IN('0','5','R','9','L','1','Q','O','p') AND C.timeInForce<>1  OR C.orderStatus='c'  ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("exchange", exchange);
            query.setParameter("marketCode", marketCode);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in finding open orders", e);
            throw new OrderException("Error in finding open orders", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findExecBrkWiseOpenOrders(String exchange, String marketCode, String execBrokerId) throws OrderException {
        logger.info("Get all open orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.exchange = :exchange AND C.marketCode=:marketCode AND C.execBrokerId=:execBrkId " +
                    EXPIRETIME_HQL_QUARY +
                    " AND C.orderStatus IN('0','5','R','9','L','1','Q','O','p') AND C.timeInForce<>1  OR C.orderStatus='c'  ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("exchange", exchange);
            query.setParameter("marketCode", marketCode);
            query.setParameter("execBrkId", execBrokerId);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in finding open orders", e);
            throw new OrderException("Error in finding open orders", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findCancelledOrders(String exchange, String marketCode) throws OrderException {
        logger.info("Get all the cancelled orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.exchange = :exchange AND C.marketCode=:marketCode " +
                    EXPIRETIME_HQL_QUARY +
                    " AND C.orderStatus='4' AND C.cumQty>0 ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("exchange", exchange);
            query.setParameter("marketCode", marketCode);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in finding cancelled orders", e);
            throw new OrderException("Error in finding cancelled orders", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public List<Order> findExecBrkWiseCancelledOrders(String exchange, String marketCode, String execBrokerId) throws OrderException {
        logger.info("Get all the cancelled orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.exchange = :exchange AND C.marketCode=:marketCode AND C.execBrokerId=:execBroker " +
                    EXPIRETIME_HQL_QUARY +
                    " AND C.orderStatus='4' AND C.cumQty>0 ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("exchange", exchange);
            query.setParameter("marketCode", marketCode);
            query.setParameter("execBroker", execBrokerId);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in finding cancelled orders", e);
            throw new OrderException("Error in finding cancelled orders", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastOrderId() {
        logger.info("getting the last clOrd_ID from the DB");
        Session session = realTimeSessionFactory.openSession();
        int appID = System.getProperty("server.id") != null ? Integer.valueOf(System.getProperty("server.id")) : 0;
        try {
            String hql = "select C.clOrdID FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C " +
                    "WHERE year(C.createDate) = year(current_date) AND month(C.createDate) = month(current_date) AND day(C.createDate) =  day(current_date)" +
                    "ORDER BY C.createDate DESC, C.clOrdID DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);

            String results = (String) query.uniqueResult();

            if (results != null) {
                return results;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(new SimpleDateFormat("yyMMdd").format(System.currentTimeMillis()));
                sb.append("-" + appID);
                return sb.append("-0").toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            StringBuilder sb = new StringBuilder();
            sb.append(new SimpleDateFormat("yyMMdd").format(System.currentTimeMillis()));
            sb.append("-" + appID);
            return sb.append("-0").toString();
        } finally {
            session.close();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertList(List<CacheObject> objectList) {

        if (objectList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        logger.info("Inserting set of orders to DB");
        long startTime = System.currentTimeMillis();
        try {
            tx = session.beginTransaction();
            OrderBean orderBean;
            int i = 0;
            for (CacheObject co : objectList) {
                orderBean = (OrderBean) co;
                try {
                    session.save(orderBean);
                    co.setDirty(false);
                    co.setNew(false);
                    if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                        //flush a batch of inserts and release memory:
                        session.flush();
                        session.clear();
                    }
                    i++;
                } catch (ConstraintViolationException e) {
                    //persist the order to the error order table
                    errorOrderPersister.insert(orderBean);
                    logger.error("Constraint violation error  - {}", e);
                    logger.error("Error Order added to the Error Table - {}", new Gson().toJson(co));
                    co.setDirty(false);
                    co.setNew(false);
                    /*if this is a constraint violation exception then issue is in the cache object set New field
                    in this kind of situation order has to be update in the cache
                     */
                    logger.error("Constraint violation exception occurred in insert of order - {}. Hence Update the order bean.", orderBean.getClOrdID());
                    session.update(orderBean);
                } catch (Exception e) {
                    //persist the order to the error order table
                    errorOrderPersister.insert(orderBean);
                    logger.error("Constraint violation error  - {}", e);
                    logger.error("Error Order added to the Error Table - {}", new Gson().toJson(co));
                    co.setDirty(false);
                    co.setNew(false);
                }
            }
            tx.commit();
            lastUpdateTime = new Date();
            logger.info("Inserting set of orders to DB finished");
            long endTime = System.currentTimeMillis();
            logger.debug("Time to commit order insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Order Bean Can't Insert, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateList(List<CacheObject> updateList) {
        if (updateList == null) {
            return;
        }
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        logger.info("Updating set of orders to DB");
        long startTime = System.currentTimeMillis();

        try {
            tx = session.beginTransaction();
            OrderBean orderBean;
            int i = 0;
            for (CacheObject co : updateList) {
                orderBean = (OrderBean) co;
                try {
                    session.update(orderBean);
                    co.setDirty(false);
                    if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                        //flush a batch of updates and release memory:
                        session.flush();
                        session.clear();
                    }
                    i++;
                } catch (ConstraintViolationException e) {
                    logger.error("Constraint violation error - {}", e);
                    logger.error("Constraint violation exception occurred in saveOrUpdate of order - {}. Hence Update the order bean.", orderBean.getClOrdID());
                    session.update(orderBean);
                }
            }
            tx.commit();
            lastUpdateTime = new Date();
            long endTime = System.currentTimeMillis();
            logger.info("Updating set of orders to DB finished");
            logger.debug("Time to commit bulk order update process >>>" + (endTime - startTime));

        } catch (ConstraintViolationException e) {
            logger.error("Order Bean Can't update - {}", e);
        } catch (Exception e) {
            logger.error("Order Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    private long getTotalRecordCount() {
        logger.info("Loading all record count from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select count(*) FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean";
            Query query = session.createQuery(hql);
            Long totalCount = (Long) query.uniqueResult();
            return totalCount;
        } catch (Exception e) {
            logger.error("Error occured while get count all Order Bean ", e);
        } finally {
            session.close();
        }
        return 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findOrderListWithDeskOrderReference(String deskOrderRef) throws OrderException {
        logger.info("Get all the Child Desk orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.deskOrderRef = :deskOrderRef " +
                    EXPIRETIME_HQL_QUARY +
                    " ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("deskOrderRef", deskOrderRef);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in find orders with desk order reference", e);
            throw new OrderException("Error in find orders with desk order reference", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public List<Order> findOrderListWithConditionalOrderReference(String conditionalOrderRef) throws OrderException {
        logger.info("Get all the Conditional triggered orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.conditionalOrderRef = :condOrdRef ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("condOrdRef", conditionalOrderRef);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in find orders with conditional order reference", e);
            throw new OrderException("Error in find orders with conditional order reference", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findOpenOrderListWithDeskOrderReference(String deskOrderRef, OrderSide orderSide) throws OrderException {
        logger.info("Get all the Child Desk orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.deskOrderRef = :deskOrderRef " +
                    EXPIRETIME_HQL_QUARY +
                    " AND C.orderStatus IN('0','1','O','5','9','A','M','Q','i','j','E') AND C.timeInForce<>1  ORDER BY C.createDate desc, C.price "
                    + (orderSide == OrderSide.BUY ? "ASC" : "DESC");

            Query query = session.createQuery(obhql);
            query.setParameter("deskOrderRef", deskOrderRef);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in find open orders with desk order reference", e);
            throw new OrderException("Error in find open orders with desk order reference", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getOrdersForIOM(String symbol, String exchange, double price, OrderSide side, String routingAcc, String securityAccount) throws OrderException {
        logger.info("Get all the Child Desk orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE " +
                    " C.symbol =:symbol AND C.exchange =:exchange AND C.price=:price AND C.orderSide=:side AND C.routingAccount=:routingAcc" +
                    " AND C.securityAccount!=:secAcc AND C.orderStatus IN('0','5','1','O','p') ";

            Query query = session.createQuery(obhql);
            query.setParameter("symbol", symbol);
            query.setParameter("exchange", exchange);
            query.setParameter("price", price);
            query.setParameter("side", side.getCode());
            query.setParameter("routingAcc", routingAcc);
            query.setParameter("secAcc", securityAccount);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in getting orders for IOM", e);
            throw new OrderException("Error in getting orders for IOM", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findClOrdIdByRemoteClOrdId(String remoteClOrdId) throws OrderException {
        logger.info("Get Order Bean from the Remote Clorder ID - {}", remoteClOrdId);
        if (remoteClOrdId == null || "".equals(remoteClOrdId)) {
            logger.debug("Validation fails OrderBean");
            throw new OrderException("Error in getting clOrdId by remote clOrdId");
        }
        String clOrdId = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "SELECT C.clOrdID FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.remoteClOrdId = :RemoteClOrdId";
            Query query = session.createQuery(obhql);
            query.setParameter("RemoteClOrdId", remoteClOrdId);
            clOrdId = (String) query.uniqueResult();
            logger.info("Retrieving Order from successful, Client Order ID : " + clOrdId);
        } catch (Exception e) {
            logger.debug("Error in getting clOrdId by remote clOrdId", e);
            throw new OrderException("Error in getting clOrdId by remote clOrdId", e);
        } finally {
            session.close();
        }
        return clOrdId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findClOrdIdByBackOfficeOrderId(String backOfficeOrderId) throws OrderException {
        logger.info("Get Order Bean from the Back Office Order ID - {}", backOfficeOrderId);
        if (backOfficeOrderId == null || "".equals(backOfficeOrderId)) {
            logger.debug("Validation fails OrderBean");
            throw new OrderException("Error in getting clOrdId by Back office order id");
        }
        String clOrdId = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "SELECT C.clOrdID FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.backOfficeOrderId = :BackOfficeOrderId";
            Query query = session.createQuery(obhql);
            query.setParameter("BackOfficeOrderId", backOfficeOrderId);
            clOrdId = (String) query.uniqueResult();
            logger.info("Retrieving Order from successful, Client Order ID : " + clOrdId);
        } catch (Exception e) {
            logger.debug("Error in getting clOrdId by Back office order ID", e);
            throw new OrderException("Error in getting clOrdId by Back office order ID", e);
        } finally {
            session.close();
        }
        return clOrdId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findClOrdIdListByMasterOrdId(String masterOrdId) throws OrderException {
        logger.info("Get given OrderBean from the Database");
        if (masterOrdId == null || "".equals(masterOrdId)) {
            logger.debug("Validation fails OrderBean");
            throw new OrderException("Error in getting clOrdId by master ordId");
        }
        List<String> clOrdIdList = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "SELECT C.clOrdID FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.masterOrderId = :MasterOrdId";
            Query query = session.createQuery(obhql);
            query.setParameter("MasterOrdId", masterOrdId);
            clOrdIdList = query.list();
            logger.info("Retrieving Order successful, Client Order ID : " + clOrdIdList);
        } catch (Exception e) {
            logger.error("Error in getting clOrdId by master orderId", e);
            throw new OrderException("Error in getting clOrdId by master OrdId", e);
        } finally {
            session.close();
        }
        return clOrdIdList;
    }

    /**
     * Find the order by order number
     *
     * @param orderNo is the order number
     * @return Order
     * @throws lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException
     */
    @Override
    public Order findOrderByOrderNo(String orderNo) throws OrderException {
        logger.info("getting the order by order number from the DB");
        Session session = realTimeSessionFactory.openSession();
        OrderBean order = null;
        try {
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C " +
                    "WHERE C.orderNo = :orderNumber";
            Query query = session.createQuery(hql);
            query.setParameter("orderNumber", orderNo);
            query.setMaxResults(1);

            order = (OrderBean) query.uniqueResult();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OrderException("Error in find order using order number", e);
        } finally {
            session.close();
        }
        return order;
    }

    @Override
    public List<String> findClOrdIdListByDeskOrdRef(String deskOrdReference) throws OrderException {
        logger.info("Get given OrderBean from the Database");
        if (deskOrdReference == null || "".equals(deskOrdReference)) {
            logger.debug("Validation fails OrderBean");
            throw new OrderException("Error in getting clOrdId by master ordId");
        }
        List<String> clOrdIdList;
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "SELECT C.clOrdID FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.deskOrderRef = :DeskOrderRef";
            Query query = session.createQuery(obhql);
            query.setParameter("DeskOrderRef", deskOrdReference);
            clOrdIdList = query.list();
            logger.info("Retrieving Order successful, Client Order ID : " + clOrdIdList);
        } catch (Exception e) {
            logger.error("Error in getting clOrdId by desk order reference", e);
            throw new OrderException("Error in getting clOrdId by desk order reference", e);
        } finally {
            session.close();
        }
        return clOrdIdList;
    }

    @Override
    public Order findOrderByBrokerFIXID(String brokerFIXID, String remoteClordID) throws OrderException {
        logger.info("Getting the order using broker fix id - {}, and remote clord id - {}", brokerFIXID, remoteClordID);
        Session session = realTimeSessionFactory.openSession();
        OrderBean order = null;
        try {
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C " +
                    "WHERE C.brokerFIXID = :brokerID and C.remoteClOrdId = :remoteClordID ORDER BY C.id DESC";

            Query query = session.createQuery(hql);
            query.setParameter("brokerID", brokerFIXID);
            query.setParameter("remoteClordID", remoteClordID);
            query.setMaxResults(1);

            order = (OrderBean) query.uniqueResult();
            logger.info("Retrieving Order successful, Client Order ID - {} ", order.getClOrdID());
        } catch (Exception e) {
            logger.error("Error in getting Order by broker fix id and clord id", e);
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return order;
    }

    public double calculateDayCumOrderNetSettle(String customerNumber, String exchange, String symbol) throws OrderException {
        double cumNetSettle = 0;
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "SELECT SUM(C.cumNetSettle)/SUM(C.cumQty) " +
                    "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C " +
                    "WHERE C.customerNumber = :custNo AND C.symbol =: symbol AND C.exchange =:exchange " +
                    "AND C.orderSide= :side AND C.DayOrder = true AND C.cumQty>0 " +
                    // we want only the day-orders within today
                    "AND year(C.createDate) = year(current_date) AND month(C.createDate) = month(current_date) AND day(C.createDate) =  day(current_date)";
            Query query = session.createQuery(hql);
            query.setParameter("custNo", customerNumber);
            query.setParameter("symbol", symbol);
            query.setParameter("exchange", exchange);
            query.setParameter("side", 1);   // todo verify this logic: only buy side?
            cumNetSettle = (double) query.uniqueResult();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in calculate day cumulative order net settle", e);
            throw new OrderException("Error in calculate day cumulative order net settle", e);
        } finally {
            session.close();
        }
        return cumNetSettle;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrders(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws OrderException {
        logger.info("Load orders filtered from the Database");
        List<Order> orderLst = new ArrayList<Order>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hqlQuery = orderSearchHQLGenerator.createQueryToGetOrdersByDatesAndInstitutions(fromDate, toDate, institutionList, filters);
            Query query = session.createQuery(hqlQuery);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            } else if (fromDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", fromDate);
            } else if (toDate != null) {
                query.setParameter("fromDate", toDate);
                query.setParameter("toDate", toDate);
            }

            List results = query.list();

            for (Object ob : results) {
                OrderBean order = (OrderBean) ob;
                orderLst.add(order);
            }
        } catch (Exception e) {
            logger.error("Error in get all orders", e);
            throw new OrderException("Error in get all orders", e);
        } finally {
            session.close();
        }

        return orderLst;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllOrdersForBroker(Date fromDate, Date toDate, Map<String, String> filters, String brokerId, String statusFilter) throws OrderException {
        logger.info("Load orders filtered from the Database");
        List<Order> orderLst = new ArrayList<Order>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hqlQuery = orderSearchHQLGenerator.createQueryToGetOrdersByBrokerDatesAndInstitutions(fromDate, toDate, filters, brokerId, statusFilter);
            Query query = session.createQuery(hqlQuery);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            } else if (fromDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", fromDate);
            } else if (toDate != null) {
                query.setParameter("fromDate", toDate);
                query.setParameter("toDate", toDate);
            }

            List results = query.list();

            for (Object ob : results) {
                OrderBean order = (OrderBean) ob;
                orderLst.add(order);
            }
        } catch (Exception e) {
            logger.error("Error in get all orders", e);
            throw new OrderException("Error in get all orders", e);
        } finally {
            session.close();
        }

        return orderLst;
    }

    /**
     * {@inheritDoc}
     */
    public double getDayCumOrdNetSettle(String accountNo, String exchange, String symbol) {
        double dayCumOrdNetSettle = 0;
        Session session = realTimeSessionFactory.openSession();
        try {
            logger.info("Calculating DayCumOrdNetSettle");

            String hql = "SELECT sum(C.cumNetSettle)/sum(C.cumQty) " +
                    "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C " +
                    "WHERE C.securityAccount = :account AND C.exchange = :exchange AND C.symbol = :symbol " +
                    "AND C.DayOrder = true AND C.orderSide = '1' AND C.cumQty>0 " +
                    "AND (year(C.lastUpdateDate) = year(current_date) AND month(C.lastUpdateDate) =  month(current_date) AND day(C.lastUpdateDate) =  day(current_date) )"; // today
            Query query = session.createQuery(hql);
            query.setParameter("account", accountNo);
            query.setParameter("symbol", symbol);
            query.setParameter("exchange", exchange);

            if (query.uniqueResult() != null) {
                dayCumOrdNetSettle = (Double) query.uniqueResult();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        } finally {
            session.close();
        }

        return dayCumOrdNetSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> findOpenOrderListWithMultiNINOrderReference(String multiNINOrderRef) throws OrderException {
        logger.info("Get all the MultiNIN child orders from the database");
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.multiNINOrderRef = :multiNINOrderRef " +
                    " AND C.orderStatus NOT IN('4','8','m','C','e','f','J','6')" +
                    " ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            query.setParameter("multiNINOrderRef", multiNINOrderRef);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in find orders with multiNIN order reference", e);
            throw new OrderException("Error in find orders with multiNIN order reference", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public List<Order> findTimeTriggerOrderList() throws OrderException {
        List<Order> resultList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String obhql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean C WHERE C.orderStatus IN('t') " +
                    EXPIRETIME_HQL_QUARY +
                    " AND C.timeTriggerOrder = 1 AND C.triggerTime is not null " +
                    " ORDER BY C.clOrdID ASC";
            Query query = session.createQuery(obhql);
            resultList = query.list();
            logger.info(RETRIEVING_ORDERS_SUCCESSFUL);
        } catch (Exception e) {
            logger.error("Error in find orders with multiNIN order reference", e);
            throw new OrderException("Error in find orders with multiNIN order reference", e);
        } finally {
            session.close();
        }
        return resultList;
    }

    /**
     * Inject the Error order persister
     *
     * @param errorOrderPersister is the persister used to persist the error occurred orders to the separate db table
     */
    public void setErrorOrderPersister(ErrorOrderPersister errorOrderPersister) {
        this.errorOrderPersister = errorOrderPersister;
    }

    /**
     * Method used to populate the order bean get from database with the values of order bean get from the cache
     *
     * @param orderFromDB    is the order bean get from the db
     * @param orderFromCache is the order bean get from the cache
     * @return the populated order
     */
    private OrderBean populateOrderFromNew(OrderBean orderFromDB, OrderBean orderFromCache) {
        logger.info("Populate the error occurred order to update in the database");
        orderFromDB.setQuantity(orderFromCache.getQuantity());
        orderFromDB.setPrice(orderFromCache.getPrice());
        orderFromDB.setStatus(orderFromCache.getStatus());
        orderFromDB.setOrderNo(orderFromCache.getOrderNo());
        orderFromDB.setCommission(orderFromCache.getCommission());
        orderFromDB.setCumCommission(orderFromCache.getCumCommission());
        orderFromDB.setCumQty(orderFromCache.getCumQty());
        orderFromDB.setCumOrderValue(orderFromCache.getCumOrderValue());
        orderFromDB.setCumBrokerCommission(orderFromCache.getCumBrokerCommission());
        orderFromDB.setCumExchangeCommission(orderFromCache.getCumExchangeCommission());
        orderFromDB.setCumParentCommission(orderFromCache.getCumParentCommission());
        orderFromDB.setCumParentCommission(orderFromCache.getCumParentCommission());
        orderFromDB.setCumThirdPartyCommission(orderFromCache.getCumThirdPartyCommission());
        orderFromDB.setCumNetSettle(orderFromCache.getCumNetSettle());
        orderFromDB.setCumNetValue(orderFromCache.getCumNetValue());
        orderFromDB.setLastPx(orderFromCache.getLastPx());
        orderFromDB.setLastShares(orderFromCache.getLastShares());
        orderFromDB.setAvgPrice(orderFromCache.getAvgPrice());
        orderFromDB.setLeavesQty(orderFromCache.getLeavesQty());
        orderFromDB.setPriceBlock(orderFromCache.getPriceBlock());
        orderFromDB.setOrderValue(orderFromCache.getOrderValue());
        orderFromDB.setNetValue(orderFromCache.getNetValue());
        orderFromDB.setNetSettle(orderFromCache.getNetSettle());
        orderFromDB.setBlockAmount(orderFromCache.getBlockAmount());
        orderFromDB.setParentBlockAmount(orderFromCache.getParentBlockAmount());
        orderFromDB.setMarginBlock(orderFromCache.getMarginBlock());
        orderFromDB.setDayMarginQuantity(orderFromCache.getDayMarginQuantity());
        orderFromDB.setParentNetSettle(orderFromCache.getParentNetSettle());
        orderFromDB.setProfitLoss(orderFromCache.getProfitLoss());
        orderFromDB.setParentProfitLoss(orderFromCache.getParentProfitLoss());
        orderFromDB.setMarginDue(orderFromCache.getMarginDue());
        orderFromDB.setDayMarginDue(orderFromCache.getDayMarginDue());
        orderFromDB.setOrdID(orderFromCache.getOrdID());
        orderFromDB.setOrigClOrdID(orderFromCache.getOrigClOrdID());

        return orderFromDB;
    }

}
