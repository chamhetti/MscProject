package lk.ac.ucsc.oms.stp_connector.api;

import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPConnectException;


public interface ExecutionReportProcessor {

    void processOrderReject(FixOrderInterface fixorder, String sessionId);


    void processExecutionReport(FixOrderInterface fixorder, String sessionId);

    Order processManualExecuteOrder(Order order);

}
