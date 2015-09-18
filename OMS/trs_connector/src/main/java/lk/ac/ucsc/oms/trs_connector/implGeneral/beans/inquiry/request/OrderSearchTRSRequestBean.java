package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.common.PaginationTRS;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.OrderSearchTRSRequest;


public class OrderSearchTRSRequestBean implements OrderSearchTRSRequest {

    private String accountNumber;
    private String startDate;
    private String endDate;
    private int orderSide;
    private int ordCategory;
    private String ordCategoryStr;
    private String orderStatus;
    private String symbol;
    private String clOrderID;
    private String exchange;
    private PaginationTRS paging;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(int orderSide) {
        this.orderSide = orderSide;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getClOrderID() {
        return clOrderID;
    }

    public void setClOrderID(String clOrderID) {
        this.clOrderID = clOrderID;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public PaginationTRS getPaging() {
        return paging;
    }

    public void setPaging(PaginationTRS paging) {
        this.paging = paging;
    }

    public int getOrdCategory() {
        return ordCategory;
    }

    public void setOrdCategory(int ordCategory) {
        this.ordCategory = ordCategory;
    }

    public String getOrdCategoryStr() {
        return ordCategoryStr;
    }

    public void setOrdCategoryStr(String ordCategoryStr) {
        this.ordCategoryStr = ordCategoryStr;
    }
}
