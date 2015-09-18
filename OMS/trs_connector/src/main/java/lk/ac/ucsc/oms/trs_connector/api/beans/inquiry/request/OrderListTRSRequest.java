package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

/**
 * User: vimalanathanr
 * Date: 4/2/14
 * Time: 1:57 PM
 */
public interface OrderListTRSRequest {
    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    int getOrderCategory();

    void setOrderCategory(int orderCategory);
}
