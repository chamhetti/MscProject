package lk.ac.ucsc.oms.symbol.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.BaseSymbolBean;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean;
import lk.ac.ucsc.oms.symbol.implGeneral.util.SymbolKeyManager;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This is the class which contain the implementation details of putting CS (common stock) symbols to the cache and loading
 * the symbol from cache/DB for later operations
 * <p/>
 * User: chamindah
 * Date: 11/19/12
 * Time: 4:13 PM
 */
public class CSSymbolCacheFacade extends AbstractSymbolCacheFacade {
    private static Logger logger = LogManager.getLogger(CSSymbolCacheFacade.class);
    private SysLevelParaManager sysLevelParaManager;
    private AbstractSequenceGenerator sequenceGenerator;

    /**
     * put a symbol to cache
     *
     * @param symbolBean symbol bean
     * @return key of the object at cache
     * @throws SymbolManageException error with code
     */
    public void addSymbol(CSSymbolBean symbolBean) throws SymbolManageException {
        if (symbolBean == null) {
            logger.error("CSSymbolCacheFacade::addSymbol --> Error given symbolBean is null");
            throw new SymbolManageException("CSSymbolCacheFacade::addSymbol --> Error given symbolBean is null");
        }
        try {
            cacheController.addToCache(symbolBean);
        } catch (CacheNotFoundException e) {
            logger.error("cannot add CSSymbol to the cache Error in cache !! ", e);
            throw new SymbolManageException("Error in adding the symbol", e);
        }
    }

    /**
     * update a symbol at cache
     *
     * @param symbolBean symbol bean
     * @return success or fail
     * @throws SymbolManageException error with code
     */
    public void updateSymbol(CSSymbolBean symbolBean) throws SymbolManageException {
        if (symbolBean == null) {
            logger.error("CSSymbolCacheFacade::updateSymbol --> Error given symbolBean is null");
            throw new SymbolManageException("CSSymbolCacheFacade::updateSymbol --> Error given symbolBean is null");
        }
        try {
            cacheController.modifyInCache(symbolBean);
        } catch (CacheNotFoundException e) {
            logger.error("error in updating CSSymbol in the cache ", e);
            throw new SymbolManageException("Error in updating the symbol", e);
        }
    }

    /**
     * Remove a symbol from cache
     *
     * @param symbolBean symbol bean
     * @return success or fail
     * @throws SymbolManageException error with code
     */
    public void markSymbolAsDeleted(CSSymbolBean symbolBean) throws SymbolManageException {
        if (symbolBean == null) {
            logger.error("CSSymbolCacheFacade::markSymbolAsDeleted --> Error given symbolBean is null");
            throw new SymbolManageException("CSSymbolCacheFacade::markSymbolAsDeleted --> Error given symbolBean is null");
        }
        try {
            CSSymbolBean b = (CSSymbolBean) cacheController.readObjectFromCache(symbolBean);
            b.setStatus(BaseSymbol.SymbolStatus.DELETED);
            updateSymbol(b);
        } catch (Exception e) {
            logger.error("error in mark symbol as deleted ", e);
            throw new SymbolManageException("Error in removing the symbol", e);
        }
    }

    /**
     * Get a symbol in cache using symbol key
     *
     * @param symbolKey symbol key of the format exchange_symbol_securityType
     * @return symbol object
     * @throws SymbolNotFoundException error with reason
     */
    public CSSymbolBean getSymbol(String symbolKey) throws SymbolManageException {
        if (symbolKey == null || "".equalsIgnoreCase(symbolKey)) {
            logger.error("CSSymbolCacheFacade::getSymbol --> Error given symbolKey is null or empty");
            throw new SymbolNotFoundException("CSSymbolCacheFacade::getSymbol --> Error given symbolKey is null or empty");
        }
        CSSymbolBean csSymbolBean = new CSSymbolBean(SymbolKeyManager.getSymbolFromKey(symbolKey), SymbolKeyManager.getExchangeFromKey(symbolKey));
        csSymbolBean.setPrimaryKeyObject(symbolKey);
        CacheObject co = null;
        try {
            co = cacheController.readObjectFromCache(csSymbolBean);
        } catch (OMSException e) {
            logger.debug("Symbol is not in the cache or active symbol table");
            logger.info("loading the symbol from Auxiliary table ");

        }
        return (CSSymbolBean) co;
    }



    public void initialize() throws SymbolManageException {
        //get the last id of the CSSymbol table and set it to sequence generator for id generation
        sequenceGenerator.setSequenceNumber(symbolPersistor.getLastSymbolId());
        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if ("true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }



    /**
     * Inject system level parameter manager
     *
     * @param sysLevelParaManager
     */
    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    /**
     * Set sequence generator
     *
     * @param sequenceGenerator
     */
    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
}
