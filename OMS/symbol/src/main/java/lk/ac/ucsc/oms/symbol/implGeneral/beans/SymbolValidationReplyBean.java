package lk.ac.ucsc.oms.symbol.implGeneral.beans;

import lk.ac.ucsc.oms.symbol.api.SymbolStatusMessages;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolValidationReply;


public class SymbolValidationReplyBean implements SymbolValidationReply {
    private SymbolValidationResult validationResult;
    private SymbolStatusMessages rejectReason;

    /**
     * Constructor with symbol validation result and reject reason as constructor
     * arguments
     *
     * @param validationResult symbol validation result enum
     * @param rejectReason     symbol status message enum
     */
    public SymbolValidationReplyBean(SymbolValidationResult validationResult, SymbolStatusMessages rejectReason) {
        this.validationResult = validationResult;
        this.rejectReason = rejectReason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolValidationResult getValidationResult() {
        return validationResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolStatusMessages getRejectReason() {
        return rejectReason;
    }
}
