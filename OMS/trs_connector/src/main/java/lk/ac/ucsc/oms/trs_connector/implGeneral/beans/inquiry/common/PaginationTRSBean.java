package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.common;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.common.PaginationTRS;


public class PaginationTRSBean implements PaginationTRS {
    private int startingSequenceNumber;
    private int pagingType;
    private int isNextPageAvailable;
    private int pageWidth;
    private int totalNumberOfRecords;

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
