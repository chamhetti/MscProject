package lk.ac.ucsc.oms.symbol.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl.SymbolPersistor;

import java.util.List;
import java.util.Map;

/**
 * Abstract cache facade for symbol cache facades
 * <p/>
 * User: thilangaj
 * Date: 4/30/13
 * Time: 10:01 AM
 */
public abstract class AbstractSymbolCacheFacade {
    protected CacheControllerInterface cacheController;
    protected SymbolPersistor symbolPersistor;

    /**
     * Facility to manually execute persist cache
     *
     * @throws lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException
     *          error with reason
     */
    public void persistSymbolCacheChanges() throws SymbolManageException {
        try {
            cacheController.persistCacheAsBulk();
        } catch (Exception e) {
            throw new SymbolManageException("Error occurred while persisting", e);
        }

    }

    /**
     * method to get all symbols from persister
     * @return
     * @throws SymbolManageException
     */
    public List<BaseSymbol> getAllSymbols() throws SymbolManageException{
        return symbolPersistor.getAllSymbols();
    }



    /**
     * set the cache controller which contain the detail of managing cache
     *
     * @param cacheController set the cache controller implementation such as infinispan oracle coherence etc
     */
    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    /**
     * this method is to set symbol persister to the abstract cache facade
     *
     * @param symbolPersistor
     */
    public void setSymbolPersistor(SymbolPersistor symbolPersistor) {
        this.symbolPersistor = symbolPersistor;
    }
}
