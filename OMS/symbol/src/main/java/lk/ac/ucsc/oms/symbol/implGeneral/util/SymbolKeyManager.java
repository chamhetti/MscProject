package lk.ac.ucsc.oms.symbol.implGeneral.util;

import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;


public final class SymbolKeyManager {

    private SymbolKeyManager() {

    }

    /**
     * Return the key of the symbol that is used to store in the cache. key is the combination of
     * exchange_symbol_securitytype for a example DFM_EMAAR_CS
     *
     * @param symbol Symbol object of supported security type
     * @return String symbolKey
     */
    public static String getSymbolKey(BaseSymbol symbol) throws SymbolManageException {
        if (symbol == null) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolKey --> Error given symbol is null");
        }
        if (symbol.getSecurityExchange() == null || symbol.getSymbol() == null || symbol.getSecurityTypes().getCode() == null) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolKey --> Error given symbol data null");
        }
        if ("".equalsIgnoreCase(symbol.getSecurityExchange()) || "".equalsIgnoreCase(symbol.getSymbol())) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolKey --> Error given symbol data null");
        }
        return symbol.getSecurityExchange() + "|" + symbol.getSymbol() + "|" + symbol.getSecurityTypes().getCode();
    }

    /**
     * Return the key of the symbol that is used to store in the cache. key is the combination of
     * exchange_symbol_securitytype for a example DFM_EMAAR_CS
     *
     * @param symbol       symbol code
     * @param exchange     exchange code
     * @param securityType security type of the symbol
     * @return Symbol key as String
     */
    public static String getSymbolKey(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException {
        if (symbol == null || securityType == null || "".equalsIgnoreCase(symbol)) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolKey --> Error given symbol data null");
        }
        if (exchange == null || "".equalsIgnoreCase(exchange)) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolKey --> Error given symbol data null");
        }
        return exchange + "|" + symbol + "|" + securityType.getCode();
    }

    /**
     * get the symbol code from a symbol key say key is DFM_EMMAR_CS this method return EMMAR
     * if the key is null or invalied formal this return null
     *
     * @param symbolKey key of the symbol at cache
     * @return String symbol code such as EMAAR
     */
    public static String getSymbolFromKey(String symbolKey) throws SymbolManageException {
        if (symbolKey == null || "".equalsIgnoreCase(symbolKey)) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolFromKey --> Error given symbolKey is null or empty");
        }
        try {
            return symbolKey.split("\\|")[1];
        } catch (Exception e) {
            throw new SymbolManageException("SymbolKeyManager::getSymbolFromKey --> Error splitting symbol key ", e);
        }
    }

    /**
     * this return the exchange from a key. if key is null or invalid format this return null
     *
     * @param symbolKey symbol key
     * @return exchange code
     */
    public static String getExchangeFromKey(String symbolKey) throws SymbolManageException {
        if (symbolKey == null || "".equalsIgnoreCase(symbolKey)) {
            throw new SymbolManageException("SymbolKeyManager::getExchangeFromKey --> Error given symbolKey is null or empty");
        }
        try {
            return symbolKey.split("\\|")[0];
        } catch (Exception e) {
            throw new SymbolManageException("SymbolKeyManager::getExchangeFromKey --> Error splitting symbol key");
        }
    }
}
