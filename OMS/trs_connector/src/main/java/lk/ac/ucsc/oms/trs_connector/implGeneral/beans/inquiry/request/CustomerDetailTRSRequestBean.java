package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerDetailTRSRequest;


public class CustomerDetailTRSRequestBean implements CustomerDetailTRSRequest {
    private String dealerID;
    private String customerId;
    private boolean isGlobalDealer;
    private ClientChannel channel;

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

    public boolean isGlobalDealer() {
        return isGlobalDealer;
    }

    public void setGlobalDealer(boolean globalDealer) {
        isGlobalDealer = globalDealer;
    }

    @Override
    public ClientChannel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(ClientChannel channel) {
        this.channel = channel;
    }
}
