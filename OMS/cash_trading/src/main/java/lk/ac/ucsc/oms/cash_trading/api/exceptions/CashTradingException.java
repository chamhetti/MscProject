package lk.ac.ucsc.oms.cash_trading.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * Used to throw exceptions in the cash trading module
 * <p/>
 */
public class CashTradingException extends OMSException {
    /**
     * create custom exception with given detail message
     *
     * @param message is the detail message
     */
    public CashTradingException(String message) {
        super(message);
    }

    /**
     * create custom exception with given detail message & root cause
     *
     * @param message is the detail message
     * @param e       is the throwable
     */
    public CashTradingException(String message, Exception e) {
        super(message, e);
    }

}
