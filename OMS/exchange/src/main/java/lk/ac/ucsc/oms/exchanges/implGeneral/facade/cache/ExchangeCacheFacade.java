package lk.ac.ucsc.oms.exchanges.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean;
import lk.ac.ucsc.oms.exchanges.implGeneral.persistantImpl.ExchangePersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.sequence_generator.api.SequenceGeneratorFactory;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ExchangeCacheFacade {

    private static Logger logger = LogManager.getLogger(ExchangeCacheFacade.class);
    private CacheControllerInterface cacheController;
    private CacheLoadedState cacheLoadedState;
    private ExchangePersister exchangePersister;
    private AbstractSequenceGenerator sequenceGenerator;


    private AbstractSequenceGenerator subMarketSequence;
    private SysLevelParaManager sysLevelParaManager;
    SequenceGeneratorFactory sequenceGeneratorFactory;
    private final static String CACHE_LOAD_STATE_ERROR_MESSAGE = "Cache load status should be fully loaded for exchanges";



    public CacheLoadedState getCacheLoadedState() {
        return cacheLoadedState;
    }

    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void initialize() throws ExchangeException {
        sequenceGenerator.setSequenceNumber(exchangePersister.getLastExchangeId());
        loadExchanges();
        subMarketSequence.setSequenceNumber(exchangePersister.getLastExchangeSubMarketId());
        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }


    private void loadExchanges() throws ExchangeException {
        logger.info("Loading all exchanges into cache");
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                cacheController.populateFullCache();
            } else {
                logger.error("Cache load status should be fully loaded for exchanges data");
                throw new ExchangeException("Cache load status should be fully loaded for exchanges data");
            }
        } catch (CacheException e) {
            logger.error("Error in loading all exchanges: ", e);
            throw new ExchangeException("Issue occurred in loading all exchanges", e);
        }
    }


    public String getMubasherExchangeCode(String exDestination, String exchange) throws ExchangeException {
        logger.info("Getting the exchange code using exchange {} and tag100 {} from fix message", exchange,
                exDestination);
        if ((exDestination == null || ("").equals(exDestination)) && (exchange == null || ("").equals(exchange))) {
            logger.error("exchange code can't be null or empty");
            throw new ExchangeException("exchange code can't be null or empty");
        }
        return exchangePersister.getMubasherExchangeCodes(exDestination, exchange);

    }

    public void putExchange(ExchangeBean exchangeBean) throws ExchangeException {
        try {
            cacheController.addToCache(exchangeBean);
        } catch (CacheNotFoundException e) {
            logger.error("Issue in adding exchange", e);
            throw new ExchangeException("Error in adding the Exchange", e);
        }
    }


    public void updateExchange(ExchangeBean exchangeBean) throws ExchangeException {
        try {
            cacheController.modifyInCache(exchangeBean);
        } catch (CacheNotFoundException e) {
            logger.error("Issue in update exchange", e);
            throw new ExchangeException("Error in updating the exchange", e);

        }
    }


    public void markExchangeAsDeleted(ExchangeBean exchangeBean) throws ExchangeException {
        logger.info("Mark exchange as delete-{} ", exchangeBean);
        try {
            ExchangeBean eb = (ExchangeBean) cacheController.readObjectFromCache(exchangeBean);
            eb.setStatus(Exchange.ExchangeStatus.DELETED);
            cacheController.modifyInCache(eb);
        } catch (Exception e) {
            logger.error("Error in removing the exchange", e);
            throw new ExchangeException("Error in removing the exchange", e);
        }
    }


    public ExchangeBean getExchange(String exchangeCode) throws ExchangeException {
        logger.info("Get exchange using exchange code-{} ", exchangeCode);
        ExchangeBean exchangeBean = new ExchangeBean(exchangeCode);
        exchangeBean.setPrimaryKeyObject(exchangeCode);
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(exchangeBean);
            return (ExchangeBean) co;
        } catch (Exception e) {
            logger.error("Exchange not found in cache or DB", e);
            throw new ExchangeException("Exchange not found in cache or DB", e);
        }

    }


    public List<CacheObject> getAllCachedExchanges() throws ExchangeException {
        logger.info("Get all exchanges");
        try {
            return cacheController.getAllCache();
        } catch (Exception e) {
            logger.error("Error while loading exchanges from cache", e);
            throw new ExchangeException("Error occurred while loading exchanges from the cache.", e);
        }
    }

    public List<String> getAllExchangeCodes() throws ExchangeException {
        logger.info("Getting all exchange codes");
        List<String> exchangeCodeList = new ArrayList<>(0);
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                List<CacheObject> cacheObjectList = cacheController.getAllCache();
                for (CacheObject obj : cacheObjectList) {
                    exchangeCodeList.add(((Exchange) obj).getExchangeCode());
                }
            } else {
                logger.error(CACHE_LOAD_STATE_ERROR_MESSAGE);
                throw new ExchangeException(CACHE_LOAD_STATE_ERROR_MESSAGE);
            }

        } catch (Exception e) {
            logger.error("Problem in getting all exchanges", e);
            throw new ExchangeException("Issue occurred in loading exchanges", e);
        }
        logger.debug("exchange code list -{}", exchangeCodeList);
        return exchangeCodeList;
    }

    public List<Exchange> getAllExchanges() throws ExchangeException {
        logger.info("Getting all exchanges");
        List<Exchange> exchangeList = new ArrayList<>(0);
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                List<CacheObject> cacheObjectList = cacheController.getAllCache();
                for (CacheObject obj : cacheObjectList) {
                    exchangeList.add((Exchange) obj);
                }
            } else {
                logger.error(CACHE_LOAD_STATE_ERROR_MESSAGE);
                throw new ExchangeException(CACHE_LOAD_STATE_ERROR_MESSAGE);
            }

        } catch (Exception e) {
            logger.error("Problem in getting all exchanges", e);
            throw new ExchangeException("Issue occurred in loading exchanges", e);
        }
        logger.debug("Price news provider list -{}", exchangeList);
        return exchangeList;
    }


    public List<Exchange> getExchangeList() throws ExchangeException {
        List<Exchange> allExchanges = new ArrayList<>();
        List<CacheObject> exchangeBeans = null;
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                exchangeBeans = cacheController.getAllCache();
            } else {
                logger.error(CACHE_LOAD_STATE_ERROR_MESSAGE);
                throw new ExchangeException(CACHE_LOAD_STATE_ERROR_MESSAGE);
            }

            if (exchangeBeans == null || exchangeBeans.isEmpty()) {
                loadExchanges();
                exchangeBeans = cacheController.getAllCache();
            }

        } catch (Exception e) {
            logger.error("Issue in exchange cache", e);
            throw new ExchangeException("Exchange cache error", e);
        }
        for (CacheObject cO : exchangeBeans) {
            allExchanges.add((Exchange) cO);
        }
        return allExchanges;
    }

    public ExchangePersister getExchangePersister() {
        return exchangePersister;
    }

    public void setExchangePersister(ExchangePersister exchangePersister) {
        this.exchangePersister = exchangePersister;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void setSubMarketSequence(AbstractSequenceGenerator subMarketSequence) {
        this.subMarketSequence = subMarketSequence;
    }
}
