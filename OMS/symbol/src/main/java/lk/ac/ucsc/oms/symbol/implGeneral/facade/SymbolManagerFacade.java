package lk.ac.ucsc.oms.symbol.implGeneral.facade;

import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.sequence_generator.api.exception.SequenceGenerationException;
import lk.ac.ucsc.oms.symbol.api.SymbolFacadeFactory;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.beans.*;
import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSSymbol;

import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.BlackListInfoBean;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.ShariaInfoBean;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolDescriptionBean;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.*;

import lk.ac.ucsc.oms.symbol.implGeneral.facade.cache.*;
import lk.ac.ucsc.oms.symbol.implGeneral.util.SymbolKeyManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public  class SymbolManagerFacade implements SymbolManager {

    private static final String CS = "CS";
    private static final String ARGUMENT_VALID_MESSAGE = "Argument to the method is valid and continue the processing";
    private static Logger logger = LogManager.getLogger(SymbolManagerFacade.class);
    private CSSymbolCacheFacade csSymbolCacheFacade;
    private AbstractSequenceGenerator csSequenceGenerator;


    /**
     * {@inheritDoc}
     */
    @Override
    public BaseSymbol getSymbol(String symbol, String exchange, BaseSymbol.SecurityType securityType) throws SymbolManageException {
        logger.debug("Loading the symbol:{} exchange:{} security type :{} ", symbol, exchange, securityType);
        if (symbol == null || "".equalsIgnoreCase(symbol)) {
            throw new SymbolManageException("Invalid Argument: Symbol can't be null or empty");
        }
        if (exchange == null || "".equalsIgnoreCase(exchange)) {
            throw new SymbolManageException("Invalid Argument: exchange can't be null or empty");
        }
        if (securityType == null) {
            throw new SymbolManageException("Invalid Argument: security type can't be null or empty");
        }
        logger.debug(ARGUMENT_VALID_MESSAGE);
        String symbolKey = SymbolKeyManager.getSymbolKey(symbol, exchange, securityType);
        logger.debug("Symbol key generated Key: {}", symbolKey);
        switch (securityType.getCode()) {
            case CS:
                return csSymbolCacheFacade.getSymbol(symbolKey);

            default:
                throw new SymbolNotFoundException("Security type not supported");
        }
    }

       /**
     * {@inheritDoc}
     */
    @Override
    public long addSymbol(BaseSymbol symbolBean) throws SymbolManageException {
        logger.info("add the symbol {} to cache", symbolBean);
        if (symbolBean == null) {
            throw new SymbolManageException("Invalid argument: Symbol bean can't be null");
        }
        validateSymbolBean(symbolBean);
        logger.debug("Symbol is valid and continue the storing process");
        String primaryKey = SymbolKeyManager.getSymbolKey(symbolBean);
        logger.debug("primaryKey :{}", primaryKey);
        if (primaryKey == null) {
            throw new SymbolManageException("Symbol contain incomplete data");
        }
        try {
            switch (symbolBean.getSecurityTypes().getCode()) {
                case CS:
                    symbolBean.setSymbolID(Long.valueOf(csSequenceGenerator.getSequenceNumber()));
                    setSymbolInnerData(symbolBean);
                    CSSymbolBean cacheObjectCS = (CSSymbolBean) symbolBean;
                    cacheObjectCS.setPrimaryKeyObject(primaryKey);
                    csSymbolCacheFacade.addSymbol(cacheObjectCS);
                    break;

                default:
                    throw new SymbolManageException("Security type not available ");
            }

        } catch (SymbolPriceException | SequenceGenerationException | NumberFormatException e) {
            logger.error("Problem in adding symbol into cache ", e);
            throw new SymbolManageException("error in adding symbol in to cache ", e);
        }
        logger.info("Symbol with id:{} stored in to cache", symbolBean.getSymbolID());
        //Need to add the code which register the symbol at price module for price update
        return symbolBean.getSymbolID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSymbol(BaseSymbol symbolBean) throws SymbolManageException {
        logger.info("update the symbol: {}", symbolBean);
        if (symbolBean == null) {
            throw new SymbolManageException("Invalid argument: Symbol bean can't be null");
        }
        validateSymbolBean(symbolBean);
        setSymbolInnerData(symbolBean);
        logger.debug("Symbol is valid and continue the updating process");
        String primaryKey = SymbolKeyManager.getSymbolKey(symbolBean);
        logger.info("primary key of the symbol: {}", primaryKey);
        if (primaryKey == null) {
            throw new SymbolManageException("Symbol contain incomplete data");
        }
        switch (symbolBean.getSecurityTypes().getCode()) {
            case CS:
                CSSymbolBean cacheObjectCS = (CSSymbolBean) symbolBean;
                cacheObjectCS.setPrimaryKeyObject(primaryKey);
                csSymbolCacheFacade.updateSymbol(cacheObjectCS);
                break;

            default:
                throw new SymbolManageException("Invalid security type");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void markSymbolAsDeleted(BaseSymbol symbolBean) throws SymbolManageException {
        logger.info("Remove a Symbol Bean: {}", symbolBean);
        if (symbolBean == null) {
            throw new SymbolManageException("Invalid argument: Symbol bean can't be null");
        }
        validateSymbolBean(symbolBean);
        logger.debug("Symbol is valid and continue the removing process");
        String primaryKey = SymbolKeyManager.getSymbolKey(symbolBean);
        if (primaryKey == null) {
            throw new SymbolManageException("Symbol contain incomplete data");
        }
        switch (symbolBean.getSecurityTypes().getCode()) {
            case CS:
                CSSymbolBean cacheObjectCS = (CSSymbolBean) symbolBean;
                cacheObjectCS.setPrimaryKeyObject(primaryKey);
                csSymbolCacheFacade.markSymbolAsDeleted(cacheObjectCS);
                break;

            default:
                throw new SymbolManageException("Security type can't recognize");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BaseSymbol> getActiveSymbolList() throws SymbolManageException {
        logger.info("Get all the symbols from active symbols tables");
        List<BaseSymbol> resultList = new ArrayList<>();
        try {
            resultList.addAll(csSymbolCacheFacade.getAllSymbols());
        } catch (SymbolManageException e) {
            logger.info("error in getting all symbols from CS Symbol persister ", e);
        }

        return resultList;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public BaseSymbol.SecurityType getSecurityType(String symbolCode, String exchange) throws SymbolManageException {
        if (symbolCode == null || "".equals(symbolCode) || exchange == null || "".equals(exchange)) {
            throw new SymbolManageException("error, given symbol code or exchange is not in proper order");
        }
        //assume symbol is CS symbol
        if (!("OPRA".equalsIgnoreCase(exchange.trim()))) {
            try {
                csSymbolCacheFacade.getSymbol(SymbolKeyManager.getSymbolKey(symbolCode, exchange, BaseSymbol.SecurityType.COMMON_STOCK));
                return BaseSymbol.SecurityType.COMMON_STOCK;
            } catch (SymbolNotFoundException e) {
                logger.debug("symbol is not a CS Symbol");
            }
        }

        throw new SymbolManageException("error symbol security type not found");
    }

    /**
     * {@inheritDoc}
     */
    private void validateSymbolBean(BaseSymbol symbolBean) throws SymbolManageException {
        if (symbolBean.getSymbol() == null || "".equalsIgnoreCase(symbolBean.getSymbol())) {
            throw new SymbolManageException("symbol can't be null or empty");
        }
        if (symbolBean.getSecurityExchange() == null || "".equalsIgnoreCase(symbolBean.getSecurityExchange())) {
            throw new SymbolManageException("Security exchange can't be null or empty");
        }
        if (symbolBean.getSecurityTypes() == null) {
            throw new SymbolManageException("security type can't be null or empty");
        }
    }





    /**
     * {@inheritDoc}
     */
    @Override
    public ShariaInfo getEmptyShariaInfoBean(BaseSymbol.SecurityType securityType) {
        switch (securityType.getCode()) {
            case CS:
                return new CSShariaInfoBean();
            default:
                return new ShariaInfoBean();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlackListInfo getEmptyBlackListInfoBean(BaseSymbol.SecurityType securityType) {
        switch (securityType.getCode()) {
            case CS:
                return new CSBlackListInfoBean();
            default:
                return new BlackListInfoBean();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolDescription getEmptySymbolDescriptionBean(BaseSymbol.SecurityType securityType) {
        switch (securityType.getCode()) {
            case CS:
                return new CSSymbolDescriptionBean();
            default:
                return new SymbolDescriptionBean();
        }
    }


    private void setSymbolInnerData(BaseSymbol symbol) {
        logger.info("Set symbol id for map entries ", symbol);
        if (symbol.getOrgWiseMarginInfo() != null && !symbol.getOrgWiseMarginInfo().isEmpty()) {
            for (SymbolMarginInfo symbolMarginInfo : symbol.getOrgWiseMarginInfo().values()) {
                symbolMarginInfo.setSymbolId(symbol.getSymbolID());
            }
        }
        if (symbol.getShariaEnableOrganisations() != null && !symbol.getShariaEnableOrganisations().isEmpty()) {
            for (ShariaInfo shariaInfo : symbol.getShariaEnableOrganisations().values()) {
                shariaInfo.setSymbolId(symbol.getSymbolID());
            }
        }
        if (symbol.getBlackListedOrganisations() != null && !symbol.getBlackListedOrganisations().isEmpty()) {
            for (BlackListInfo blackListInfo : symbol.getBlackListedOrganisations().values()) {
                blackListInfo.setSymbolId(symbol.getSymbolID());
            }
        }
        if (symbol.getSymbolDescriptions() != null && !symbol.getSymbolDescriptions().isEmpty()) {
            for (SymbolDescription symbolDescription : symbol.getSymbolDescriptions().values()) {
                symbolDescription.setSymbolId(symbol.getSymbolID());
            }
        }
        logger.info("Set symbol id for map entries finished ", symbol);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CSSymbol getEmptyCSSymbolBean(String exchange, String symbol) {
        return new CSSymbolBean(symbol, exchange);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() throws SymbolManageException {
        // Loading symbols into cache
        csSymbolCacheFacade.initialize();
    }


    /**
     * set the implementation of the CS symbol cache facade which contain the detail of managing CS symbol
     *
     * @param csSymbolCacheFacade CSSymbolCacheFacade MubasherPricePluginImpl
     */
    public void setCsSymbolCacheFacade(CSSymbolCacheFacade csSymbolCacheFacade) {
        this.csSymbolCacheFacade = csSymbolCacheFacade;
    }

    public void setCsSequenceGenerator(AbstractSequenceGenerator csSequenceGenerator) {
        this.csSequenceGenerator = csSequenceGenerator;
    }
}
