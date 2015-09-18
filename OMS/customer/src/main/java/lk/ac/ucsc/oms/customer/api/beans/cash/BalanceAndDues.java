package lk.ac.ucsc.oms.customer.api.beans.cash;

public interface BalanceAndDues {

    void setCashBalance(double cashBalance);

    void setDayMarginDue(double dayMarginDue);

    void setOverNightMarginDue(double overNightMarginDue);

    double getCashBalance();

    double getOverNightMarginDue();

    double getDayMarginDue();

}
