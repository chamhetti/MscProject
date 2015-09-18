package lk.ac.ucsc.oms.symbol.implGeneral.facade;


import lk.ac.ucsc.oms.symbol.api.SymbolPriceManager;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolPriceData;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean;
import lk.ac.ucsc.oms.symbol.implGeneral.facade.cache.SymbolPriceCacheFacade;
import lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl.hibernate.SymbolPricePersisterHibernate;

import java.util.List;


public class SymbolPriceManagerFacade implements SymbolPriceManager {
    private SymbolPriceCacheFacade symbolPriceCacheFacade;
    private SymbolPricePersisterHibernate symbolPricePersisterHibernate;
    private static final int CONST_PRICE_BLOCK = 10000; // default value to validate the price block amount

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolPriceData getPriceDataForSymbol(String symbol, String exchange) throws SymbolPriceException {
        return symbolPriceCacheFacade.get(symbol, exchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSymbolPriceData(SymbolPriceData symbolPriceData) throws SymbolPriceException {
        symbolPriceCacheFacade.update((SymbolPriceDataBean) symbolPriceData);
    }

    public void initialize() throws SymbolPriceException {
        symbolPriceCacheFacade.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolPriceData getEmptySymbolPriceDataBean() {
        return new SymbolPriceDataBean();
    }

    // todo temp
    @Override
    public boolean insetSymbolPriceData(SymbolPriceData symbolPriceData) throws SymbolPriceException {
        symbolPriceCacheFacade.add((SymbolPriceDataBean) symbolPriceData);
        return true;
    }

    @Override
    public double getSymbolPrice(String symbol, String exchange, int priceType, double priceBlockPercentage) throws SymbolPriceException {
        double priceBlk = 0.0;
        SymbolPriceData symbolPriceData = getPriceDataForSymbol(symbol, exchange);
        //according to the price type get the price block value
        switch (priceType) {
            case 1:
                priceBlk = symbolPriceData.getBestBidPrice() * (1 + priceBlockPercentage);
                break;
            case 2:
                priceBlk = symbolPriceData.getMax() * (1 + priceBlockPercentage);
                break;
            case 3:
                priceBlk = symbolPriceData.getBestBidPrice() * (1 + priceBlockPercentage);
                break;
            case 4:
                priceBlk = symbolPriceData.getBestAskPrice() * (1 + priceBlockPercentage);
                break;
            case 5:
                if (symbolPriceData.getLastTradePrice() > 0) {
                    priceBlk = symbolPriceData.getLastTradePrice() * (1 + priceBlockPercentage);
                } else {
                    priceBlk = symbolPriceData.getPreviousClosed() * (1 + priceBlockPercentage);
                }
                break;
            default:
                break;
        }
        if (priceBlk <= 0 || priceBlk >= CONST_PRICE_BLOCK) {
            priceBlk = symbolPriceData.getMax();

            if (priceBlk > CONST_PRICE_BLOCK) {
                priceBlk = 0;
            }
        }
        if (priceBlk <= 0) {
            priceBlk = symbolPriceData.getPreviousClosed();
        }
        if (priceBlk <= 0) {
            priceBlk = symbolPriceData.getLastTradePrice();
        }
        if (priceBlk <= 0) {
            priceBlk = symbolPriceData.getBestBidPrice();
        }
        if (priceBlk <= 0) {
            priceBlk = symbolPriceData.getBestAskPrice();
        }

        return priceBlk;
    }

    /**
     * This method replace the values in the cache with the last data in DB
     *
     * @return
     */
    @Override
    public void refreshCache() throws SymbolPriceException {
        List<SymbolPriceDataBean> symbolPriceDataBeans = symbolPricePersisterHibernate.getLoadedPriceDataList();
        symbolPriceCacheFacade.clearCache();
        for (SymbolPriceDataBean spdb : symbolPriceDataBeans) {
            symbolPriceCacheFacade.updateOnlyInCache(spdb);
        }
    }

    /**
     * Inject the symbol price data cache
     *
     * @param symbolPriceCacheFacade
     */
    public void setSymbolPriceCacheFacade(SymbolPriceCacheFacade symbolPriceCacheFacade) {
        this.symbolPriceCacheFacade = symbolPriceCacheFacade;
    }

    /**
     * Inject the symbol price persistor
     *
     * @param symbolPricePersisterHibernate
     */
    public void setSymbolPricePersisterHibernate(SymbolPricePersisterHibernate symbolPricePersisterHibernate) {
        this.symbolPricePersisterHibernate = symbolPricePersisterHibernate;
    }
}
