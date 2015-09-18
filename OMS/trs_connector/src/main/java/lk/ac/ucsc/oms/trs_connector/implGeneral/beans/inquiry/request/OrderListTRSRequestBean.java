package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.OrderListTRSRequest;


public class OrderListTRSRequestBean implements OrderListTRSRequest {
    private String accountNumber;
    private int orderCategory;

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
