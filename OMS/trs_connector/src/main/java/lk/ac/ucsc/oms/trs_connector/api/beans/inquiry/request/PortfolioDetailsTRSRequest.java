package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;

/**
 * User: vimalanathanr
 * Date: 4/2/14
 * Time: 11:27 AM
 */
public interface PortfolioDetailsTRSRequest {
    ClientChannel getChannel();

    void setChannel(ClientChannel channel);

    String getUserId();

    void setUserId(String userId);

    String getAccountNumbers();

    void setAccountNumbers(String accountNumbers);

    String getSymbol();

    void setSymbol(String symbol);

    String getLanguage();

    void setLanguage(String language);
}
