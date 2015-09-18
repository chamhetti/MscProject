package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * User: vimalanathanr
 * Date: 12/17/13
 * Time: 7:56 PM
 */
public interface HoldingLogRecord {

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    String getSecurityAccountNumber();

    void setSecurityAccountNumber(String securityAccountNumber);

    String getInstituteID();

    void setInstituteID(String instituteID);

    String getExchange();

    void setExchange(String exchange);

    String getSymbol();

    void setSymbol(String symbol);

    String getInstrumentType();

    void setInstrumentType(String instrumentType);

    int getOrderSide();

    void setOrderSide(int orderSide);

    double getLastShares();

    void setLastShares(double lastShares);

    double getLastPrice();

    void setLastPrice(double lastPrice);
}
