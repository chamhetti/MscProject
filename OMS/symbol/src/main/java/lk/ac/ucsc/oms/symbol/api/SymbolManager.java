package lk.ac.ucsc.oms.symbol.api;

import lk.ac.ucsc.oms.symbol.api.beans.*;
import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;

import java.util.List;
import java.util.Map;


public interface SymbolManager {

    /**
     * Method use to get the symbol from the cache or DB. This symbol bean contain all the information about
     * the symbol those need for processing at OMS level. While loading it does not consider the property like
     * trading enable status or status of the symbol. It is expected to handle those at caller level as they wan
     *
     * @param symbol       symbol code  like EMAAR
     * @param exchange     exchange code like DFM
     * @param securityType type of the security
     * @return symbol bean of give security type.
     * @throws SymbolNotFoundException Symbol not in the DB or any error occurs
     */
    BaseSymbol getSymbol(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException;


    /**
     * Method use to store a new symbol at cache and DB.
     *
     * @param symbolBean populated symbol bean of required security type
     * @return symbol_id
     * @throws SymbolManageException any error happen during the execution
     */
    long addSymbol(BaseSymbol symbolBean) throws SymbolManageException;

    /**
     * Method use to update the existing symbol at cache or DB
     *
     * @param symbolBean symbol bean with necessary changes
     * @return true or false
     * @throws SymbolManageException any error happen during the execution
     */
    void updateSymbol(BaseSymbol symbolBean) throws SymbolManageException;

    /**
     * Method used to make the status of the symbol at DB as deleted and removed from the cache.
     *
     * @param symbolBean symbol bean need to be removed from cache
     * @return true or false
     * @throws SymbolManageException any error happen during the execution
     */
    void markSymbolAsDeleted(BaseSymbol symbolBean) throws SymbolManageException;

    /**
     * Get the all the active symbol list from DB
     *
     * @return List<CacheObject>
     * @throws SymbolManageException error with code
     */

    List<BaseSymbol> getActiveSymbolList() throws SymbolManageException;


    /**
     * method used to get the symbol's security type when it is not given with a request
     *
     * @param symbolCode String symbol code
     * @param exchange   String exchange code
     * @return Security type enum
     */
    BaseSymbol.SecurityType getSecurityType(String symbolCode, String exchange) throws SymbolManageException;




    /**
     * Institution wise sharia enable or not for the symbol. This bean can be use to set the sharia information
     * for a symbol
     *
     * @param securityType security type of the symbol
     * @return ShariaInfo
     */
    ShariaInfo getEmptyShariaInfoBean(BaseSymbol.SecurityType securityType);

    /**
     * Bean that can be used to set the symbol access level at institution wise. Then those symbols are not
     * allowed to trade for the customers on that institutions
     *
     * @param securityType security type of the symbol
     * @return BlackListInfo list of black listed symbol
     */
    BlackListInfo getEmptyBlackListInfoBean(BaseSymbol.SecurityType securityType);

    /**
     * get the interface that can be used to set the margin trading related information of the symbol
     *
     * @param securityType security type of the symbol
     * @return SymbolMarginInfo  margin info of the symbol
     */
    //SymbolMarginInfo getEmptySymbolMarginInfoBean(BaseSymbol.SecurityType securityType);

    /**
     * Give the symbol description bean which hold the language wise short and long description of a symbol
     *
     * @param securityType securityType
     * @return SymbolDescription symbol descriptions in supported languages
     */
    SymbolDescription getEmptySymbolDescriptionBean(BaseSymbol.SecurityType securityType);

    /**
     * Method use to get the empty common stock
     *
     * @param exchange exchange code of the symbol
     * @param symbol   symbol code
     * @return CSSymbol CS symbol api
     */
    CSSymbol getEmptyCSSymbolBean(String exchange, String symbol);



    /**
     * Used to initialize the module at OMS startup. Start sequnce
     * generators for CS and OPt symbols
     * This includes: setting last persisted id
     *
     * @return true or false
     */
    void initialize() throws SymbolManageException;


}
