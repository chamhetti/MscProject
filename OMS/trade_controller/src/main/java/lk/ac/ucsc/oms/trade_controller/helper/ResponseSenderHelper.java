package lk.ac.ucsc.oms.trade_controller.helper;

import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ResponseSenderHelper {
    private static Logger logger = LogManager.getLogger(ResponseSenderHelper.class);
    private static ResponseSenderHelper instance;

    private ResponseSenderHelper() {
    }

    public static synchronized ResponseSenderHelper getInstance() {
        if (instance == null) {
            instance = new ResponseSenderHelper();
        }
        return instance;
    }

    /**
     * Send responses to the corresponding parties
     *
     * @param order is the order bean
     */
    public void sendResponsesToCorrespondingParties(Order order) {

    }



}
