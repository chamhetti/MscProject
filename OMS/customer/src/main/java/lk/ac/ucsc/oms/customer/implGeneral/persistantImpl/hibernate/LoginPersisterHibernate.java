package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.customer.api.exceptions.CustomerLoginException;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.LoginPersister;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginPersisterHibernate implements LoginPersister {
    private static Logger logger = LogManager.getLogger(LoginPersisterHibernate.class);
    private SessionFactory sessionFactory;

    public LoginPersisterHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String getCustomerNumber(String loginName) throws CustomerLoginException {
        String customerNumber = null;
        Session session = sessionFactory.openSession();
        try {
            String hql = "select C.loginName FROM lk.ac.ucsc.oms.customer.implGeneral.beans.customer.LoginProfileBean C WHERE C.loginAlias = :alias OR  C.loginName= :cusNumber";
            Query query = session.createQuery(hql);
            query.setParameter("alias", loginName);
            query.setParameter("cusNumber", loginName);
            customerNumber = (String) query.uniqueResult();
            logger.info("Loaded customer number:{}", customerNumber);
        } catch (Exception e) {
            logger.error("Error in Getting Customer Number for Given Login Name", e);
            throw new CustomerLoginException("Error in Getting Customer Number for Given Login Name", e);
        } finally {
            session.close();
        }
        return customerNumber;
    }
}
