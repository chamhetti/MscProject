package lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl;


import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;

import java.util.List;
import java.util.Map;


public interface SymbolPersistor extends CachePersister {

    /**
     * Method use to find out the symbol key using ISIN
     *
     * @param isin ISIN of the symbol
     * @return  symbol key
     * @throws SymbolNotFoundException  exception with reason
     */
    String getSymbolKeyFromISIN(String isin) throws SymbolNotFoundException;

    /**
     * Delete a symbol from DB. This method is used to remove the inactive symbols from
     * active symbol table.
     *
     * @param symbol symbol object need to be deleted
     * @return  success or fail
     */
    void deleteFromDB(BaseSymbol symbol) throws SymbolManageException;

    /**
     * Method use to find out the symbol key using RIC
     *
     * @param ric RIC of the symbol
     * @return symbol key
     * @throws SymbolNotFoundException exception with reason
     */
    String getSymbolKeyFromRIC(String ric) throws SymbolNotFoundException;

    /**
     * get the latest symbol id from DB
     *
     * @return lastSymbolId as a string
     */
    String getLastSymbolId() throws SymbolManageException;

    /**
     * Method use to find all the symbols related to given exchange
     *
     * @param exchange of the symbol
     * @return a list of BaseSymbol objects
     */
    List<BaseSymbol> getSymbolsForExchange(String exchange) throws SymbolManageException;

    /**
     * method to get all the symbols
     *
     * @return
     * @throws SymbolManageException
     */
    List<BaseSymbol> getAllSymbols() throws SymbolManageException;


   }
