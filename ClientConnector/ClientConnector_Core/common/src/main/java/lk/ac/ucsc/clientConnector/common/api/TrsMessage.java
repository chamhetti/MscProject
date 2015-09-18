package lk.ac.ucsc.clientConnector.common.api;

import java.util.Date;

/**
 * @author AmilaS
 *         Date: 9/9/13
 *         Time: 5:53 PM
 */
public class TrsMessage {

    private int group;
    private int reqType;
    private String userId;
    private String clientIp;
    private String msgSessionId;
    private String uniqueReqId;
    private String clientReqId;
    private int channelId;
    private ProcessingStatus processingStatus = ProcessingStatus.NEW;
    private String invalidReason;
    private int errorCode;
    private Object messageObject = null;
    private String originalMessage;
    private MessageDestination destination;
    private ClientType clientType;
    private Date timeStamp;
    private boolean unsolicited = false;
    private String clientVersion;
    private int compression;
    private int compressedSize;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getMsgSessionId() {
        return msgSessionId;
    }

    public void setMsgSessionId(String msgSessionId) {
        this.msgSessionId = msgSessionId;
    }

    public String getUniqueReqId() {
        return uniqueReqId;
    }

    public void setUniqueReqId(String uniqueReqId) {
        this.uniqueReqId = uniqueReqId;
    }

    public String getClientReqId() {
        return clientReqId;
    }

    public void setClientReqId(String clientReqId) {
        this.clientReqId = clientReqId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public ProcessingStatus getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public Object getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(Object messageObject) {
        this.messageObject = messageObject;
    }

    public MessageDestination getDestination() {
        return destination;
    }

    public void setDestination(MessageDestination destination) {
        this.destination = destination;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isUnsolicited() {
        return unsolicited;
    }

    public void setUnsolicited(boolean unsolicited) {
        this.unsolicited = unsolicited;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getCompression() {
        return compression;
    }

    public void setCompression(int compression) {
        this.compression = compression;
    }

    public int getCompressedSize() {
        return compressedSize;
    }

    public void setCompressedSize(int compressedSize) {
        this.compressedSize = compressedSize;
    }

    @Override
    public String toString() {
        return "TrsMessage{" +
                "group=" + group +
                ", reqType=" + reqType +
                ", userId='" + userId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", msgSessionId='" + msgSessionId + '\'' +
                ", uniqueReqId='" + uniqueReqId + '\'' +
                ", clientReqId='" + clientReqId + '\'' +
                ", channelId=" + channelId +
                ", processingStatus=" + processingStatus +
                ", invalidReason='" + invalidReason + '\'' +
                ", messageObject=" + messageObject +
                ", originalMessage='" + originalMessage + '\'' +
                ", destination=" + destination +
                ", clientType=" + clientType +
                ", clientVersion=" + clientVersion +
                ", compression=" + compression +
                '}';

    }

    public String toStringMetaData() {
        return "TrsMessage{" +
                "group=" + group +
                ", reqType=" + reqType +
                ", userId='" + userId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", msgSessionId='" + msgSessionId + '\'' +
                ", uniqueReqId='" + uniqueReqId + '\'' +
                ", clientReqId='" + clientReqId + '\'' +
                ", channelId=" + channelId +
                ", processingStatus=" + processingStatus +
                ", invalidReason='" + invalidReason + '\'' +
                ", destination=" + destination +
                ", clientType=" + clientType +
                ", clientVersion=" + clientVersion +
                ", unsolicited=" + unsolicited +
                ", compression=" + compression +
                '}';
    }
}
