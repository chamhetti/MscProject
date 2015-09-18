package lk.ac.ucsc.oms.stp_connector.api;


public interface FixConnectionStatusController {

    void processConnectedSession(String sessionId);


    void processDisconnectSession(String sessionId);

}
