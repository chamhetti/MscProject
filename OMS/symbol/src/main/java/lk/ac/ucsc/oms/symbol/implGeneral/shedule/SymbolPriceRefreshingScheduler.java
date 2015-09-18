package lk.ac.ucsc.oms.symbol.implGeneral.shedule;


import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import lk.ac.ucsc.oms.symbol.implGeneral.facade.SymbolPriceManagerFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class SymbolPriceRefreshingScheduler extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(SymbolPriceRefreshingScheduler.class);
    private SymbolPriceManagerFacade symbolPriceManagerFacade;
    private static boolean stillRunning = false;

    /**
     * inject the symbol price manager
     *
     * @param symbolPriceManagerFacade
     */
    public void setSymbolPriceManagerFacade(SymbolPriceManagerFacade symbolPriceManagerFacade) {
        this.symbolPriceManagerFacade = symbolPriceManagerFacade;
    }

    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("symbol price refreshing scheduler starts task " + new Date());
                symbolPriceManagerFacade.refreshCache();
                logger.debug("symbol price refreshing scheduler ends task " + new Date());
            } catch (SymbolPriceException e) {
                throw new SchedulingException("error in persist cache ", e);
            } finally {
                stillRunning = false;
            }
        } else {
            logger.debug("Symbol price data scheduler already running...");
        }
    }
}
