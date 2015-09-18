package lk.ac.ucsc.oms.exchanges.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

public class ExchangeException extends OMSException {

    public ExchangeException(String message) {
        super(message);
    }

    public ExchangeException(String message, Exception e) {
        super(message, e);
    }
}