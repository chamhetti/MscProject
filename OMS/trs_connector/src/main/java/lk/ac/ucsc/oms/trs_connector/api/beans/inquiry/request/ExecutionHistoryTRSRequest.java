package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

/**
 * User: vimalanathanr
 * Date: 4/2/14
 * Time: 3:28 PM
 */
public interface ExecutionHistoryTRSRequest {
    String getClOrdId();

    void setClOrdId(String clOrdId);

    String getMubasherOrderNumber();

    void setMubasherOrderNumber(String mubasherOrderNumber);

    int getOrderCategory();

    void setOrderCategory(int orderCategory);
}
