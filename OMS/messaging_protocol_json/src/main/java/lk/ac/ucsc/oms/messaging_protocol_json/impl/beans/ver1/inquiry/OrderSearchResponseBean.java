package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common.OrderBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;

public class OrderSearchResponseBean implements Message {
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

    @SerializedName(ORDER_CATEGORY)
    private int orderCategory;

    @SerializedName(ORDER_GROUP)
    private int orderGroup;

    @SerializedName(SEARCH_MODE)
    private int searchMode = -1;            //Used only in DT search

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

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public int getOrderGroup() {
        return orderGroup;
    }

    public void setOrderGroup(int orderGroup) {
        this.orderGroup = orderGroup;
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

    public int getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(int searchMode) {
        this.searchMode = searchMode;
    }
}
