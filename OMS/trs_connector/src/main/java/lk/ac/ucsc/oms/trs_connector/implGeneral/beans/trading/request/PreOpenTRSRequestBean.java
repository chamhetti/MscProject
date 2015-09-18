package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.PreOpenTRSRequest;

public class PreOpenTRSRequestBean implements PreOpenTRSRequest {
    private String exchange;
    private String marketCode;
    private String institutionID;
    private boolean isForcefulPreOpen;
    private String execBrokerId;

    /**
     * Get the Security Exchange
     *
     * @return
     */
    @Override
    public String getExchange() {
        return exchange;
    }

    /**
     * @param exchange is the Security Exchange
     */
    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * Get the market code
     *
     * @return
     */
    @Override
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * @param marketCode is the marketr code
     */
    @Override
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * Get the institution id
     *
     * @return
     */
    @Override
    public String getInstitutionID() {
        return institutionID;
    }

    /**
     * @param institutionID is the institution id
     */
    @Override
    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    /**
     * Is forceful pre open
     *
     * @return
     */
    @Override
    public boolean isForcefulPreOpen() {
        return isForcefulPreOpen;
    }

    /**
     * @param forcefulPreOpen
     */
    @Override
    public void setForcefulPreOpen(boolean forcefulPreOpen) {
        this.isForcefulPreOpen = forcefulPreOpen;
    }

    /**
     * {@inheritDoc}
     */
    public String getExecBrokerId() {
        return execBrokerId;
    }

    /**
     * {@inheritDoc}
     */
    public void setExecBrokerId(String execBrokerId) {
        this.execBrokerId = execBrokerId;
    }
}
