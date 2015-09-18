package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.CLIENT_ORDER_ID;
import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.ORDER_CATEGORY;


public class OrderDetailsRequestBean implements Message {
    @SerializedName(CLIENT_ORDER_ID)
    private String clOrderID = null;

    @SerializedName(ORDER_CATEGORY)
    private int ordCategory = -1;

    public String getClOrderID() {
        return clOrderID;
    }

    public void setClOrderID(String clOrderID) {
        this.clOrderID = clOrderID;
    }

    public int getOrdCategory() {
        return ordCategory;
    }

    public void setOrdCategory(int ordCategory) {
        this.ordCategory = ordCategory;
    }
}
