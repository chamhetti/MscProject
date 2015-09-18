package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;


import lk.ac.ucsc.oms.customer.api.beans.AccountType;
import lk.ac.ucsc.oms.customer.api.beans.CustomerSearchType;
import lk.ac.ucsc.oms.customer.api.beans.CustomerType;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.CustomerPersister;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerPersisterHibernate extends AbstractCachePersister implements CustomerPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(CustomerPersisterHibernate.class);
    private static Date lastUpdateTime;
    private SessionFactory searchSessionFactory;


    public CustomerPersisterHibernate(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }


    public void setSearchSessionFactory(SessionFactory searchSessionFactory) {
        this.searchSessionFactory = searchSessionFactory;
    }

    @Override
    public void update(CacheObject co) throws CustomerException {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                logger.error("Customer bean can't be null");
                throw new CustomerException("Customer bean can't be null");
            }
            logger.debug("updating the Customer bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("Customer bean can't update", e);
            throw new CustomerException("Customer bean can't update", e);
        } finally {
            session.close();
        }

    }

    @Override
    public void insert(CacheObject co) throws CustomerException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            logger.debug("Inserting the Customer bean:{} ", co);
            if (co == null) {
                logger.error("Customer bean can't be null");
                throw new CustomerException("Customer bean can't be null");
            }
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting Customer bean finished");
        } catch (Exception e) {
            logger.error("problem in adding customer", e);
            throw new CustomerException("problem in adding customer", e);
        } finally {
            session.close();
        }
    }

    @Override
    public CacheObject load(CacheObject co) throws CustomerException {
        CustomerBean customerBean = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                logger.error("Customer bean can't be null");
                throw new CustomerException("Customer bean can't be null");
            }

            String customerNumber = (String) co.getPrimaryKeyObject();
            logger.info("Loading the customer with customer code:{} ", customerNumber);
            if (customerNumber == null || "".equals(customerNumber)) {
                logger.warn("Customer info provided not enough to load from DB");
                throw new CustomerException("Customer bean can't be null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean C WHERE C.customerNumber = :customer";
            Query query = session.createQuery(hql);
            query.setParameter("customer", customerNumber);
            customerBean = (CustomerBean) query.uniqueResult();
            if (customerBean != null) {
                customerBean.setPrimaryKeyObject(customerBean.getCustomerNumber());
            }
            logger.info("Loaded Customer:{}", customerBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CustomerException("Error in executing Hibernate Query for loading customer", e);
        } finally {
            session.close();
        }
        return customerBean;
    }

    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all the Customer from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                CustomerBean customerBean = (CustomerBean) cgb;
                customerBean.setPrimaryKeyObject(customerBean.getCustomerNumber());
                cashObjectLst.add(customerBean);
            }
            logger.debug("Loaded Customers list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }


    @Override
    public void remove(CacheObject co) throws CustomerException {
        logger.info("Removing the Customer:{} from DB", co);
        if (co == null) {
            throw new CustomerException("The Customer to remove can't be null");
        }
        CustomerBean customerBean = (CustomerBean) load(co);
        customerBean.setStatus(RecordStatus.DELETED);
        update(customerBean);

    }

    @Override
    public void deleteFromDB(CustomerBean customer) throws CustomerException {
        logger.info("Deleting Customer bean ");
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (customer == null) {
                throw new CustomerException("The Customer to remove can't be null");
            }
            logger.debug("Deleting the Customer bean: {}", customer);
            tx = session.beginTransaction();
            session.delete(customer);
            tx.commit();
            logger.info("Deleting finished");
        } catch (Exception e) {
            logger.error("Customer bean can't delete", e);
            throw new CustomerException("Error in Removing Customer From DB", e);
        } finally {
            session.close();
        }

    }

    @Override
    public String getLastCustomerId() throws CustomerException {
        logger.info("Loading all the Customer codes from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select max(C.customerId) FROM lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean C ";
            Query query = session.createQuery(hql);

            Long results = (Long) query.uniqueResult();
            if (results != null) {
                return results.toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CustomerException("Error in Getting Last Customer Id", e);
        } finally {
            session.close();
        }

    }

    @Override
    public CustomerBean loadByCustomerNumber(String customerNumber) throws CustomerException {
        CustomerBean customerBean;
        Session session = realTimeSessionFactory.openSession();
        try {
            logger.info("Loading the customer with customer Id:{} ", customerNumber);
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean C WHERE C.customerNumber = :customerNumber";
            Query query = session.createQuery(hql);
            query.setParameter("customerNumber", customerNumber);
            customerBean = (CustomerBean) query.uniqueResult();
            if (customerBean != null) {
                customerBean.setPrimaryKeyObject(customerBean.getCustomerNumber());
            }
            logger.info("Loaded Customer:{}", customerBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CustomerException("Error in Getting Customer from Customer Number", e);
        } finally {
            session.close();
        }
        return customerBean;
    }

    @Override
    public List<Customer> getCustomerList(CustomerSearchType customerSearchType, String value) throws CustomerException {
        logger.info("Loading Customer list with a customer search type from DB");
        List<Customer> customerList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean C ";

        try {
            Query query;
            if (customerSearchType != null && value != null && !"".equals(value)) {
                hql = hql + createQueryForColumnFilter(customerSearchType, value);
                query = session.createQuery(hql);
                if (customerSearchType == CustomerSearchType.CUSTOMER_TYPE) {
                    query.setParameter("customerType", CustomerType.getEnum(Integer.parseInt(value)));
                } else if (customerSearchType == CustomerSearchType.ACCOUNT_TYPE) {
                    query.setParameter("accountType", AccountType.getEnum(Integer.parseInt(value)));
                } else if (customerSearchType == CustomerSearchType.RECORD_STATUS) {
                    query.setParameter("status", RecordStatus.getEnum(Integer.parseInt(value)));
                }
            } else {
                query = session.createQuery(hql);
            }

            List results = query.list();

            for (Object cgb : results) {
                CustomerBean customerBean = (CustomerBean) cgb;
                customerList.add(customerBean);
            }
            logger.debug("Loaded Customers list of size:{} and list:{}", customerList.size(), customerList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CustomerException("Error in Getting Customer List Using CustomerSearchType and Value", e);
        } finally {
            session.close();
        }
        return customerList;
    }

    private String createQueryForColumnFilter(CustomerSearchType customerSearchType, String value) {
        StringBuilder hql = new StringBuilder("");
        String wildCardText = "%";

        switch (customerSearchType) {
            case CUSTOMER_NUMBER:
                hql.append("WHERE C.customerNumber LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case FIRST_NAME:
                hql.append("WHERE C.personalProfile.firstName LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case LAST_NAME:
                hql.append("WHERE C.personalProfile.lastName LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case INST_CODE:
                hql.append("WHERE C.institutionCode LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case CUSTOMER_TYPE:
                hql.append("WHERE C.customerType = :customerType");
                break;
            case ACCOUNT_TYPE:
                hql.append("WHERE C.accountType = :accountType");
                break;
            case NATIONALITY:
                hql.append("WHERE C.personalProfile.nationality LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case EMAIL:
                hql.append("WHERE C.personalProfile.email LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case MOBILE_NO:
                hql.append("WHERE C.personalProfile.mobile LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case OFFICE_TELE:
                hql.append("WHERE C.personalProfile.officeTele LIKE '" + wildCardText + value + wildCardText + "' ");
                break;
            case RECORD_STATUS:
                hql.append("WHERE C.status = :status");
                break;
            default:
                hql.append("");
                break;
        }
        return hql.toString();
    }

    @Override
    public void insertList(List<CacheObject> objectList) {
        if (objectList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            CustomerBean customerBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                customerBean = (CustomerBean) co;
                session.save(customerBean);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            long endTime = System.currentTimeMillis();
            lastUpdateTime = new Date();
            logger.debug("Time to commit customer insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: Customer Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        if (updateList == null) {
            return;
        }
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            CustomerBean customerBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                customerBean = (CustomerBean) co;
                session.saveOrUpdate(customerBean);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            long endTime = System.currentTimeMillis();
            lastUpdateTime = new Date();
            logger.debug("Time to commit customer updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Updating Process: Customer Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

}
