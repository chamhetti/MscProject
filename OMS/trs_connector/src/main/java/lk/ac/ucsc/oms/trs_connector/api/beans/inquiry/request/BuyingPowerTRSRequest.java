package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;

/**
 * User: vimalanathanr
 * Date: 4/2/14
 * Time: 10:32 AM
 */
public interface BuyingPowerTRSRequest {
    ClientChannel getChannel();

    void setChannel(ClientChannel channel);

    String getUserId();

    void setUserId(String userId);

    String getAccountNumbers();

    void setAccountNumbers(String accountNumbers);
}
