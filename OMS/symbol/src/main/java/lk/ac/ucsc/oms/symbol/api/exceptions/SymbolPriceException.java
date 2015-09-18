package lk.ac.ucsc.oms.symbol.api.exceptions;

/**
 * Exception that throws if any issue happen while storing or updating a symbol.
 *
 * User: Hetti
 * Date: 11/19/12
 * Time: 4:30 PM
 */
public class SymbolPriceException extends SymbolManageException {

    /**
     * Construct new exception using given detailed error message
     *
     * @param message detailed error message
     */
    public SymbolPriceException(String message){
        super(message);
    }

    /**
     * Construct new exception by using detailed error message and thrown exception
     *
     * @param message detailed error message
     * @param e thrown exception
     */
    public SymbolPriceException(String message, Exception e){
        super(message, e);
    }
}
