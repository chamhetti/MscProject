package lk.ac.ucsc.oms.exchanges.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderTIF;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.exchanges.api.beans.OrderRoutingInfo;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Indexed
public class OrderRoutingInfoBean extends CacheObject implements OrderRoutingInfo {
    @Id
    @Field
    private int subMarketId;
    @Field
    private int buyTplus;
    @Field
    private int sellTplus;
    @Field
    private double minOrderValue;
    @Field
    private double maxOrderValue;
    @Field
    private int lotSize;
    @Field
    private int quantityUnitSize;
    @Field
    private double priceMultifactor;
    @Field
    private int commissionCalType;
    @Field
    private int commissionCalUnit;
    @Field
    private int minDiscloseQty;
    @Field
    private String priceDisplayFormat;
    @Field
    private int mreOrdAllowed;
    @Field
    private int priceMinMaxValidateAllow;
    @Field
    private String allowTifs;
    @Field
    private String allowOrdTypes;
    @Field
    private String allowOrdSides;
    @Field
    private String dayTradingLiquidateTime;

    @Field
    private int squareOffOrderType;
    @Field
    private int limitOrderPriceType;

    @Override
    public int getSubMarketId() {
        return subMarketId;
    }


    public void setSubMarketId(int subMarketId) {
        this.subMarketId = subMarketId;
    }


    @Override
    public int getBuyTplus() {
        return buyTplus;
    }

    @Override
    public void setBuyTplus(int buyTplus) {
        this.buyTplus = buyTplus;
    }

    @Override
    public int getSellTplus() {
        return sellTplus;
    }

    @Override
    public void setSellTplus(int sellTplus) {
        this.sellTplus = sellTplus;
    }

    @Override
    public double getMinOrderValue() {
        return minOrderValue;
    }

    @Override
    public void setMinOrderValue(double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    @Override
    public int getLotSize() {
        return lotSize;
    }

    @Override
    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    @Override
    public int getQuantityUnitSize() {
        return quantityUnitSize;
    }

    @Override
    public void setQuantityUnitSize(int quantityUnitSize) {
        this.quantityUnitSize = quantityUnitSize;
    }


    @Override
    public double getPriceMultifactor() {
        return priceMultifactor;
    }

    @Override
    public void setPriceMultifactor(double priceMultifactor) {
        this.priceMultifactor = priceMultifactor;
    }


    public int getCommissionCalType() {
        return commissionCalType;
    }

    @Override
    public CommCalBasis getCommissionCalBasis() {
        return CommCalBasis.getEnum(commissionCalType);
    }

    public void setCommissionCalType(int commissionCalType) {
        this.commissionCalType = commissionCalType;
    }

    @Override
    public void setCommissionCalBasis(CommCalBasis commissionCalType) {
        this.commissionCalType = commissionCalType.getCode();
    }


    @Override
    public CommCalUnit getCommCalUnit() {
        return CommCalUnit.getEnum(commissionCalUnit);
    }

    @Override
    public void setCommCalUnit(CommCalUnit commissionCalUnit) {
        this.commissionCalUnit = commissionCalUnit.getCode();
    }


    @Override
    public int getMinDiscloseQty() {
        return minDiscloseQty;
    }


    @Override
    public void setMinDiscloseQty(int minDiscloseQty) {
        this.minDiscloseQty = minDiscloseQty;
    }

    @Override
    public String getPriceDisplayFormat() {
        return priceDisplayFormat;
    }


    @Override
    public void setPriceDisplayFormat(String priceDisplayFormat) {
        this.priceDisplayFormat = priceDisplayFormat;
    }



    @Override
    public boolean isMreOrdAllow() {
        return mreOrdAllowed == 1;
    }


    public void setMreOrdAllowed(int mreOrdAllowed) {
        this.mreOrdAllowed = mreOrdAllowed;
    }

    @Override
    public void setMreOrdAllow(MREOrderAllow mreOrdAllowed) {
        this.mreOrdAllowed = mreOrdAllowed.getCode();
    }


    @Override
    public int getPriceMinMaxValidateAllow() {
        return priceMinMaxValidateAllow;
    }

    @Override
    public void setPriceMinMaxValidateAllow(int priceMinMaxValidateAllow) {
        this.priceMinMaxValidateAllow = priceMinMaxValidateAllow;
    }


    public String getAllowTifs() {
        return allowTifs;
    }

    @Override
    public List<OrderTIF> getAllowTif() {
        List<OrderTIF> tifList = new ArrayList<>();
        for (String tif : allowTifs.split(",")) {
            tifList.add(OrderTIF.getEnum(Integer.parseInt(tif)));
        }
        return tifList;
    }

    public void setAllowTifs(String allowTifs) {
        this.allowTifs = allowTifs;
    }

    @Override
    public void setAllowTif(List<Integer> allowTifs) {
        StringBuilder buf = new StringBuilder();
        String tifs = "";
        if (!allowTifs.isEmpty()) {
            for (int tif : allowTifs) {
                buf.append(tif).append(',');
            }
            tifs = buf.toString();
            this.allowTifs = tifs.substring(0, tifs.length() - 1);
        } else {
            this.allowTifs = tifs;
        }
    }


    public String getAllowOrdTypes() {
        return allowOrdTypes;
    }

    @Override
    public List<OrderType> getAllowType() {
        List<OrderType> typeList = new ArrayList<>();
        for (String type : allowOrdTypes.split(",")) {
            typeList.add(OrderType.getEnum(type));
        }
        return typeList;
    }


    public void setAllowOrdTypes(String allowOrdTypes) {
        this.allowOrdTypes = allowOrdTypes;
    }

    @Override
    public void setAllowOrdType(List<String> allowOrdType) {
        StringBuilder buf = new StringBuilder();
        String types = "";
        for (String type : allowOrdType) {
            buf.append(type).append(',');
        }
        types = buf.toString();
        this.allowOrdTypes = types.substring(0, types.length() - 1);
    }

    @Override
    public List<OrderSide> getAllowOrdSide() {
        List<OrderSide> sideList = new ArrayList<>();
        for (String side : allowOrdSides.split(",")) {
            if (OrderSide.getEnum(side) != null) {
                sideList.add(OrderSide.getEnum(side));
            }
        }
        return sideList;
    }

    public String getAllowOrdSides() {
        return allowOrdSides;
    }

    public void setAllowOrdSides(String allowOrdSides) {
        this.allowOrdSides = allowOrdSides;
    }


    @Override
    public void setAllowOrdSide(List<String> allowOrdSides) {
        StringBuilder buf = new StringBuilder();
        String sides = "";
        for (String side : allowOrdSides) {
            sides = sides + side + ",";
            buf.append(side).append(',');
        }
        sides = buf.toString();
        this.allowOrdSides = sides.substring(0, sides.length() - 1);
    }

    @Override
    public double getMaxOrderValue() {
        return maxOrderValue;
    }

    @Override
    public void setMaxOrderValue(double maxOrderValue) {
        this.maxOrderValue = maxOrderValue;
    }

    @Override
    public String getDayTradingLiquidateTime() {
        return dayTradingLiquidateTime;
    }

    @Override
    public void setDayTradingLiquidateTime(String dayTradingLiquidateTime) {
        this.dayTradingLiquidateTime = dayTradingLiquidateTime;
    }

    @Override
    public int getSquareOffOrderType() {
        return squareOffOrderType;
    }

    @Override
    public void setSquareOffOrderType(int squareOffOrderType) {
        this.squareOffOrderType = squareOffOrderType;
    }

    @Override
    public int getLimitOrderPriceType() {
        return limitOrderPriceType;
    }

    @Override
    public void setLimitOrderPriceType(int limitOrderPriceType) {
        this.limitOrderPriceType = limitOrderPriceType;
    }

}
