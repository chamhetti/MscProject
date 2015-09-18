package lk.ac.ucsc.oms.execution_controller;

import lk.ac.ucsc.oms.exchanges.api.ExchangeFactory;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.beans.MarketStatus;
import lk.ac.ucsc.oms.exchanges.api.beans.SubMarket;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFacade;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;

import lk.ac.ucsc.oms.stp_connector.api.MarketStatusMessageController;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnector;
import lk.ac.ucsc.oms.execution_controller.helper.util.ModuleDINames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TradingSessionStatusProcessor implements MarketStatusMessageController {
    private static Logger logger = LogManager.getLogger(ExecutionController.class);
    private ApplicationContext ctx;
    private FIXConnectionFacade fixConnectionFacade;
    private ExchangeFactory exchangeFactory;
    private static int STATUS_REQUEST_ALLOW = 1;

    public TradingSessionStatusProcessor() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("/spring-config-execution_controller.xml");
        }
        fixConnectionFacade = ctx.getBean(ModuleDINames.FIX_CONNECTION_FACADE, FIXConnectionFacade.class);
        exchangeFactory = ctx.getBean(ModuleDINames.EXCHANGE_FACTORY_DI_NAME, ExchangeFactory.class);
    }

    public TradingSessionStatusProcessor(ApplicationContext applicationContext) {

    }

    @Override
    public void processTradingSessionStatus(FixOrderInterface fixorder, String sid) {
        logger.info("Market status message received for sid -{} is -{}", sid, fixorder);

        try {
            //get the fix connection
            FIXConnection fixConnection = fixConnectionFacade.getFixConnection(sid);

            if (fixConnection.getStatusRequestAllow() != STATUS_REQUEST_ALLOW) {
                logger.info("Trading Session Status request is disabled for this fix connection. No need to continue");
                return;
            }
            String exchangeCode = fixConnection.getExchangeCode();
            if (exchangeCode != null && !("").equals(exchangeCode)) {
                Exchange exchange = exchangeFactory.getExchangeManager().getExchange(exchangeCode);
                if (exchange != null && fixorder.getTradSesStatus() > FIXConstants.T340_UNKNOWN && fixorder
                        .getTradSesStatus() <
                        FIXConstants.T340_REQUEST_REJECTED) {
                    //getting the sub market related to this status message
                    SubMarket subMarket = exchange.getSubMarkets().get(fixorder.getTradingSessionID());
                    if (subMarket != null) {
                        //update the market status of the corresponding sub market
                        exchangeFactory.getExchangeManager().setSubMarketStatus(subMarket, fixorder.getBrokerID(), MarketStatus.getEnum(fixorder.getTradSesStatus()));
                        exchange.getSubMarkets().put(fixorder.getTradingSessionID(), subMarket);
                        exchangeFactory.getExchangeManager().updateExchange(exchange);
                    }
                    if (fixorder.getTradSesStatus() == FIXConstants.T340_PRE_OPEN && subMarket != null &&
                            !subMarket.isPreOpenRan()) {
                        logger.info(" Need to include the pre open logic here & Need to send the status message " +
                                "to exec brokers");
                        fixorder.setSecurityExchange(exchange.getExchangeCode());
                    }

                    if (fixorder.getTradSesStatus() == FIXConstants.T340_CLOSED && subMarket != null &&
                            !subMarket.isEODRan()) {
                        logger.info("Need to include the eod logic here & Need to send the status message to exec" +
                                " brokers");
                        fixorder.setSecurityExchange(exchange.getExchangeCode());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("problem in processing the market status response - {}", e);
        }
    }
}
