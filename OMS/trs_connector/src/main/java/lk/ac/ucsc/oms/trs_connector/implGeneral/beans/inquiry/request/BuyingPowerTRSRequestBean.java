package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.BuyingPowerTRSRequest;


public class BuyingPowerTRSRequestBean implements BuyingPowerTRSRequest {
    private ClientChannel channel;
    private String userId;
    private String accountNumbers;

    @Override
    public ClientChannel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(ClientChannel channel) {
        this.channel = channel;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getAccountNumbers() {
        return accountNumbers;
    }

    @Override
    public void setAccountNumbers(String accountNumbers) {
        this.accountNumbers = accountNumbers;
    }
}
