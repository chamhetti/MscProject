package lk.ac.ucsc.oms.exchanges.api;

import lk.ac.ucsc.oms.exchanges.api.beans.*;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public interface ExchangeManager {


    void initialize() throws ExchangeException;

    String getMubasherExchangeCode(String exDestination, String exchange) throws ExchangeException;

    List<String> getAllExchangeCodes() throws ExchangeException;

    Exchange getExchange(String exchangeCode) throws ExchangeException;

    void addExchange(Exchange exchange) throws ExchangeException;

    void updateExchange(Exchange exchange) throws ExchangeException;

    void markExchangeAsDeleted(String exchangeCode) throws ExchangeException;

    String getChannelsExchangeCode(String exchangeCode, int channelId) throws ExchangeException;

    boolean isMarketOpen(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException;

    List<Exchange> getAllExchanges() throws ExchangeException;

    Date getLastPreOpenDate(String exchange, String marketCode, String execBrokerId) throws ExchangeException;

    Date getLastEODDate(String exchange, String marketCode, String execBrokerId) throws ExchangeException;

    List<Exchange> getExchangeList() throws ExchangeException;

    Exchange getEmptyExchange(String exchangeCode) throws ExchangeException;

    OrderRoutingInfo getOrderRoutingInfo();

    SubMarket getEmptySubMarket();

    MarketStatus getSubMarketStatus(String execBrokerId, String exchangeCode, String marketCode);

    MarketConnectStatus getSubMarketConnectStatus(String execBrokerId, String exchangeCode, String marketCode);

    void setSubMarketStatus(SubMarket subMarket, String execBrokerId, MarketStatus marketStatus);
}
