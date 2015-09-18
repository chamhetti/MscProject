package lk.ac.ucsc.oms.exchanges.api;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderTIF;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.exchanges.api.beans.SubMarket;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;


public interface ExchangeValidator {

    boolean isValidExchange(String exchangeCode) throws ExchangeException;


    boolean isMarketOrderAllow(String exchangeCode, SubMarket subMarket, ClientChannel orderChannel, OrderType orderType, String execBrokerId) throws ExchangeException;

    boolean isAllowTif(SubMarket subMarket, OrderTIF tifType) throws ExchangeException;

    boolean isAllowOrderType(SubMarket subMarket, OrderType type) throws ExchangeException;

    boolean isAllowSide(SubMarket subMarket, OrderSide side) throws ExchangeException;

    boolean isAmendAllow(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException;

    boolean isCancelAllow(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException;


    boolean isOrderAllow(String exchangeCode, String marketCode, String execBrokerId) throws ExchangeException;

}
