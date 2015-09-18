package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.orderMgt.api.OrderSearchType;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.OrderHistoryPersister;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class OrderHistoryHibernatePersister implements OrderHistoryPersister {
    private static Logger logger = LogManager.getLogger(OrderHistoryHibernatePersister.class);

    private SessionFactory sessionFactory;

    public OrderHistoryHibernatePersister(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> filterOrdersByFieldAndValue(OrderSearchType searchType, String value) throws OrderException {
        logger.info("Search Orders for given Field and value, Field : " + searchType + ", Value : " + value);
        CacheObject historyOrder;
        List<Order> returnOrderList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            List results = session.createCriteria(OrderBean.class).add(Restrictions.eq(getColumnName(searchType), value)).list();
            for (Object ob : results) {
                historyOrder = (OrderBean) ob;
                returnOrderList.add((Order) historyOrder);
            }
            logger.debug("Filter Order successful for given Field and Value, Field : " + searchType + ", Value : " + value);
        } catch (HibernateException e) {
            logger.error("Error in filterOrdersByFieldAndValue :", e);
            throw new OrderException("Error in filter order history by field and value", e);
        } finally {
            session.close();
        }
        return returnOrderList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> filterOrdersForAllGivenTypeValues(Map<OrderSearchType, String> criteriaMap) throws OrderException {
        logger.info("Search Orders for given SearchType values");
        CacheObject hOrder;
        List<Order> returnOrderList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(OrderBean.class);
            Set s = criteriaMap.entrySet();
            Iterator it = s.iterator();
            while (it.hasNext()) {
                Map.Entry m = (Map.Entry) it.next();
                String column = getColumnName((OrderSearchType) m.getKey());
                criteria.add(Restrictions.eq(column, m.getValue()));
                criteria.add(Restrictions.conjunction());
                logger.debug("Key :" + column + "  Value :" + m.getValue());
            }
            List results = criteria.list();
            for (Object ob : results) {
                hOrder = (OrderBean) ob;
                returnOrderList.add((Order) hOrder);
            }

            logger.debug("Search successful for given SearchType criteria, list size : " + results.size());
        } catch (HibernateException e) {
            logger.error("Error in filterOrdersForAllGivenTypeValues :", e);
            throw new OrderException("Error in filter order history by all field and value pairs", e);
        } finally {
            session.close();
        }
        return returnOrderList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> filterOrdersForEitherGivenTypeValues(Map<OrderSearchType, String> criteriaMap) throws OrderException {
        logger.info("Search Orders for Either given SearchType criteria");
        CacheObject hOrder = null;
        List<Order> returnOrderList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(OrderBean.class);
            Set s = criteriaMap.entrySet();
            Iterator it = s.iterator();
            Junction junction = Restrictions.disjunction();
            while (it.hasNext()) {
                Map.Entry m = (Map.Entry) it.next();
                String column = getColumnName((OrderSearchType) m.getKey());
                junction.add(Restrictions.eq(column, m.getValue()));
                logger.debug("Key :" + column + "  Value :" + m.getValue());
            }
            criteria.add(junction);
            List results = criteria.list();
            for (Object ob : results) {
                hOrder = (OrderBean) ob;
                returnOrderList.add((Order) hOrder);
            }
            logger.debug("Search successful for Either given SearchType criteria, list size : " + results.size());
        } catch (HibernateException e) {
            logger.error("Error in filterOrdersForAllGivenTypeValues :", e);
            throw new OrderException("Error in filter order history by either field and value", e);
        } finally {
            session.close();
        }
        return returnOrderList;
    }


    private String getColumnName(OrderSearchType searchType) {
        String propertyName = null;
        switch (searchType) {
            case EXCHANGE:
                propertyName = "exchange";
                break;
            case ORDER_NO:
                propertyName = "orderNo";
                break;
            case ORDER_STATUS:
                propertyName = "orderStatus";
                break;
            case SYMBOL:
                propertyName = "symbol";
                break;
            case INSTITUTION_ID:
                propertyName = "institutionID";
                break;
            case SECURITY_ACCOUNT:
                propertyName = "securityAccount";
                break;
            default:
                return null;
        }
        return propertyName;
    }

}
