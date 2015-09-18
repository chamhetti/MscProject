package lk.ac.ucsc.oms.sequence_generator.implGeneral.facade.cache;


import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.sequence_generator.implGeneral.bean.SequenceBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SequenceGeneratorCacheFacade {
    private static Logger logger = LogManager.getLogger(SequenceGeneratorCacheFacade.class);
    private CacheControllerInterface cacheController;


    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }


    public int getNumber(String sequenceGenerator) throws OMSException {
        SequenceBean sequenceBean = new SequenceBean();
        sequenceBean.setPrimaryKeyObject(sequenceGenerator);
        SequenceBean resultBean = (SequenceBean) cacheController.readObjectFromCache(sequenceBean);
        return resultBean.getNumber();
    }

    public void updateNumber(String sequenceGenerator, int number) {
        SequenceBean sequenceBean = new SequenceBean();
        sequenceBean.setPrimaryKeyObject(sequenceGenerator);
        sequenceBean.setNumber(number);
        try {
            SequenceBean sequenceBean1 = (SequenceBean) cacheController.readObjectFromCache(sequenceBean);
            if (sequenceBean1 != null) {
                sequenceBean1.setNumber(number);
                cacheController.modifyInCache(sequenceBean1);
            } else {
                cacheController.addToCache(sequenceBean);
            }
        } catch (OMSException e) {
            try {
                cacheController.addToCache(sequenceBean);

            } catch (CacheNotFoundException e1) {
                logger.error("Failed to update sequence number", e);
            }
        }
    }
}
