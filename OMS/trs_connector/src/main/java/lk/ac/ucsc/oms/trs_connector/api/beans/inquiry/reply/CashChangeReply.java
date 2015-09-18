package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * User: chamindah
 * Date: 2/27/15
 * Time: 2:10 PM
 */
public interface CashChangeReply {
    String getDate();

    void setDate(String date);

    String getCashAccNumber();

    void setCashAccNumber(String cashAccNumber);

    String getCurrency();

    void setCurrency(String currency);

    String getTransactionType();

    void setTransactionType(String transactionType);

    double getAmount();

    void setAmount(double amount);

    double getBalance();

    void setBalance(double balance);
}
