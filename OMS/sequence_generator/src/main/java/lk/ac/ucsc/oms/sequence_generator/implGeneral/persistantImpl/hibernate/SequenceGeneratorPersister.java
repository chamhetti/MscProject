package lk.ac.ucsc.oms.sequence_generator.implGeneral.persistantImpl.hibernate;


import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class SequenceGeneratorPersister implements CachePersister {
    private static Logger logger = LogManager.getLogger(SequenceGeneratorPersister.class);



    /**
     * {@inheritDoc}
     */
    @Override
    public void update(CacheObject co) throws OMSException {
        throw new OMSException("Unsupported operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(CacheObject co) throws OMSException {
        throw new OMSException("Unsupported operation");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(CacheObject co) throws OMSException {
        throw new OMSException("Unsupported operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeToHistoryMode() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeToPresentMode() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertList(List<CacheObject> objectList) {
        logger.error("Unsupported operation");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.error("Unsupported operation");
    }


}
