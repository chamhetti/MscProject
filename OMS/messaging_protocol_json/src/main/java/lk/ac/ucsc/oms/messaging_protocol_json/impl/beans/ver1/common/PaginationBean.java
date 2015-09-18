package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class PaginationBean {

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
