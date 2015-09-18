package lk.ac.ucsc.oms.symbol.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User: Hetti
 * Date: 9/5/13
 * Time: 2:18 PM
 */
public class SymbolPriceCacheFacade {
    private static Logger logger = LogManager.getLogger(SymbolPriceCacheFacade.class);
    private CacheControllerInterface cacheCon;
    private CacheLoadedState cacheLoadedState;

    private SysLevelParaManager sysLevelParaManager;

    /**
     * used to add the symbol price data bean to the cache
     *
     * @param symbolPriceDataBean
     * @return
     */
    public void add(SymbolPriceDataBean symbolPriceDataBean) throws SymbolPriceException {
        if (symbolPriceDataBean == null) {
            logger.error("SymbolPriceCacheFacade::add --> Error given symbolPriceDataBean is null");
            throw new SymbolPriceException("SymbolPriceCacheFacade::add --> Error given symbolPriceDataBean is null");
        }
        symbolPriceDataBean.setPrimaryKeyObject(symbolPriceDataBean.getExchangeCode() + "-" + symbolPriceDataBean.getSymbolCode());
        try {
            cacheCon.addToCache(symbolPriceDataBean);
        } catch (CacheNotFoundException e) {
            logger.error("Error in add", e);
            throw new SymbolPriceException("error adding symbol price data to the cache ", e);
        }
    }

    /**
     * get latest price data for a symbol.
     *
     * @param symbol
     * @param exchange
     * @return
     */
    public SymbolPriceDataBean get(String symbol, String exchange) throws SymbolPriceException {
        if (symbol == null || "".equalsIgnoreCase(symbol) || exchange == null || "".equalsIgnoreCase(exchange)) {
            logger.error("given data is not sufficient to retrieve symbol price");
            throw new SymbolPriceException("given data is not sufficient to retrieve symbol price");
        }
        SymbolPriceDataBean bean = new SymbolPriceDataBean();
        bean.setPrimaryKeyObject(exchange + "-" + symbol);
        bean.setExchangeCode(exchange);
        bean.setSymbolCode(symbol);
        try {
            SymbolPriceDataBean ssb = (SymbolPriceDataBean) cacheCon.readObjectFromCache(bean);
            return ssb;
        } catch (Exception e) {
            logger.info("Error in get symbol price data");
            throw new SymbolPriceException("error in getting symbol price data for given symbol and exchange ", e);
        }
    }

    /**
     * This method is for update existing sell side broker from given sell side broker values. Broker code will not be
     * changed.
     *
     * @param symbolPriceDataBean sell side broker with values need to be changed
     * @return true if updating success, false otherwise
     */
    public void update(SymbolPriceDataBean symbolPriceDataBean) throws SymbolPriceException {
        if (symbolPriceDataBean == null) {
            logger.error("SymbolPriceCacheFacade::update --> Error given symbolPriceDataBean is null");
            throw new SymbolPriceException("SymbolPriceCacheFacade::update --> Error given symbolPriceDataBean is null");
        }
        symbolPriceDataBean.setPrimaryKeyObject(symbolPriceDataBean.getExchangeCode() + "-" + symbolPriceDataBean.getSymbolCode());
        try {
            cacheCon.readObjectFromCache(symbolPriceDataBean);
            cacheCon.modifyInCache(symbolPriceDataBean);
            logger.debug("Updated symbol price data " + symbolPriceDataBean.getPrimaryKeyObject());
        } catch (OMSException e) {
            logger.error("Error in update process", e);
            throw new SymbolPriceException("Error in updating symbol price data ", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void initialize() throws SymbolPriceException {
        loadAllSymbolPriceData();
        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if ("true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheCon.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    /**
     * This method is for load all the db entries of symbol price data into cache.
     */
    private void loadAllSymbolPriceData() throws SymbolPriceException {
        logger.info("Loading all symbol price data into cache");
        if (cacheLoadedState != CacheLoadedState.NOT_LOADED) {
            throw new SymbolPriceException("Cache load status should be not loaded for symbol price data");
        }
    }

    /**
     * This method update the object in the cache. Here it is assume that data in the DB is latest
     *
     * @param symbolPriceDataBean
     * @return
     */
    public void updateOnlyInCache(SymbolPriceDataBean symbolPriceDataBean) throws SymbolPriceException {
        logger.debug("updating symbol price data bean in the cache");
        if (symbolPriceDataBean == null) {
            logger.error("SymbolPriceCacheFacade::updateOnlyInCache --> Error given symbolPriceDataBean is null");
            throw new SymbolPriceException("SymbolPriceCacheFacade::updateOnlyInCache --> Error given symbolPriceDataBean is null");
        }
        try {
            cacheCon.updateOnlyInCache(symbolPriceDataBean);

        } catch (CacheException e) {
            logger.error("Error in updating symbol price data bean in the cache: ", e);
            throw new SymbolPriceException("Error in updating symbol price data bean in the cache ", e);
        }
    }

    /**
     * Method used to clear symbol price date from cache just before reloading from DB
     * @throws SymbolPriceException
     */
    public void clearCache()throws SymbolPriceException{
        try {
            cacheCon.clearCache();
        } catch (CacheException e) {
            logger.error("Error in updating symbol price data bean in the cache: ", e);
            throw new SymbolPriceException("Error in updating symbol price data bean in the cache ", e);
        }
    }
    /**
     * This method is for set cache controller interface
     *
     * @param cacheCon Cache controller interface
     */
    public void setCacheCon(CacheControllerInterface cacheCon) {
        this.cacheCon = cacheCon;
    }

    /**
     * get cache loaded state from cache facade
     *
     * @return cacheLoadedState
     */
    public CacheLoadedState getCacheLoadedState() {
        return cacheLoadedState;
    }

    /**
     * set cache loaded state to the order cache
     *
     * @param cacheLoadedState
     */
    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    /**
     * Inject system level parameter manager
     *
     * @param sysLevelParaManager
     */
    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }
}
