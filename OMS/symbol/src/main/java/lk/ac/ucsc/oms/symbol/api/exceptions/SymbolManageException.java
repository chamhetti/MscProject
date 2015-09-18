package lk.ac.ucsc.oms.symbol.api.exceptions;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * Exception that throws if any issue happen while storing or updating a symbol.
 *
 * User: Hetti
 * Date: 11/19/12
 * Time: 4:30 PM
 */
public class SymbolManageException extends OMSException {

    /**
     * Construct new exception using given detailed error message
     *
     * @param message detailed error message
     */
    public  SymbolManageException(String message){
        super(message);
    }

    /**
     * Construct new exception by using detailed error message and thrown exception
     *
     * @param message detailed error message
     * @param e thrown exception
     */
    public SymbolManageException(String message, Exception e){
        super(message, e);
    }
}
