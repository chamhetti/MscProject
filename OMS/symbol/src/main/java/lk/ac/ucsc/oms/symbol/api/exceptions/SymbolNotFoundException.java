package lk.ac.ucsc.oms.symbol.api.exceptions;

/**
 * Exception use to throw any error happen during loading a symbol.
 *
 * User: Hetti
 * Date: 11/19/12
 * Time: 4:30 PM
 */
public class SymbolNotFoundException extends SymbolManageException {

    /**
     * Construct new exception by using detailed error message of loading error
     *
     * @param message detailed error message
     */
    public SymbolNotFoundException(String message){
        super(message);
    }

    /**
     * Construct new exception by using detailed error message of loading error
     * and thrown exception
     *
     * @param message detailed error message
     * @param e exception thrown
     */
    public SymbolNotFoundException(String message, Exception e){
        super(message, e);
    }
}
