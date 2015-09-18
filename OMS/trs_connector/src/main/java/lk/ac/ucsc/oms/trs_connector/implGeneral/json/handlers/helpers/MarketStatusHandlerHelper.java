package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers;

import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.PreOpenTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.PreOpenTRSRequestBean;

public class MarketStatusHandlerHelper {
    private TrsConnectorFactory trsConnectorFactory;


    /*
    *usage is only for unit test
    */
    public void setTrsConnectorFactory(TrsConnectorFactory trsConnectorFactory) {
        this.trsConnectorFactory = trsConnectorFactory;
    }
}
