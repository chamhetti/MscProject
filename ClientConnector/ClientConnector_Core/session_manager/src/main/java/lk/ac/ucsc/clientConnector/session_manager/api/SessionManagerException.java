package lk.ac.ucsc.clientConnector.session_manager.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;


public class SessionManagerException extends OMSException {

    public SessionManagerException(String message) {
        super(message);
    }

    public SessionManagerException(String message, Exception e) {
        super(message, e);
    }
}
