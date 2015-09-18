package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

/**
 * User: vimalanathanr
 * Date: 4/1/14
 * Time: 6:20 PM
 */
public interface CustomerAccountDetailTRSRequest {
    String getDealerID();

    void setDealerID(String dealerID);

    String getCustomerId();

    void setCustomerId(String customerId);

    boolean isGlobalDealer();

    void setGlobalDealer(boolean globalDealer);
}
