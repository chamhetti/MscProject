package lk.ac.ucsc.oms.customer.api.beans.holding;

import java.util.Date;


public interface HoldingLog {


    int getStatus();

    void setStatus(int status);

    long getHoldingLogId();

    void setHoldingLogId(long holdingLogId);

    String getAccountNumber();

    void setAccountNumber(String accountId);

    String getExchange();

    void setExchange(String exchange);

    String getSymbol();

    void setSymbol(String symbol);

    double getAvgCost();

    void setAvgCost(double avgCost);

    String getClOrderId();

    void setClOrderId(String clOrderId);

    int getInstitutionId();

    void setInstitutionId(int institutionId);

    double getOrdCumHolding();

    void setOrdCumHolding(double ordCumHolding);

    double getNetHolding();

    void setNetHolding(double netHolding);

    int getSide();

    void setSide(int side);

    String getNarration();

    void setNarration(String narration);

    Date getTransactionTime();

    void setTransactionTime(Date transactionTime);

    double getNetSettle();

    void setNetSettle(double netSettle);

    double getPrice();

    void setPrice(double price);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    double getLastShares();

    void setLastShares(double lastShares);

    double getTotalHoldings();

    void setTotalHoldings(double totalHoldings);

    long getBackOfficeId();

    void setBackOfficeId(long backOfficeId);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

    String getInstrumentType();

    void setInstrumentType(String instrumentType);

    double getCommissionDifference();

    void setCommissionDifference(double commissionDifference);

}
