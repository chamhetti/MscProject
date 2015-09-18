package lk.ac.ucsc.oms.symbol.implGeneral.facade;

import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.SymbolStatusMessages;
import lk.ac.ucsc.oms.symbol.api.SymbolValidator;
import lk.ac.ucsc.oms.symbol.api.beans.*;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolValidationReplyBean;


public class SymbolValidatorFacade implements SymbolValidator {
    private SymbolManager symbolManager;

    /**
     * Method to set symbol manager
     *
     * @param symbolManager instance
     */
    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolValidationReply isValidSymbol(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException {
        BaseSymbol baseSymbol = symbolManager.getSymbol(symbol, exchange, securityType);

        //Check whether symbol is active or not
        if (baseSymbol.getStatus() != BaseSymbol.SymbolStatus.APPROVED) {
            return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.FAILED, SymbolStatusMessages.SYMBOL_NOT_APPROVED);
        }
        //trade enable or not for the symbol
        if (!baseSymbol.isTradeEnable()) {
            return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.FAILED, SymbolStatusMessages.TRADING_DISABLE);
        }
        return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.SUCCESS, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolValidationReply isOrderQtyAllowForSymbol(String symbol, String exchange, BaseSymbol.SecurityType securityType, double ordQty) throws SymbolManageException {
        BaseSymbol symbolBean = symbolManager.getSymbol(symbol, exchange, securityType);
        if (ordQty < symbolBean.getMinOrdSize()) {
            return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.FAILED, SymbolStatusMessages.INVALID_MIN_QTY);
        }
        return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.SUCCESS, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolValidationReply isNationalityRestrictedSymbol(String symbol, String exchange, BaseSymbol.SecurityType secType, String customerNationality, boolean isGcc) throws SymbolManageException {
        BaseSymbol symbolBean = symbolManager.getSymbol(symbol, exchange, secType);
        switch (symbolBean.getAccessLevels()) {
            case INTERNATIONAL:
                return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.SUCCESS, null);
            case REGIONAL:
                if (!isGcc) {
                    return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.FAILED, SymbolStatusMessages.LOCAL_ONLY_SYMBOL);
                }
            case LOCAL:
                if (!symbolBean.getNationality().equalsIgnoreCase(customerNationality)) {
                    return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.FAILED, SymbolStatusMessages.LOCAL_ONLY_SYMBOL);
                }
                break;
            default:
                break;
        }
        return new SymbolValidationReplyBean(SymbolValidationReply.SymbolValidationResult.SUCCESS, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOnlineAllowed(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException {
        BaseSymbol symbolBean = symbolManager.getSymbol(symbol, exchange, securityType);
        return symbolBean.isOnlineAllow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRestrictedForInstitution(String symbol, String exchange, BaseSymbol.SecurityType securityType, int instId) throws SymbolManageException {
        BaseSymbol symbolBean = symbolManager.getSymbol(symbol, exchange, securityType);
        BlackListInfo info = symbolBean.getBlackListedOrganisations().get(instId);
        return info != null && info.getBlackListed() == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShariaCompliant(String symbol, String exchange, BaseSymbol.SecurityType securityType, int instId) throws SymbolManageException {
        BaseSymbol symbolBean = symbolManager.getSymbol(symbol, exchange, securityType);
        ShariaInfo shariaInfo = symbolBean.getShariaEnableOrganisations().get(instId);
        return shariaInfo != null && shariaInfo.isShariaEnable();
    }



}
