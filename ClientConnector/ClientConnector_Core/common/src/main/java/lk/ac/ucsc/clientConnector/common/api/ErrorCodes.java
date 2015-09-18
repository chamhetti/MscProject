package lk.ac.ucsc.clientConnector.common.api;

/**
 * @author AmilaS
 *         Date: 11/13/13
 *         Time: 2:17 PM
 */
public class ErrorCodes {

    private ErrorCodes() {
    }

    public static final int SESSION_VALIDATION_FAILED = 2;
    public static final int NON_AUTH_REQUEST_FOR_UNAUTHENTICATED_CLIENT = 3;
    public static final int INVALID_MESSAGE = 4;
    public static final int TIME_OUT_FROM_OMS = 5;
    public static final int MESSAGE_TYPE_IS_NOT_SUPPORTED = 7;
    public static final int ERR_INVALID_CLIENT_VERSION = 12;

}
