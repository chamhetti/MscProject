package lk.ac.ucsc.oms.symbol.api;

import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolValidationReply;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;


public interface SymbolValidator {
    /**
     * check whether symbol is a approved and trading enable one.
     *
     *
     * @param symbol string symbol code
     * @param exchange string exchange code
     * @return symbol validation reply api bean
     * @throws SymbolNotFoundException
     */
    SymbolValidationReply isValidSymbol(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException;

    /**
     * check the order qty against the min order qty for the symbol
     *
     *
     * @param symbol string symbol code
     * @param exchange string exchange code
     * @param securityType enum security type
     * @param ordQty double order quantity
     * @return symbol validation reply api bean
     * @throws SymbolNotFoundException
     */
    SymbolValidationReply isOrderQtyAllowForSymbol(String symbol, String exchange, BaseSymbol.SecurityType securityType,double ordQty) throws SymbolManageException;

    /**
     * check whether this symbol is allow for the customer with given nationality
     *
     *
     * @param symbol string symbol code
     * @param exchange string exchange code
     * @param secType  enum security type
     * @param customerNationality string customer nationality
     * @return symbol validation api bean
     * @throws SymbolNotFoundException
     */
    SymbolValidationReply isNationalityRestrictedSymbol(String symbol, String exchange, BaseSymbol.SecurityType secType,
                                                        String customerNationality, boolean isGcc) throws SymbolManageException;

    /**
     * online orders are allow or not for the symbol
     *
     *
     * @param symbol string symbol code
     * @param exchange string exchange code
     * @param securityType enum security type
     * @return boolean value of online allowed or not
     * @throws SymbolNotFoundException
     */
    boolean isOnlineAllowed(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException;

    /**
     * trading on the symbol is allow for the institution
     *
     *
     * @param symbol string symbol code
     * @param exchange string exchange code
     * @param securityType enum security type
     * @param instId int institution id
     * @return boolean value of institution restricted or not
     * @throws SymbolNotFoundException
     */
    boolean isRestrictedForInstitution(String symbol, String exchange, BaseSymbol.SecurityType securityType, int instId) throws SymbolManageException;

    /**
     * is this is a sharia enable symbol
     *
     *
     * @param symbol string symbol code
     * @param exchange string exchange code
     * @param securityType enum security type
     * @param instId int insitution id
     * @return boolean value of sharia enabled or not
     * @throws SymbolNotFoundException
     */
    boolean isShariaCompliant(String symbol, String exchange, BaseSymbol.SecurityType securityType, int instId) throws SymbolManageException;


}
