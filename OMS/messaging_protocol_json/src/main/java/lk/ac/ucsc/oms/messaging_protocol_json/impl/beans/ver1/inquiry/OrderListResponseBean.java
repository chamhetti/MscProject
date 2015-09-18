package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common.OrderBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.ORDER_CATEGORY;
import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.ORDER_LIST;


public class OrderListResponseBean implements Message {
    @SerializedName(ORDER_CATEGORY)
    private int ordCategory = -1;

    @SerializedName(ORDER_LIST)
    private List<OrderBean> orderList = new ArrayList<OrderBean>();

    public List<OrderBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderBean> orderList) {
        this.orderList = orderList;
    }

    public void addOrder(OrderBean order) {
        this.orderList.add(order);
    }

    public int getOrdCategory() {
        return ordCategory;
    }

    public void setOrdCategory(int ordCategory) {
        this.ordCategory = ordCategory;
    }
}
