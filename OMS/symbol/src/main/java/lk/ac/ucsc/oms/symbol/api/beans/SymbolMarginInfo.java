package lk.ac.ucsc.oms.symbol.api.beans;


import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

/**
 * This is the data transfer interface used to give symbol margin related info.
 *
 * User: Hetti
 * Date: 11/22/12
 * Time: 10:54 AM
 */
public interface SymbolMarginInfo {
    /**
     * get symbol margin info id
     * @return  long
     */
    long getId();
    /**
     * set symbol margin info id
     * @param  id
     */
    void setId(long id);
    /**
     * get symbol id related to this margin info
     * @return  long
     */
    long getSymbolId();

    /**
     * set the symbol id related to margin info
     * @param symbolId   long
     */
    void setSymbolId(long symbolId);

    /**
     * set margin enable or not 1 -enable 0 -not enable
     * @param marginTradingEnabled  MarginEnable
     */
    void setMarginTradingEnable(PropertyEnable marginTradingEnabled);

    /**
     * get the inst id related to this margin info
     * @return  int
     */
    int getInstID();

    /**
     * set the inst id related to this margin info
     * @param instID  int
     */
    void setInstID(int instID);

    /**
     * get marginable percentage. it is given for 100. that 50% etc
     * @return double
     */
    double getMarginablePercentage();

    /**
     *  set the marginable percentage. if it is 50% set 50
     * @param marginablePercentage  double
     */
    void setMarginablePercentage(double marginablePercentage);

    /**
     * get the max margin amount for this symbol
     * @return  double
     */
    double getMaxMarginAmount();

    /**
     * set the max margin amount
     * @param maxMarginAmount double
     */
    void setMaxMarginAmount(double maxMarginAmount);

    /**
     * get max day margin amount for this symbol
     * @return double
     */
    double getMaxDayMarginAmount();

    /**
     * set the max day margin amount
     * @param maxDayMarginAmount double value of margin amount
     */
    void setMaxDayMarginAmount(double maxDayMarginAmount);

    /**
     * get margin by percentage return  0<= value <=100
     * @return double amount of margin amount
     */
    double getMarginBuyPercentage();

    /**
     * set marginable percentage
     * @param marginBuyPercentage double
     */
    void setMarginBuyPercentage(double marginBuyPercentage);

    /**
     * get day margin by percentage return  0<= value <=100
     * @return double
     */
    double getMarginDayBuyPercentage();

    /**
     * set day margin by percentage return  0<= value <=100
     * @param marginDayBuyPercentage double
     */
    void setMarginDayBuyPercentage(double marginDayBuyPercentage);

    /**
     * get margin callable level 0<= value <=100
     * @return  double
     */
    double getMarginCallLevel();

    /**
     * set margin callable level 0<= value <=100
     * @param marginCallLevel  double
     */
    void setMarginCallLevel(double marginCallLevel);

    /**
     * get day margin callable level 0<= value <=100
     * @return  double
     */
    double getDayMarginCallLevel();

    /**
     * set day margin callable level 0<= value <=100
     * @param marginDayCallLevel double
     */
    void setDayMarginCallLevel(double marginDayCallLevel);

    /**
     * get day margin call liquidate level
     * @return double
     */
    double getDayMarginCallLiquidateLevel();

    /**
     * set day margin call liquidate level
     * @param dayMarginCallLiquidateLevel double
     */
    void setDayMarginCallLiquidateLevel(double dayMarginCallLiquidateLevel);

    /**
     * get day margin call remind level
     * @return double
     */
    double getDayMarginCallRemindLevel();

    /**
     * set day margin call remind level
     * @param dayMarginCallRemindLevel  double
     */
    void setDayMarginCallRemindLevel(double dayMarginCallRemindLevel);

    /**
     * get day margin percentage 0<= value <=100
     * @return double
     */
    double getDayMarginPercentage();

    /**
     * set day margin percentabe 0<= value <=100
     * @param marginablePercentageDay double
     */
    void setDayMarginPercentage(double marginablePercentageDay);

    /**
     * Get the Institution Code
     *
     * @return
     */
    String getInstitutionCode();


    /**
     * Set the institution Code
     *
     * @param institutionCode
     */
    void setInstitutionCode(String institutionCode);

    /**
     * get symbol is marginable or not
     * @return boolean true or false
     */
    boolean isMarginEnable();

    double getConcentrationLimit();

    void setConcentrationLimit(double concentrationLimit);

    double getDayConcentrationLimit();

    void setDayConcentrationLimit(double dayConcentrationLimit);

    int getSymbolGroup();

    void setSymbolGroup(int symbolGroup);

    /**
     * Sharia Enable status
     * NO -0
     * YES -1
     *
     */
    enum MarginEnable {
        NO(0), YES(1);
        private int code;

        MarginEnable(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
