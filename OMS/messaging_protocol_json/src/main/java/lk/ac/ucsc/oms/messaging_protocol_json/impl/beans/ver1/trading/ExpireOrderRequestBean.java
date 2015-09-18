package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.ORIGINAL_CLIENT_ORDER_ID;
import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.SECURITY_ACCOUNT_NUMBER;


public class ExpireOrderRequestBean implements Message {
    @SerializedName(ORIGINAL_CLIENT_ORDER_ID)
    private String origClOrdID = null;

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String securityAccNo = null;

    public String getOrigClOrderId() {
        return origClOrdID;
    }

    public void setOrigClOrderId(String origClOrderId) {
        this.origClOrdID = origClOrderId;
    }

    public String getSecurityAccNo() {
        return securityAccNo;
    }

    public void setSecurityAccNo(String securityAccNo) {
        this.securityAccNo = securityAccNo;
    }
}
