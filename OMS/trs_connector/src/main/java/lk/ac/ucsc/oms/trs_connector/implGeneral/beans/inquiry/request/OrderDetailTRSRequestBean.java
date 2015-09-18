package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.OrderDetailTRSRequest;


public class OrderDetailTRSRequestBean implements OrderDetailTRSRequest {
    private String clOrdId;
    private int orderCategory;

    @Override
    public String getClOrdId() {
        return clOrdId;
    }

    @Override
    public void setClOrdId(String clOrdId) {
        this.clOrdId = clOrdId;
    }

    @Override
    public int getOrderCategory() {
        return orderCategory;
    }

    @Override
    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }
}
