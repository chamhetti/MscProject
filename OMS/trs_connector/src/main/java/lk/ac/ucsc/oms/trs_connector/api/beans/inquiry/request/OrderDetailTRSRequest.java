package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

/**
 * User: vimalanathanr
 * Date: 4/2/14
 * Time: 3:11 PM
 */
public interface OrderDetailTRSRequest {
    String getClOrdId();

    void setClOrdId(String clOrdId);

    int getOrderCategory();

    void setOrderCategory(int orderCategory);
}
