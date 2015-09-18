package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class CustomerAccountDetailRequestBean implements Message {
    @SerializedName(DEALER_ID)
    private String dealerID = null;

    @SerializedName(CUSTOMER_ID)
    private String customerId = null;

    @SerializedName(DEALER_TYPE)
    private int dealerType = -1;

    public String getDealerID() {
        return dealerID;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getDealerType() {
        return dealerType;
    }

    public void setDealerType(int dealerType) {
        this.dealerType = dealerType;
    }
}
