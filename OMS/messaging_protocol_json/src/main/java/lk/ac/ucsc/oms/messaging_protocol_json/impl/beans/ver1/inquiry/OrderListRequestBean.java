package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.ORDER_CATEGORY;
import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.SECURITY_ACCOUNT_NUMBER;


public class OrderListRequestBean implements Message {
    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String ordPortfolioID = null;

    @SerializedName(ORDER_CATEGORY)
    private int ordCategory = -1;

    public String getOrdPortfolioID() {
        return ordPortfolioID;
    }

    public void setOrdPortfolioID(String ordPortfolioID) {
        this.ordPortfolioID = ordPortfolioID;
    }

    public int getOrdCategory() {
        return ordCategory;
    }

    public void setOrdCategory(int ordCategory) {
        this.ordCategory = ordCategory;
    }
}
