package lk.ac.ucsc.oms.customer.api.beans;

/**
 * Created by dushani on 1/27/15.
 */
public enum PersonalTitle {

    MR(1), MRS(2), MISS(3), DR(4);
    private int code;

    /**
     * constructor of the Enum
     *
     * @param code of Gender
     */
    PersonalTitle(int code) {
        this.code = code;
    }

    /**
     * get code of the Enum
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * get Enum for the code
     *
     * @param code of the enum
     * @return PersonalTitle
     */
    public static PersonalTitle getEnum(int code) {
        switch (code) {
            case 1:
                return MR;
            case 2:
                return MRS;
            case 3:
                return MISS;
            case 4:
                return DR;
            default:
                return null;
        }
    }
}