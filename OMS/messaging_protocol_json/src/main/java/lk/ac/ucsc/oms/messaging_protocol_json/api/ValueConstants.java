package lk.ac.ucsc.oms.messaging_protocol_json.api;

/**
 * @author hetti
 */
public interface ValueConstants {

    // Response status
    int RESPONSE_STATUS_SUCCESS = 1;
    int RESPONSE_STATUS_FAILURE = 0;


    /**
     * Error codes for message header
     * some of these are used only in TRS
     */

    int ERR_INVALID_MESSAGE = 4;
    int ERR_SYSTEM_ERROR = 8;
    int ERR_INVALID_PARAMETERS = 9;
    int ERR_SECURITY_VIOLATION = 10;
    int ERR_INVALID_VERSION = 11;


}
