package lk.ac.ucsc.oms.exchanges.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean;

import java.util.List;


public interface ExchangePersister extends CachePersister {


    boolean deleteFromDB(ExchangeBean exchangeBean);

    String getMubasherExchangeCodes(String exDestination, String exchange);

    List<String> getAllExchangeCodes();

    String getLastExchangeId();

    String getLastExchangeSubMarketId();

    List<Exchange> getAllExchanges();

    void updateSuspendedStatusForSubMarket();
}
