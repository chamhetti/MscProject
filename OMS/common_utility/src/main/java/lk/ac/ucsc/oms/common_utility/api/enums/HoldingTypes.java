package lk.ac.ucsc.oms.common_utility.api.enums;



/**
 * @author hetti
 */
public enum HoldingTypes  {

    LONG(1), SHORT(2);
    private int code;

    HoldingTypes(int code){
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static HoldingTypes getEnum(int code){
        switch (code){
            case 1:
                return LONG;
            case 2:
                return SHORT;
            default:
                return LONG;
        }
    }

}
