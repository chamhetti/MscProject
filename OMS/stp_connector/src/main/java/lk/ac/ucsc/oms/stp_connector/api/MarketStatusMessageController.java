package lk.ac.ucsc.oms.stp_connector.api;

import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;


public interface MarketStatusMessageController {

    /**
     * process the market status response
     *
     *
     * @param fixorder contains market status
     * @param sid      session Id of FIX connection
     * @return market status
     */
    void processTradingSessionStatus(FixOrderInterface fixorder, String sid);
}
