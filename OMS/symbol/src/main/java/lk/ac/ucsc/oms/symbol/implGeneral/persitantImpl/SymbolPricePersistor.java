package lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl;


import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean;

import java.util.List;


public interface SymbolPricePersistor extends CachePersister {

    /**
     * Method use to find all loaded price data from datasource
     *
     * @return a list of BaseSymbol objects
     * @throws SymbolPriceException
     */
    List<SymbolPriceDataBean> getLoadedPriceDataList() throws SymbolPriceException;

    /**
     * method that set the cache loaded status as 1
     */
    void reSetCacheLoadedStatus();
}
