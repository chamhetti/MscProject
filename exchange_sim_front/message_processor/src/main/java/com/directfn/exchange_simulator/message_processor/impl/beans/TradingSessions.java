package com.directfn.exchange_simulator.message_processor.impl.beans;


public class TradingSessions {
    private String tradingSession;
    private String remoteFirmId;
    private String localFirmId;
    private String connectionStatus;

    public String getTradingSession() {
        return tradingSession;
    }

    public void setTradingSession(String tradingSession) {
        this.tradingSession = tradingSession;
    }

    public String getRemoteFirmId() {
        return remoteFirmId;
    }

    public void setRemoteFirmId(String remoteFirmId) {
        this.remoteFirmId = remoteFirmId;
    }

    public String getLocalFirmId() {
        return localFirmId;
    }

    public void setLocalFirmId(String localFirmId) {
        this.localFirmId = localFirmId;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
