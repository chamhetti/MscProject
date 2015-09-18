package lk.ac.ucsc.oms.symbol.api.beans;

import lk.ac.ucsc.oms.symbol.api.SymbolStatusMessages;

/**
 * This is used to send validation status after a symbol validation
 * validation result and reject reason are sent through this.
 *
 * User: Hetti
 * Date: 2/13/13
 * Time: 10:19 PM
 */
public interface SymbolValidationReply {

    /**
     * Method used to get validation result after symbol validation
     *
     * @return Symbol Validation Result enum
     */
    SymbolValidationResult getValidationResult();

    /**
     * Method used to get reject reason after symbol bean validation
     *
     * @return Symbol status message enum
     * INVALID_SYMBOL - 1
     * TRADING_DISABLE - 2
     * SYMBOL_NOT_APPROVED - 3
     * INVALID_MIN_QTY - 4
     * LOCAL_ONLY_SYMBOL - 5
     */
    SymbolStatusMessages getRejectReason();

    enum SymbolValidationResult{
        SUCCESS, FAILED
    }
}
