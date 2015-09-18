package lk.ac.ucsc.clientConnector.common.api;

/**
 * @Author AmilaS
 * Date: 10/1/13
 * Time: 4:30 PM
 */
public enum ProcessingStatus {
    NEW, VALID, EMPTY_REQUEST, PARSE_ERROR, NOT_SUPPORTED, INVALID, SENT_TO_OMS, OMS_RESPONSE_RECEIVED, SENT_TO_CLIENT, SENT_ERROR_RESPONSE
}
