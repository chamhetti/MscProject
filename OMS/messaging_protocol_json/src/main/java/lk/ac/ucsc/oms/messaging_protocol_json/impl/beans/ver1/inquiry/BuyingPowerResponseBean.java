package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common.BuyingPowerBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.BUYING_POWER_LIST;

/**
 * User: vimalanathanr
 * Date: 9/13/13
 * Time: 10:31 AM
 */
public class BuyingPowerResponseBean implements Message {
    @SerializedName(BUYING_POWER_LIST)
    private List<BuyingPowerBean> buyingPowerRecords = new ArrayList<BuyingPowerBean>();

    public List<BuyingPowerBean> getBuyingPowerRecords() {
        return buyingPowerRecords;
    }

    public void setBuyingPowerRecords(List<BuyingPowerBean> buyingPowerRecords) {
        this.buyingPowerRecords = buyingPowerRecords;
    }

    public void addBuyingPower(BuyingPowerBean cashAccount) {
        this.buyingPowerRecords.add(cashAccount);
    }

}
