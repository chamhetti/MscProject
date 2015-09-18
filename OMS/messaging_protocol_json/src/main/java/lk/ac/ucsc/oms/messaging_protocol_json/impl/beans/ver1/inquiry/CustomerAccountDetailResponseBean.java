package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class CustomerAccountDetailResponseBean implements Message {
    @SerializedName(DEALER_ID)
    private String dealerID = null;

    @SerializedName(CUSTOMER_ID)
    private String customerId = null;

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


}
