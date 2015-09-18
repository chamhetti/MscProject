package lk.ac.ucsc.oms.symbol.api;

import lk.ac.ucsc.oms.symbol.api.beans.SymbolPriceData;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;



public interface SymbolPriceManager {
    /**
     * Method used to get the rial time price data related to a symbol.
     *
     * @param symbol
     * @param exchange
     * @return
     */
    SymbolPriceData getPriceDataForSymbol(String symbol, String exchange) throws SymbolPriceException;

    /**
     * Method used to update the existing symbol price data
     *
     * @param symbolPriceData
     * @return
     */
    void updateSymbolPriceData(SymbolPriceData symbolPriceData) throws SymbolPriceException;

    /**
     * Used to initialize the module at OMS startup. Start sequnce
     *
     * @return true or false
     */
    void initialize() throws SymbolPriceException;

    /**
     * give the empty symbol price data bean
     *
     * @return
     */
    SymbolPriceData getEmptySymbolPriceDataBean();

    // todo temp
    boolean insetSymbolPriceData(SymbolPriceData symbolPriceData) throws SymbolPriceException;

    /**
     * Get the symbol price block for the given symbol
     *
     * @param symbol               is the symbol bean
     * @param exchange             is the security exchange
     * @param priceType            is the symbol price type
     * @param priceBlockPercentage is the institutional price block percentage
     * @return the symbol price block
     * @throws SymbolPriceException
     */
    double getSymbolPrice(String symbol, String exchange, int priceType, double priceBlockPercentage) throws SymbolPriceException;

    /**
     * This method replace the values in the cache with the last data in DB
     *
     * @throws SymbolPriceException
     */
    void refreshCache() throws SymbolPriceException;
}
