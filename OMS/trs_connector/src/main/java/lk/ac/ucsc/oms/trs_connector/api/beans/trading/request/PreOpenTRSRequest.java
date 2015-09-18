package lk.ac.ucsc.oms.trs_connector.api.beans.trading.request;

/**
 * Interface for the Market Pre Open Request
 * <p/>
 * Author: Chathura
 * Date: 5/26/13
 */
public interface PreOpenTRSRequest {
    /**
     * Get the Security Exchange
     *
     * @return
     */
    String getExchange();

    /**
     * @param exchange is the Security Exchange
     */
    void setExchange(String exchange);

    /**
     * Get the market code
     *
     * @return
     */
    String getMarketCode();

    /**
     * @param marketCode is the marketr code
     */
    void setMarketCode(String marketCode);

    /**
     * Get the institution id
     *
     * @return
     */
    String getInstitutionID();

    /**
     * @param institutionID is the institution id
     */
    void setInstitutionID(String institutionID);

    /**
     * Is forceful pre open
     *
     * @return
     */
    boolean isForcefulPreOpen();

    /**
     * @param forcefulPreOpen
     */
    void setForcefulPreOpen(boolean forcefulPreOpen);

    /**
     * set exec broker id
     *
     * @return
     */
    String getExecBrokerId();

    /**
     * set exec broker id
     *
     * @param execBrokerId
     */
    void setExecBrokerId(String execBrokerId);
}
