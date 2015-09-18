package lk.ac.ucsc.clientConnector.client;

import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.api.MessageRouter;
import lk.ac.ucsc.clientConnector.common.api.ClientType;
import lk.ac.ucsc.clientConnector.common.api.ErrorCodes;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.Mapper;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.exceptions.RequestTimedOutException;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;
import lk.ac.ucsc.clientConnector.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


public class WebClient extends AbstractClient {
    private static Logger logger = LoggerFactory.getLogger(WebClient.class);
    private HttpSession session;
    private Mapper mapper;

    public WebClient(String trsId, HttpSession session, Converter converter, Mapper mapper, MessageRouter messageRouter,
                     ClientType clientType, Settings settings) {
        setTrsId(trsId);
        this.session = session;
        this.mapper = mapper;
        setConnected(true);
        setConverter(converter);
        setMsgRouter(messageRouter);
        setClientType(clientType);
        setSettings(settings);
        logger.info(clientType.toString() + " user connected. ip: " + getClientAddress());
    }

    /**
     * Process web client request
     *
     * @param requestMsg is the web request to the trs
     */
    @Override
    public String processUserRequest(String requestMsg) {
        String res;
        TrsMessage trsMessage = preProcessRequest(requestMsg);
        int messageGroup = trsMessage.getGroup();
        int requestType = trsMessage.getReqType();

        if (trsMessage.getProcessingStatus() == ProcessingStatus.VALID) {

            try {
                try {
                    if (getConverter().isPulseMessage(messageGroup, requestType)) {
                        logger.info("Pulse message received from {} client with session id: {}. Generating response", getClientType(), getSessionID());
                        res = getConverter().getPulseMessage(getSessionID());
                    } else {
                        getMsgRouter().putMessage(trsMessage);
                        logger.info("Message routed. Waiting for response. Message: {}", trsMessage.toStringMetaData());
                        if(messageGroup!=1) {
                            res = (String) mapper.processRequest(Long.parseLong(trsMessage.getUniqueReqId()));
                        }else{
                            res ="order received";
                        }
                        logger.info("Response dispatched from sync/async mapper. Message: {}", trsMessage.toStringMetaData());
                    }
                } catch (RequestTimedOutException e) {
                    logger.error("Request from web client timed-out", e);
                    // no asynchronous response received from OMS after the timeout
                    trsMessage.setProcessingStatus(ProcessingStatus.INVALID);
                    trsMessage.setInvalidReason("Timed-out from OMS");
                    trsMessage.setErrorCode(ErrorCodes.TIME_OUT_FROM_OMS);
                    res = createAndRecordErrorResponse(trsMessage);
                }
            } catch (ClientConnectorException e) {
                logger.error("Error while routing web request", e);
                res = createAndRecordErrorResponse(trsMessage);
            }

        } else {
            logger.error("Invalid message from web client: {}", trsMessage.toStringMetaData());
            res = createAndRecordErrorResponse(trsMessage);
        }

        return res;
    }

    @Override
    public String authenticate(String request) {
        TrsMessage authRequest;
        String res;

        logger.info("Authenticating web client from " + this.getClientAddress());
        authRequest = preProcessAuthentication(request);

        if (authRequest.getProcessingStatus() == ProcessingStatus.VALID) {
            try {
                getMsgRouter().putMessage(authRequest);

                try {
                    logger.info("Auth message routed. Waiting for response. Message: {}", authRequest.toStringMetaData());
                    res = (String) mapper.processRequest(Long.parseLong(authRequest.getUniqueReqId()));
                    logger.info("Auth response dispatched from sync/async mapper. Message: {}", authRequest.toStringMetaData());
                } catch (RequestTimedOutException e) {
                    // no asynchronous response received from OMS after the timeout
                    authRequest.setProcessingStatus(ProcessingStatus.INVALID);
                    authRequest.setInvalidReason("Timed-out from OMS");
                    res = createAndRecordErrorResponse(authRequest);
                }
            } catch (ClientConnectorException e) {
                logger.error("Error while routing web auth request", e);
                res = createAndRecordErrorResponse(authRequest);
            }

        } else {
            logger.info("Error authenticating web client {}. Reason: {}", this.getClientAddress(), authRequest.getInvalidReason());
            res = createAndRecordErrorResponse(authRequest);
        }

        return res;
    }

    public String createAndRecordErrorResponse(TrsMessage message) {
        TrsMessage errorResponse = createErrorResponse(message);
        try {
            session.getCreationTime();
            try {
                message.setProcessingStatus(ProcessingStatus.SENT_ERROR_RESPONSE);
            } catch (Exception e) {
                logger.error("Error recording error response");
            }
        } catch (IllegalStateException ise) {
            // session is invalid, no need to record error response
        }
        return errorResponse.getOriginalMessage();
    }

    @Override
    void write(String msg) throws IOException {
        // Not applicable
    }

    /**
     * This overridden method is needed for web client since sync/async mapper is used to send http response
     *
     * @param message        trs message
     * @param responseString is the message string
     */
    @Override
    public void writeResponse(TrsMessage message, String responseString) {
        message.setTimeStamp(new Date());
        try {
            message.setProcessingStatus(ProcessingStatus.SENT_TO_CLIENT);
        } catch (Exception e) {
            logger.error("Error recording response message", e);
        }

        // unsolicited messages are ignored here since comet server is used to push unsolicited messages to web clients.
        if (!message.isUnsolicited()) {
            String uniqueId = message.getUniqueReqId();
            if (uniqueId != null) {
                logger.info("Submitting web response to sync/async mapper. {}", message.toStringMetaData());
                mapper.processResponse(Long.parseLong(uniqueId), responseString);
            } else {
                logger.error("Unique id is null. Response is discarded:" + responseString);
            }
        }

    }

    @Override
    public void close(String reason, boolean sendLogOut) {
        super.close(reason, sendLogOut);
        session.invalidate();
    }

    @Override
    public void sendLogoutResponse(String sessionId, String userId) {
        String logOutResponse = this.getConverter().getLogOutMessage(this.getUserID(), this.getSessionID());
    }

}
