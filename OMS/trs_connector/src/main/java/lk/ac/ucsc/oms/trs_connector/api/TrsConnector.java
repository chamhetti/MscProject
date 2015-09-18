package lk.ac.ucsc.oms.trs_connector.api;




import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.PortfolioHeader;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.TradingAccount;



public interface TrsConnector {
    void processTrsMessage(String mixString);

    PortfolioHeader getEmptyPortfolioHeader();

    TradingAccount getEmptyTradingAccount();

}
