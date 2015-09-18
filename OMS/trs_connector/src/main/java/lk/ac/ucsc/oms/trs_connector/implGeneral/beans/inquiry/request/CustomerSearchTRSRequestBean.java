package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerSearchTRSRequest;


public class CustomerSearchTRSRequestBean implements CustomerSearchTRSRequest {
    private String dealerId;
    private int startingSequenceNumber;
    private int pageWidth;
    private String filterOn;
    private String filterText;
    private boolean isGlobalDealer;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public int getStartingSequenceNumber() {
        return startingSequenceNumber;
    }

    public void setStartingSequenceNumber(int startingSequenceNumber) {
        this.startingSequenceNumber = startingSequenceNumber;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
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

    public boolean isGlobalDealer() {
        return isGlobalDealer;
    }

    public void setGlobalDealer(boolean globalDealer) {
        isGlobalDealer = globalDealer;
    }
}
