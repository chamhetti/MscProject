package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * @author Chathura
 *         Date: Feb 18, 2013
 */
public interface MarginableBuyingPowerRecord {

    double getMarginableBuyingPower();

    void setMarginableBuyingPower(double marginBuyingPower);

    double getSymbolMarginPercentageDay();

    void setSymbolMarginPercentageDay(double symbolMarginPercentageDay);

    double getSymbolMarginPercentage();

    void setSymbolMarginPercentage(double symbolMarginPercentage);

    String getSymbolCurrency();

    void setSymbolCurrency(String symbolCurrency);
}
