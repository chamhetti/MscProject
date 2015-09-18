package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.common;

/**
 * User: vimalanathanr
 * Date: 8/26/13
 * Time: 10:20 AM
 */
public interface PaginationTRS {

    int getStartingSequenceNumber();

    void setStartingSequenceNumber(int startingSequenceNumber);

    int getPagingType();

    void setPagingType(int pagingType);

    int getNextPageAvailable();

    void setNextPageAvailable(int nextPageAvailable);

    int getPageWidth();

    void setPageWidth(int pageWidth);

    int getTotalNumberOfRecords();

    void setTotalNumberOfRecords(int totalNumberOfRecords);
}
