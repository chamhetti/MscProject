package lk.ac.ucsc.oms.trs_writer.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * This is the common checked exception thrown by TRS writer module
 *
 * @author tharinduj
 * @since 1.0
 */
public class TrsWriterException extends OMSException {

    /**
     * {@inheritDoc}
     */
    public TrsWriterException(String message) {
        super(message);
    }

    /**
     * Create a new exception with error message detail and thrown exception
     * @param message -error message detail
     * @param e   - thrown exception
     */
    public TrsWriterException(String message, Exception e){
        super(message, e);
    }
}