package lk.ac.ucsc.oms.customer.implGeneral.beans.cash;

import lk.ac.ucsc.oms.customer.api.beans.cash.BalanceAndDues;


public class BalanceAndDuesBean implements BalanceAndDues{
    private double cashBalance;
    private double overNightMarginDue;
    private double dayMarginDue;
    private double shortSellCashBalance;

    @Override
    public double getCashBalance() {
        return cashBalance;
    }

    @Override
    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    @Override
    public double getOverNightMarginDue() {
        return overNightMarginDue;
    }

    @Override
    public void setOverNightMarginDue(double overNightMarginDue) {
        this.overNightMarginDue = overNightMarginDue;
    }

    @Override
    public double getDayMarginDue() {
        return dayMarginDue;
    }

    @Override
    public void setDayMarginDue(double dayMarginDue) {
        this.dayMarginDue = dayMarginDue;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BalanceAndDuesBean{" +
                "cashBalance=" + cashBalance +
                ", overNightMarginDue=" + overNightMarginDue +
                ", dayMarginDue=" + dayMarginDue +
                ", shortSellCashBalance=" + shortSellCashBalance +
                '}';
    }
}
