package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;

public class OrderCancelRequestBean implements Message {
    @SerializedName(ORDER_TYPE)
    private String ordType = null;

    @SerializedName(ORIGINAL_CLIENT_ORDER_ID)
    private String origClOrdID = null;

    @SerializedName(ORDER_SIDE)
    private String side = null;

    @SerializedName(SYMBOL)
    private String symbol = null;

    @SerializedName(SIGNATURE)
    private String signature = null;

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String securityAccountNumber;

    @SerializedName(BOOK_KEEPER_ID)
    private String bookKeeperId = null;

    @SerializedName(INSTRUMENT_TYPE)
    private String instrumentType = null;

    @SerializedName(INTERNAL_MATCH_STATUS)
    private int internalMatchStatus;

    @SerializedName(MULTININ_BREAKDOWN_REF)
    private String multiNINBreakDownRef;

    @SerializedName(MULTININ_BATCH_NO)
    private String multiNINBatchNO;

    @SerializedName(CALL_CENTER_ORD_REF)
    private String callCenterOrdRef;

    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    public String getOrigClOrdID() {
        return origClOrdID;
    }

    public void setOrigClOrdID(String origClOrdID) {
        this.origClOrdID = origClOrdID;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSecurityAccNo() {
        return securityAccountNumber;
    }

    public void setSecurityAccNo(String securityAccNo) {
        this.securityAccountNumber = securityAccNo;
    }

    public String getBookKeeperID() {
        return bookKeeperId;
    }

    public void setBookKeeperID(String bookKeeperID) {
        this.bookKeeperId = bookKeeperID;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public int getInternalMatchStatus() {
        return internalMatchStatus;
    }

    public void setInternalMatchStatus(int internalMatchStatus) {
        this.internalMatchStatus = internalMatchStatus;
    }

    public String getMultiNINBreakDownRef() {
        return multiNINBreakDownRef;
    }

    public void setMultiNINBreakDownRef(String multiNINBreakDownRef) {
        this.multiNINBreakDownRef = multiNINBreakDownRef;
    }

    public String getMultiNINBatchNO() {
        return multiNINBatchNO;
    }

    public void setMultiNINBatchNO(String multiNINBatchNO) {
        this.multiNINBatchNO = multiNINBatchNO;
    }

    public String getCallCenterOrdRef() {
        return callCenterOrdRef;
    }

    public void setCallCenterOrdRef(String callCenterOrdRef) {
        this.callCenterOrdRef = callCenterOrdRef;
    }
}
