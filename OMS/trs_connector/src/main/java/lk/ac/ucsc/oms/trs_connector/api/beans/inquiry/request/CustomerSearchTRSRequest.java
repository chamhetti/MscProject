package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

/**
 * User: vimalanathanr
 * Date: 3/31/14
 * Time: 11:18 AM
 */
public interface CustomerSearchTRSRequest {
    String getDealerId();

    void setDealerId(String dealerId);

    int getStartingSequenceNumber();

    void setStartingSequenceNumber(int startingSequenceNumber);

    int getPageWidth();

    void setPageWidth(int pageWidth);

    String getFilterOn();

    void setFilterOn(String filterOn);

    String getFilterText();

    void setFilterText(String filterText);

    boolean isGlobalDealer();

    void setGlobalDealer(boolean globalDealer);

}
