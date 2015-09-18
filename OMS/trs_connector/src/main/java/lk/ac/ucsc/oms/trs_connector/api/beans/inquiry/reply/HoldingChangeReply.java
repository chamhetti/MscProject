package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * User: chamindah
 * Date: 2/27/15
 * Time: 2:20 PM
 */
public interface HoldingChangeReply {
    String getSecAccNumber();

    void setSecAccNumber(String secAccNumber);

    String getTransactionType();

    void setTransactionType(String transactionType);

    double getQuantityChange();

    void setQuantityChange(double quantityChange);

    double getTotalQuantity();

    void setTotalQuantity(double totalQuantity);

    String getDate();

    void setDate(String date);
}
