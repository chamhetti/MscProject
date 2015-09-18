package lk.ac.ucsc.oms.system_configuration.implGeneral.persitantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import lk.ac.ucsc.oms.system_configuration.implGeneral.bean.SysLevelParamBean;
import lk.ac.ucsc.oms.system_configuration.implGeneral.persitantImpl.SysLevelParamPersister;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SysLevelParamPersisterHibernate extends AbstractCachePersister implements SysLevelParamPersister {
    private static Logger logger = LogManager.getLogger(SysLevelParamPersisterHibernate.class);
    private static Date lastUpdateTime;

    /**
     * @param sessionFactory SessionFactory
     */
    public SysLevelParamPersisterHibernate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update(CacheObject co) throws SysConfigException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                return;
            }
            logger.debug("updating the SysLevelParam bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("SysLevelParam bean can't update", e);
            throw new SysConfigException("SysLevelParam bean can't update", e);
        } finally {
            session.close();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(CacheObject co) throws SysConfigException {
        Transaction tx;

        Session session = sessionFactory.openSession();
        try {

            logger.debug("Inserting the SysLevelParam bean:{} ", co);
            if (co == null) {
                return;
            }
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("Inserting SysLevelParam bean finished");
        } catch (Exception e) {
            logger.error("problem in adding sysLevelParam", e);
            throw new SysConfigException("problem in adding sysLevelParam", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheObject load(CacheObject co) {
        SysLevelParamBean sysLevelParamBean = null;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                return null;
            }

            SystemParameter sysLevelParam = (SystemParameter) co.getPrimaryKeyObject();
            logger.info("Loading the sysLevelParam with sysLevelParam code:{} ", sysLevelParam);
            if (sysLevelParam == null) {
                logger.warn("SysLevelParam info provided not enough to load from DB");
                return null;
            }
            String hql = "FROM lk.ac.ucsc.oms.system_configuration.implGeneral.bean.SysLevelParamBean C WHERE C.paramId = :sysLevelParam";
            Query query = session.createQuery(hql);
            query.setParameter("sysLevelParam", sysLevelParam);
            sysLevelParamBean = (SysLevelParamBean) query.uniqueResult();
            if (sysLevelParamBean != null) {
                sysLevelParamBean.setPrimaryKeyObject(sysLevelParamBean.getParamId());
            }
            logger.info("Loaded SysLevelParam:{}", sysLevelParamBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return sysLevelParamBean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all the SysLevelParam from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {

            String hql = "FROM lk.ac.ucsc.oms.system_configuration.implGeneral.bean.SysLevelParamBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                SysLevelParamBean sysLevelParamBean = (SysLevelParamBean) cgb;
                sysLevelParamBean.setPrimaryKeyObject(sysLevelParamBean.getParamId());
                cashObjectLst.add(sysLevelParamBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.debug("Loaded SysLevelParams list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
    public void remove(CacheObject co) throws SysConfigException {
        logger.info("Deleting SysLevelParam bean ");
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                return;
            }
            logger.debug("Deleting the SysLevelParam bean: {}", co);
            tx = session.beginTransaction();
            session.delete(co);
            tx.commit();
            logger.info("Deleting finished");
        } catch (Exception e) {
            logger.error("SysLevelParam bean can't delete", e);
            throw new SysConfigException("SysLevelParam bean can't delete", e);
        } finally {
            session.close();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeToHistoryMode() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeToPresentMode() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertList(List<CacheObject> objectList) {
        logger.debug("Method will not be used at this moment");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.debug("Method will not be used at this moment");
    }

    private long getTotalRecordCount() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "select count(*) FROM lk.ac.ucsc.oms.system_configuration.implGeneral.bean.SysLevelParamBean ";
            Query query = session.createQuery(hql);
            Long totalCount = (Long) query.uniqueResult();
            return totalCount;
        } catch (Exception e) {
            logger.error("Error occurred  while getting total record count ", e);
        } finally {
            session.close();
        }
        return 0;
    }


}
