package lk.ac.ucsc.oms.exchanges.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderTIF;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.exchanges.api.ExchangeValidator;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.beans.MarketStatus;
import lk.ac.ucsc.oms.exchanges.api.beans.SSBMarketInfo;
import lk.ac.ucsc.oms.exchanges.api.beans.SubMarket;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ExchangeValidatorImpl implements ExchangeValidator {
    private static Logger logger = LogManager.getLogger(ExchangeValidatorImpl.class);
    private ExchangeManagerFacade exchangeManagerFacade;

    public ExchangeValidatorImpl(ExchangeManagerFacade exchangeManagerFacade) {
        this.exchangeManagerFacade = exchangeManagerFacade;
    }

    @Override
    public boolean isValidExchange(String exchangeCode) throws ExchangeException {
        try {
            if (exchangeCode == null || ("").equals(exchangeCode)) {
                logger.error("exchange code can't be null or empty");
                throw new ExchangeException("exchange code null or empty");
            }
            Exchange exchange = null;
            exchange = exchangeManagerFacade.getExchange(exchangeCode);
            if (exchange == null) {
                logger.error("Invalid exchange code");
                throw new ExchangeException("Invalid exchange code");
            }
        } catch (ExchangeException e) {
            logger.error("Error in validate exchange", e);
            throw new ExchangeException("Issue in validate exchange", e);
        }
        return true;
    }

    @Override
    public boolean isMarketOrderAllow(String exchangeCode, SubMarket subMarket, ClientChannel orderChannel, OrderType orderType, String execBrokerId) throws ExchangeException {
        if (subMarket == null || orderChannel == null || orderType == null) {
            logger.error("input data error");
            throw new ExchangeException("input data error");
        }
        if (orderType == OrderType.MARKET && orderChannel != ClientChannel.MANUAL) {
            MarketStatus marketStatus = exchangeManagerFacade.getSubMarketStatus(execBrokerId, exchangeCode, subMarket.getMarketCode());
            logger.info("Sub Market Status - {}", marketStatus);
            if (marketStatus == MarketStatus.OPEN || marketStatus == MarketStatus.PRE_OPEN) {
                return true;
            }
        } else {
            return true; // bypass other types of orders
        }
        return false;
    }

    @Override
    public boolean isAllowTif(SubMarket subMarket, OrderTIF tifType) throws ExchangeException {
        if (subMarket == null || tifType == null) {
            logger.error("input data error");
            throw new ExchangeException("input data error");
        }
        List<OrderTIF> allowTIFs = subMarket.getOrderRoutingInfo().getAllowTif();
        return allowTIFs.contains(tifType);
    }


    @Override
    public boolean isAllowOrderType(SubMarket subMarket, OrderType type) throws ExchangeException {
        if (subMarket == null || type == null) {
            logger.error("input data error");
            throw new ExchangeException("input data error");
        }
        List<OrderType> allowTypes = subMarket.getOrderRoutingInfo().getAllowType();
        return allowTypes.contains(type);
    }

    @Override
    public boolean isAllowSide(SubMarket subMarket, OrderSide side) throws ExchangeException {
        if (subMarket == null || side == null) {
            logger.error("input data error");
            throw new ExchangeException("input data error");
        }
        List<OrderSide> allowSides = subMarket.getOrderRoutingInfo().getAllowOrdSide();
        return allowSides.contains(side);
    }

    @Override
    public boolean isAmendAllow(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException {
        Exchange exchange;
        try {
            exchange = exchangeManagerFacade.getExchange(exchangeCode);
            SubMarket subMarket = exchange.getSubMarkets().get(marketCode);
            MarketStatus subMarketStatus = exchangeManagerFacade.getSubMarketStatus(execBrokerId, exchangeCode, marketCode);
            logger.info("Sub Market Status - {}", subMarketStatus);
            if (subMarketStatus == MarketStatus.PRE_OPEN && subMarket.isManuallySuspend()) {
                return false;
            }
            //allow to amend only when market open
            if (subMarketStatus != MarketStatus.PRE_OPEN && subMarketStatus != MarketStatus
                    .OPEN) {
                return false;
            }
            // if the market is pre open and the manual suspended at the executing broker level not allow amend
            if (execBrokerId != null && !("").equals(execBrokerId) && subMarket != null && subMarket.getSsbMarketInfo() != null && !subMarket.getSsbMarketInfo().isEmpty()) {
                SSBMarketInfo ssbMarketInfo = null;
                if (subMarket.getSsbMarketInfo().containsKey(execBrokerId)) {
                    ssbMarketInfo = subMarket.getSsbMarketInfo().get(execBrokerId);
                }
                if (ssbMarketInfo != null && ssbMarketInfo.getMarketStatus() == MarketStatus.PRE_OPEN && ssbMarketInfo.isManuallySuspend()) {
                    return false;
                }
            }
        } catch (ExchangeException e) {
            logger.error("Error while checking is amend allow ", e);
            throw new ExchangeException("Issue while checking amend allow", e);
        }
        return true;
    }

    @Override
    public boolean isCancelAllow(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException {
        Exchange exchange;
        try {
            exchange = exchangeManagerFacade.getExchange(exchangeCode);
            SubMarket subMarket = exchange.getSubMarkets().get(marketCode);

            MarketStatus subMarketStatus = exchangeManagerFacade.getSubMarketStatus(execBrokerId, exchangeCode, marketCode);
            logger.info("Sub Market Status - {}", subMarketStatus);
            if (subMarketStatus == MarketStatus.PRE_OPEN && subMarket.isManuallySuspend()) {
                return false;
            }
            //allow to cancel only when market open
            if (subMarketStatus != MarketStatus.PRE_OPEN && subMarketStatus != MarketStatus.OPEN) {
                return false;
            }
            // if the market is pre open and the manual suspended at the executing broker level not allow cancel
            if (execBrokerId != null && !("").equals(execBrokerId) && subMarket != null && subMarket.getSsbMarketInfo() != null && !subMarket.getSsbMarketInfo().isEmpty()) {
                SSBMarketInfo ssbMarketInfo = null;
                if (subMarket.getSsbMarketInfo().containsKey(execBrokerId)) {
                    ssbMarketInfo = subMarket.getSsbMarketInfo().get(execBrokerId);
                }
                if (ssbMarketInfo != null && ssbMarketInfo.getMarketStatus() == MarketStatus.PRE_OPEN && ssbMarketInfo.isManuallySuspend()) {
                    return false;
                }
            }
        } catch (ExchangeException e) {
            logger.error("Error while checking is cancel allow", e);
            throw new ExchangeException("Issue while checking is cancel allow", e);
        }
        return true;
    }

    @Override
    public boolean isOrderAllow(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException {
        Exchange exchange;
        try {
            exchange = exchangeManagerFacade.getExchange(exchangeCode);
            long orderNotAllowDuration = exchange.getOrderNotAllowDuration();
            Date lastEodDate = exchangeManagerFacade.getLastEODDate(exchangeCode, marketCode, execBrokerId);
            Date today = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            today = format.parse(format.format(today));
            Date notAllowedTill = new Date(lastEodDate.getTime() + orderNotAllowDuration * 60 * 1000);
            Date eodDate = format.parse(format.format(lastEodDate));
            if (orderNotAllowDuration > 0 && today.equals(eodDate) && System.currentTimeMillis() < notAllowedTill.getTime()) {
                return false;
            }

        } catch (Exception e) {
            logger.error("Error while checking is amend allow ", e);
            throw new ExchangeException("Issue while checking amend allow", e);
        }
        return true;
    }


}
