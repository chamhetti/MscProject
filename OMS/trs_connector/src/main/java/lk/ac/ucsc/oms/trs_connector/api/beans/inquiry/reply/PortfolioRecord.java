package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * User: Chathura
 * Date: Jan 31, 2013
 */
public interface PortfolioRecord {

    String getAccountNo();

    void setAccountNo(String accountNo);

    String getExchange();

    void setExchange(String exchange);

    String getSymbol();

    void setSymbol(String symbol);

    int getLotSize();

    void setLotSize(int lotSize);

    double getQuantity();

    void setQuantity(double quantity);

    double getPendingBuy();

    void setPendingBuy(double pendingBuy);

    double getPledged();

    void setPledged(double pledged);

    double getPendingSell();

    void setPendingSell(double pendingSell);

    double getAverageCost();

    void setAverageCost(double averageCost);

    double getAveragePrice();

    void setAveragePrice(double averagePrice);

    double getMarginDue();

    void setMarginDue(double marginDue);

    double getDayMarginDue();

    void setDayMarginDue(double dayMarginDue);

    double getNetDayHoldings();

    void setNetDayHoldings(double netDayHoldings);

    int getInstrumentType();

    void setInstrumentType(int instrumentType);

    String getCurrency();

    void setCurrency(String currency);

    double getAvailableQuantity();

    void setAvailableQuantity(double availableQuantity);

    String getSymbolCode();

    void setSymbolCode(String symbolCode);

    double getAvailableForSell();

    void setAvailableForSell(double availableForSell);

    double getMarketPrice();

    void setMarketPrice(double marketPrice);

    double getDaySellPending();

    void setDaySellPending(double daySellPending);

    double getNetTplusHolding();

    void setNetTplusHolding(double netTplusHolding);

    String getSymbolShortDescription();

    void setSymbolShortDescription(String symbolShortDescriptionEN);

    int getDayMarginNotificationLevel();

    void setDayMarginNotificationLevel(int dayMarginNotificationLevel);

    int getHoldingType();

    void setHoldingType(int holdingType);
}
