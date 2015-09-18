package lk.ac.ucsc.oms.exchanges.api.beans;

import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderTIF;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

import java.util.List;


public interface OrderRoutingInfo {


    int getSubMarketId();

    void setSubMarketId(int subMarketId);

    int getBuyTplus();

    void setBuyTplus(int buyTplus);

    int getSellTplus();

    void setSellTplus(int sellTplus);

    double getMinOrderValue();

    void setMinOrderValue(double minOrderValue);

    int getLotSize();

    void setLotSize(int lotsize);

    int getQuantityUnitSize();

    void setQuantityUnitSize(int quantityUnitSize);

    double getPriceMultifactor();

    void setPriceMultifactor(double priceMultifactor);

    CommCalBasis getCommissionCalBasis();

    void setCommissionCalBasis(CommCalBasis commissionCalType);

    CommCalUnit getCommCalUnit();

    void setCommCalUnit(CommCalUnit commissionCalUnit);

    int getMinDiscloseQty();

    void setMinDiscloseQty(int minDiscloseQty);

    String getPriceDisplayFormat();

    void setPriceDisplayFormat(String priceDisplayFormat);


    boolean isMreOrdAllow();


    void setMreOrdAllow(MREOrderAllow mreOrdAllowed);

    int getPriceMinMaxValidateAllow();

    void setPriceMinMaxValidateAllow(int priceMinMaxValidateAllow);

    List<OrderTIF> getAllowTif();

    void setAllowTif(List<Integer> allowTifs);

    void setAllowOrdType(List<String> allowOrdTypes);

    List<OrderSide> getAllowOrdSide();

    void setAllowOrdSide(List<String> allowOrdSides);

    List<OrderType> getAllowType();

    double getMaxOrderValue();

    void setMaxOrderValue(double maxOrderValue);

    String getDayTradingLiquidateTime();

    void setDayTradingLiquidateTime(String dayTradingLiquidateTime);

    int getSquareOffOrderType();

    void setSquareOffOrderType(int squareOffOrderType);

    int getLimitOrderPriceType();

    void setLimitOrderPriceType(int limitOrderPriceType);

    /**
     * Enum for MREOrderAllow status
     * NO -0
     * YES -1
     */
    enum MREOrderAllow {
        NO(0), YES(1);
        private int code;

        MREOrderAllow(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static MREOrderAllow getEnum(int code) {
            switch (code) {
                case 0:
                    return NO;
                case 1:
                    return YES;
                default:
                    return null;
            }
        }
    }

    /**
     * Enum for commission calculation type
     * ORDER_VALUE - 1
     * ORDER_QTY - 2
     * ORDER_PRICE - 3
     */
    enum CommCalBasis {
        ORDER_VALUE(1), ORDER_QTY(2), ORDER_PRICE(3);
        private int code;

        CommCalBasis(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static CommCalBasis getEnum(int code) {
            int index = code - 1;
            CommCalBasis[] values = CommCalBasis.values();
            if (index < 0 || index >= values.length) {
                return null;
            } else {
                return values[index];
            }
        }
    }

    /**
     * Enum for commission calculation Unit
     * PER_ORDER  - 1
     * PER_DAY - 2
     * PER_WEEK - 3
     * PER_MONTH - 4
     */
    enum CommCalUnit {
        PER_ORDER(1), PER_DAY(2), PER_WEEK(3), PER_MONTH(4);
        private int code;

        CommCalUnit(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static CommCalUnit getEnum(int code) {
            int index = code - 1;
            CommCalUnit[] values = CommCalUnit.values();
            if (index < 0 || index >= values.length) {
                return null;
            } else {
                return values[index];
            }
        }
    }
}
