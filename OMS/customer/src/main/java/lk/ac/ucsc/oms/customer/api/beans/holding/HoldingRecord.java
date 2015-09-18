package lk.ac.ucsc.oms.customer.api.beans.holding;


public interface HoldingRecord {

    HoldingKey getHoldingInfoKey();

    void setHoldingInfoKey(HoldingKey holdingInfoKey);

    double getDaySellPending();

    void setDaySellPending(double daySellPending);

    int getInstrumentType();

    void setInstrumentType(int instrumentType);

    double getPendingHolding();

    void setPendingHolding(double pendingHolding);

    double getNetDayHolding();

    void setNetDayHolding(double netDayHolding);

    double getPriceRatio();

    void setPriceRatio(double priceRatio);

    double getAvgPrice();

    void setAvgPrice(double avgPrice);

    double getBuyPending();

    void setBuyPending(double buyPending);

    double getPledgeQty();

    void setPledgeQty(double pledgeQty);

    double getSellPending();

    void setSellPending(double sellPending);

    double getAvgCost();

    void setAvgCost(double avgCost);

    double getNetHolding();

    void setNetHolding(double netHolding);

    double getDayHoldingAvgPrice();

    void setDayHoldingAvgPrice(double dayHoldingAvgPrice);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

}
