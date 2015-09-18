package lk.ac.ucsc.oms.common_utility.api.formatters;

/**
 * User: Hetti
 */
public final class DecimalFormatterUtil {
    private static final int ROUND_CONST = 10;

    /**
     * Default Constructor of the DecimalFormatterUtil
     */
    private DecimalFormatterUtil() {

    }


    public static double round(double value, int decimalPoints) {
        int i = 1;
        for (int j = 0; j < decimalPoints; j++) {
            i = i * ROUND_CONST;
        }
        double value1 = Math.round(value * i);
        return value1 / i;
    }
}
