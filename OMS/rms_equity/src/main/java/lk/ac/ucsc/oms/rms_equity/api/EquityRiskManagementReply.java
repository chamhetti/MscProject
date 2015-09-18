package lk.ac.ucsc.oms.rms_equity.api;


public interface EquityRiskManagementReply  {
    /**
     * Check the status of the Equity Risk Management Reply
     *
     * @return the status
     */
    boolean isSuccess();

    /**
     * Get the reject code of the Equity Risk Management Reply
     *
     * @return the reject code
     */
    int getRejectCode();

    /**
     * Get the reject reason of the Equity Risk Management Reply
     *
     * @return the reject reason
     */
    String getRejectReason();

    /**
     * Get the module code
     *
     * @return the module code
     */
    int getModuleCode();

    /**
     * Get the error message parameter list from the Equity Risk Management Reply
     *
     * @return the list
     */
    String getErrMsgParameterList();
}
