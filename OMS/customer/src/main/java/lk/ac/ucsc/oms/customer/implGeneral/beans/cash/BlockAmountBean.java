package lk.ac.ucsc.oms.customer.implGeneral.beans.cash;

import lk.ac.ucsc.oms.customer.api.beans.cash.BlockAmount;


public class BlockAmountBean implements BlockAmount{
    private double cashBlock;

    @Override
    public double getCashBlock() {
        return cashBlock;
    }

    @Override
    public void setCashBlock(double cashBlock) {
        this.cashBlock = cashBlock;
    }


}
