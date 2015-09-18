package lk.ac.ucsc.oms.exchanges.implGeneral.facade;

import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.exchanges.api.beans.*;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.OrderRoutingInfoBean;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.SubMarketBean;
import lk.ac.ucsc.oms.exchanges.implGeneral.facade.cache.ExchangeCacheFacade;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.sequence_generator.api.exception.SequenceGenerationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ExchangeManagerFacade implements ExchangeManager {
    private static Logger logger = LogManager.getLogger(ExchangeManagerFacade.class);
    private ExchangeCacheFacade exchangeCacheFacade;
    private static final String MESSAGE_EXCHANGE_CODE_CANT_BE_NULL = "Exchange code can't be null";

    private AbstractSequenceGenerator sequenceGenerator;
    private AbstractSequenceGenerator subMarketSeqGenerator;

    public AbstractSequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    public AbstractSequenceGenerator getSubMarketSeqGenerator() {
        return subMarketSeqGenerator;
    }


    public void setExchangeCacheFacade(ExchangeCacheFacade exchangeCacheFacade) {
        this.exchangeCacheFacade = exchangeCacheFacade;
    }

    @Override
    public void initialize() throws ExchangeException {
        try {
            exchangeCacheFacade.initialize();
        } catch (ExchangeException e) {
            logger.error("Error in initialize" + e.getMessage(), e);
            throw new ExchangeException("Issue in initialize", e);
        }

    }


    @Override
    public String getMubasherExchangeCode(String exDestination, String exchange) throws ExchangeException {
        return exchangeCacheFacade.getMubasherExchangeCode(exDestination, exchange);
    }


    @Override
    public List<String> getAllExchangeCodes() throws ExchangeException {
        return exchangeCacheFacade.getAllExchangeCodes();
    }


    @Override
    public Exchange getExchange(String exchangeCode) throws ExchangeException {
        logger.info("Get the exchange with exchange code- {}", exchangeCode);
        if (exchangeCode == null || ("").equalsIgnoreCase(exchangeCode)) {
            logger.error("Exchange code can't be null or empty");
            throw new ExchangeException("exchange code can't be null or empty");
        }
        return exchangeCacheFacade.getExchange(exchangeCode);
    }


    @Override
    public void addExchange(Exchange exchange) throws ExchangeException {
        logger.info("Adding the exchange {} to cache and DB {}", exchange);
        if (exchange == null) {
            logger.error("Exchange bean can't be null");
            throw new ExchangeException("Exchange bean  can't be null");
        }
        validateExchangeBean(exchange);
        ExchangeBean exchangeBean = (ExchangeBean) exchange;
        try {
            logger.debug("Getting the exchange id from sequence generator");
            String sequenceID = sequenceGenerator.getSequenceNumber();
            exchangeBean.setExchangeId(Long.parseLong(sequenceID));
            logger.info("exchange Id of the new exchange {}", exchangeBean.getExchangeId());

            logger.debug("Getting sub market id from sequence generator");
            for (SubMarket subMarket : exchangeBean.getSubMarkets().values()) {
                String seqId = subMarketSeqGenerator.getSequenceNumber();
                subMarket.setSubMarketId(Integer.parseInt(seqId));
                subMarket.getOrderRoutingInfo().setSubMarketId(Integer.parseInt(seqId));
            }


        } catch (Exception e) {
            logger.error("Issue in adding new symbol", e);
            throw new ExchangeException("Problem in adding new symbol", e);
        }
        exchangeBean.setPrimaryKeyObject(exchangeBean.getExchangeCode());
        exchangeCacheFacade.putExchange(exchangeBean);
        logger.debug("Adding exchange process finished {}", exchangeBean.getExchangeId());
        exchangeBean.getExchangeId();
    }

    @Override
    public void updateExchange(Exchange exchange) throws ExchangeException {
        logger.info("Update the exchange {}", exchange);
        if (exchange == null) {
            throw new ExchangeException("Exchange can't be null");
        }
        validateExchangeBean(exchange);
        ExchangeBean exchangeBean = (ExchangeBean) exchange;
        exchangeBean.setPrimaryKeyObject(exchangeBean.getExchangeCode());

        for (SubMarket subMarket : exchangeBean.getSubMarkets().values()) {
            if (subMarket.getSubMarketId() <= 0) {
                String seqId = null;
                try {
                    seqId = subMarketSeqGenerator.getSequenceNumber();
                } catch (SequenceGenerationException e) {
                    logger.error("Error in adding new sub market - can't get sub market id from generator ", e);
                    throw new ExchangeException("Error in adding new sub market - can't get sub market id from generator ", e);
                }
                int id = Integer.parseInt(seqId);
                subMarket.setSubMarketId(id);
                subMarket.getOrderRoutingInfo().setSubMarketId(id);
            }
        }

        exchangeCacheFacade.updateExchange(exchangeBean);
    }


    @Override
    public void markExchangeAsDeleted(String exchangeCode) throws ExchangeException {
        if (exchangeCode == null || ("").equalsIgnoreCase(exchangeCode)) {
            throw new ExchangeException(MESSAGE_EXCHANGE_CODE_CANT_BE_NULL);
        }
        ExchangeBean exchangeBean = (ExchangeBean) getExchange(exchangeCode);
        if (exchangeBean == null) {
            throw new ExchangeException("No Exchange found for this exchange code - " + exchangeCode);
        }
        exchangeBean.setPrimaryKeyObject(exchangeCode);
        exchangeCacheFacade.markExchangeAsDeleted(exchangeBean);
    }

    @Override
    public String getChannelsExchangeCode(String exchangeCode, int channelId) throws ExchangeException {
        if (exchangeCode == null || ("").equalsIgnoreCase(exchangeCode)) {
            throw new ExchangeException(MESSAGE_EXCHANGE_CODE_CANT_BE_NULL);
        }
        try {
            ExchangeBean exchange = exchangeCacheFacade.getExchange(exchangeCode);
            if (exchange == null) {
                throw new ExchangeException("Error:exchange is null");
            }
            return exchange.getChannelWiseExchangeCodes().get(channelId);
        } catch (ExchangeException e) {
            logger.error("Error while getting channels exchange codes", e);
            throw new ExchangeException(e.getMessage(), e);
        }
    }


    private void validateExchangeBean(Exchange exchange) throws ExchangeException {
        if (exchange.getExchangeCode() == null || ("").equalsIgnoreCase(exchange.getExchangeCode())) {
            throw new ExchangeException("Exchange code can't be null or empty");
        }
    }


    @Override
    public boolean isMarketOpen(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException {
        if (exchangeCode == null || ("").equalsIgnoreCase(exchangeCode) || marketCode == null || ("").equals(marketCode)) {
            throw new ExchangeException(MESSAGE_EXCHANGE_CODE_CANT_BE_NULL);
        }
        //check whether market is open or not
        Exchange exchange = null;
        try {
            exchange = getExchange(exchangeCode);
        } catch (ExchangeException e) {
            logger.error("Error: can't find exchange {}", e);
            throw new ExchangeException(e.getMessage(), e);
        }
        SubMarket subMarket = exchange.getSubMarkets().get(marketCode);
        if (subMarket == null) {
            throw new ExchangeException("sub market is null");
        }
        MarketStatus subMarketStatus = getSubMarketStatus(execBrokerId, exchangeCode, marketCode);
        logger.info("Sub Market Status - ", subMarketStatus);
        return subMarketStatus == MarketStatus.OPEN || subMarketStatus == MarketStatus.PRE_OPEN;

    }


    @Override
    public List<Exchange> getAllExchanges() throws ExchangeException {
        return exchangeCacheFacade.getAllExchanges();
    }

    @Override
    public Date getLastPreOpenDate(String exchange, String marketCode, String execBrokerId) throws ExchangeException {
        if (exchange == null || ("").equalsIgnoreCase(exchange) || marketCode == null || ("").equals(marketCode)) {
            throw new ExchangeException(MESSAGE_EXCHANGE_CODE_CANT_BE_NULL);
        }
        try {
            ExchangeBean exchangeBean = exchangeCacheFacade.getExchange(exchange);
            SubMarketBean subMarketBean = (SubMarketBean) exchangeBean.getSubMarkets().get(marketCode);
            if (execBrokerId != null && !("").equals(execBrokerId) && subMarketBean.getSsbMarketInfo() != null && !subMarketBean.getSsbMarketInfo().isEmpty()) {
                    SSBMarketInfo ssbMarketInfo = subMarketBean.getSsbMarketInfo().get(execBrokerId);
                    if (ssbMarketInfo != null) {
                        logger.info("Retrieving last pre open date from ssb market info for exec id : " + execBrokerId, ssbMarketInfo);
                        subMarketBean.setLastPreOpenedDate(ssbMarketInfo.getLastPreOpenedDate());
                    }
            }
            return subMarketBean.getLastPreOpenedDate();
        } catch (ExchangeException e) {
            logger.error("problem in getting last pre open date {}", e);
            throw new ExchangeException(e.getMessage(), e);
        }

    }


    @Override
    public Date getLastEODDate(String exchange, String marketCode, String execBrokerId) throws ExchangeException {
        if (exchange == null || ("").equalsIgnoreCase(exchange) || marketCode == null || ("").equals(marketCode)) {
            throw new ExchangeException(MESSAGE_EXCHANGE_CODE_CANT_BE_NULL);
        }
        try {
            ExchangeBean exchangeBean = exchangeCacheFacade.getExchange(exchange);
            SubMarket subMarketBean = exchangeBean.getSubMarkets().get(marketCode);

            if (execBrokerId != null && !("").equals(execBrokerId) && subMarketBean.getSsbMarketInfo() != null && !subMarketBean.getSsbMarketInfo().isEmpty()) {
                    SSBMarketInfo ssbMarketInfo = subMarketBean.getSsbMarketInfo().get(execBrokerId);
                    if (ssbMarketInfo != null) {
                        logger.info("Retrieving last eod date from ssb market info for exec id : " + execBrokerId, ssbMarketInfo);
                        subMarketBean.setLastEODDate(ssbMarketInfo.getLastEODDate());
                    }
            }
            return subMarketBean.getLastEODDate();
        } catch (ExchangeException e) {
            logger.error("problem in getting last eod date {}", e);
            throw new ExchangeException(e.getMessage(), e);
        }

    }


    @Override
    public List<Exchange> getExchangeList() throws ExchangeException {
        return exchangeCacheFacade.getExchangeList();
    }


    @Override
    public Exchange getEmptyExchange(String exchangeCode) throws ExchangeException {
        try {
            return new ExchangeBean(exchangeCode);
        } catch (Exception e) {
            throw new ExchangeException("Error creating empty exchange.", e);
        }
    }



    public OrderRoutingInfo getOrderRoutingInfo() {
        return new OrderRoutingInfoBean();
    }


    public SubMarket getEmptySubMarket() {
        return new SubMarketBean();
    }



    @Override
    public MarketStatus getSubMarketStatus(String execBrokerId, String exchangeCode, String marketCode) {

        logger.info("Retrieving sub market status of : " + exchangeCode + " market code : " + marketCode + " sell side broker : " + execBrokerId);
        Exchange exchange = null;
        if (exchangeCode == null || ("").equals(exchangeCode)) {
            return MarketStatus.UNKNOWN;
        }
        try {
            exchange = exchangeCacheFacade.getExchange(exchangeCode);
        } catch (ExchangeException e) {
            logger.error("Error in retrieving exchange {}", e);
            return MarketStatus.UNKNOWN;
        }

        if (marketCode != null && !("").equals(marketCode)) {
            if (exchange != null) {
                SubMarket subMarket = exchange.getSubMarkets().get(marketCode);
                if (subMarket != null) {
                    if (execBrokerId != null && !("").equals(execBrokerId) && Integer.parseInt(execBrokerId) > 0) {
                        logger.info("Getting the sub market status of sell side broker : " + execBrokerId);
                        if (subMarket.getSsbMarketInfo().containsKey(execBrokerId)) {
                            return subMarket.getSsbMarketInfo().get(execBrokerId).getMarketStatus();
                        }
                    }
                    logger.info("Getting the market status of sub market : " + marketCode);
                    return subMarket.getMarketStatus();
                }
            }

        } else {
            return MarketStatus.UNKNOWN;
        }

        return MarketStatus.UNKNOWN;
    }

    @Override
    public MarketConnectStatus getSubMarketConnectStatus(String execBrokerId, String exchangeCode, String marketCode) {

        logger.info("Retrieving sub market status of : " + exchangeCode + " market code : " + marketCode + " sell side broker : " + execBrokerId);
        Exchange exchange = null;
        if (exchangeCode == null || ("").equals(exchangeCode)) {
            return MarketConnectStatus.UNKNOWN;
        }
        try {
            exchange = exchangeCacheFacade.getExchange(exchangeCode);
        } catch (ExchangeException e) {
            logger.error("Error in retrieving exchange", e);
            return MarketConnectStatus.UNKNOWN;
        }

        if (marketCode != null && !("").equals(marketCode)) {
            if (exchange != null) {
                SubMarket subMarket = exchange.getSubMarkets().get(marketCode);
                if (subMarket != null) {
                    if (execBrokerId != null && !("").equals(execBrokerId) && Integer.parseInt(execBrokerId) > 0) {
                        logger.info("Getting the sub market status of sell side broker : {}", execBrokerId);
                        if (subMarket.getSsbMarketInfo().containsKey(execBrokerId)) {
                            return subMarket.getSsbMarketInfo().get(execBrokerId).getConnectStatus();
                        }
                    }
                    logger.info("Getting the market connect status of sub market : {}", marketCode);
                    return subMarket.getConnectStatus();
                }
            }

        } else {
            return MarketConnectStatus.UNKNOWN;
        }

        return MarketConnectStatus.UNKNOWN;
    }

    @Override
    public void setSubMarketStatus(SubMarket subMarket, String execBrokerId, MarketStatus marketStatus) {

        logger.info("Set sub market status of sub market {}", subMarket);
        if (subMarket != null) {
            if (subMarket.getSsbMarketInfo() != null) {
                if (execBrokerId != null && !("").equals(execBrokerId) && Integer.parseInt(execBrokerId) > 0) {
                    logger.info("Setting sell side broker market status : " + execBrokerId + " into : " + marketStatus);
                    subMarket.getSsbMarketInfo().get(execBrokerId).setMarketStatus(marketStatus);
                }

                for (String execId : subMarket.getSsbMarketInfo().keySet()) {
                    if (subMarket.getSsbMarketInfo().get(execId).getMarketStatus() == MarketStatus.OPEN) {
                        subMarket.setMarketStatus(MarketStatus.OPEN);
                        break;
                    }
                    subMarket.setMarketStatus(marketStatus);
                }
            } else {
                subMarket.setMarketStatus(marketStatus);
            }
            logger.info("Final Sub market status : " + subMarket.getMarketStatus());
        }
    }



    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public void setSubMarketSeqGenerator(AbstractSequenceGenerator subMarketSeqGenerator) {
        this.subMarketSeqGenerator = subMarketSeqGenerator;
    }
}
