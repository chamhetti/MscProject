package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;

/**
 * User: vimalanathanr
 * Date: 4/1/14
 * Time: 5:13 PM
 */
public interface CustomerDetailTRSRequest {
    String getDealerID();

    void setDealerID(String dealerID);

    String getCustomerId();

    void setCustomerId(String customerId);

    boolean isGlobalDealer();

    void setGlobalDealer(boolean globalDealer);

    ClientChannel getChannel();

    void setChannel(ClientChannel channel);
}
