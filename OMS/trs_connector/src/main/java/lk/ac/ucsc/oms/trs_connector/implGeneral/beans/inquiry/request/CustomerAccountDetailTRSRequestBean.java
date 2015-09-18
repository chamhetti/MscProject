package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerAccountDetailTRSRequest;


public class CustomerAccountDetailTRSRequestBean implements CustomerAccountDetailTRSRequest {
    private String dealerID;
    private String customerId;
    private boolean isGlobalDealer;

    @Override
    public String getDealerID() {
        return dealerID;
    }

    @Override
    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean isGlobalDealer() {
        return isGlobalDealer;
    }

    @Override
    public void setGlobalDealer(boolean globalDealer) {
        isGlobalDealer = globalDealer;
    }
}
