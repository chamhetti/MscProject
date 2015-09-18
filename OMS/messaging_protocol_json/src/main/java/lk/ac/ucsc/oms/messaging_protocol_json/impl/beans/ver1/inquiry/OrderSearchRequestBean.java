package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class OrderSearchRequestBean implements Message {
    @SerializedName(DEALER_ID)
    private String dealerId = null;       //Used only in DT search

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String portfolioId = null;

    @SerializedName(START_DATE)
    private String startDate = null;

    @SerializedName(END_DATE)
    private String endDate = null;

    @SerializedName(ORDER_SIDE)
    private int orderSide = -1;

    @SerializedName(ORDER_CATEGORY)
    private int orderCategory = -1;

    @SerializedName(ORDER_STATUS)
    private String orderStatus = null;

    @SerializedName(SYMBOL)
    private String symbol = null;

    @SerializedName(CLIENT_ORDER_ID)
    private String clOrderID = null;

    @SerializedName(EXCHANGE)
    private String exchange = null;

    @SerializedName(FILTER_ON)
    private String filterOn = null;         //Used only in DT search

    @SerializedName(FILTER_TEXT)
    private String filterText = null;       //Used only in DT search

    @SerializedName(FILTER_ON2)
    private String filterOn2 = null;        //Used only in DT search

    @SerializedName(FILTER_TEXT2)
    private String filterText2 = null;      //Used only in DT search

    @SerializedName(DEALER_TYPE)
    private int dealerType = -1;            //Used only in DT search

    @SerializedName(ORDER_GROUP)
    private int orderGroup = -1;            //Used only in DT search

    @SerializedName(SEARCH_MODE)
    private int searchMode = -1;            //Used only in DT search

    @SerializedName(START_SEQUENCE)
    private int startingSequenceNumber = -1;

    @SerializedName(PAGING_TYPE)
    private int pagingType = -1;

    @SerializedName(IS_NEXT_PAGE_AVAILABLE)
    private int isNextPageAvailable = -1;

    @SerializedName(PAGE_WIDTH)
    private int pageWidth = -1;

    @SerializedName(TOTAL_NO_OF_RECORDS)
    private int totalNumberOfRecords = -1;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
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

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getFilterOn() {
        return filterOn;
    }

    public void setFilterOn(String filterOn) {
        this.filterOn = filterOn;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getFilterOn2() {
        return filterOn2;
    }

    public void setFilterOn2(String filterOn2) {
        this.filterOn2 = filterOn2;
    }

    public String getFilterText2() {
        return filterText2;
    }

    public void setFilterText2(String filterText2) {
        this.filterText2 = filterText2;
    }

    public int getDealerType() {
        return dealerType;
    }

    public void setDealerType(int dealerType) {
        this.dealerType = dealerType;
    }

    public int getOrderGroup() {
        return orderGroup;
    }

    public void setOrderGroup(int orderGroup) {
        this.orderGroup = orderGroup;
    }

    public int getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(int searchMode) {
        this.searchMode = searchMode;
    }

    public int getStartingSequenceNumber() {
        return startingSequenceNumber;
    }

    public void setStartingSequenceNumber(int startingSequenceNumber) {
        this.startingSequenceNumber = startingSequenceNumber;
    }

    public int getPagingType() {
        return pagingType;
    }

    public void setPagingType(int pagingType) {
        this.pagingType = pagingType;
    }

    public int getNextPageAvailable() {
        return isNextPageAvailable;
    }

    public void setNextPageAvailable(int nextPageAvailable) {
        isNextPageAvailable = nextPageAvailable;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getTotalNumberOfRecords() {
        return totalNumberOfRecords;
    }

    public void setTotalNumberOfRecords(int totalNumberOfRecords) {
        this.totalNumberOfRecords = totalNumberOfRecords;
    }
}
