package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


public class OrderKeyHibernatePersister extends AbstractCachePersister {

    public OrderKeyHibernatePersister(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update(CacheObject co) throws OMSException {
        throw new OMSException("Operation is not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(CacheObject co) throws OMSException {
        throw new OMSException("Operation is not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheObject load(CacheObject co) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CacheObject> loadAll() {
        return new ArrayList<>();
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(CacheObject co) throws OMSException {
        throw new OMSException("Operation is not implemented");
    }

    private long getTotalRecordCount() {
        return 0;
    }

}
