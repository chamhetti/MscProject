package lk.ac.ucsc.oms.common_utility.api.formatters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Hetti
 */
public final class DateFormatterUtil {
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS_SEPERATORS = "yyyyMMdd-HH:mm:ss";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

     private DateFormatterUtil() {

    }

    public static String formatDateToString(Date date, String formatString) {
        try {
            DateFormat formatter = new SimpleDateFormat(formatString);
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static Date formatStringToDate(String date, String formatString) {
        try {
            DateFormat formatter = new SimpleDateFormat(formatString);
            return formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static String formatStringDateToOtherFormat(String date, String formatString) {
        try {
            DateFormat formatter = new SimpleDateFormat(formatString);
            Date date2 = formatter.parse(date);
            return formatter.format(date2);
        } catch (Exception e) {
            return null;
        }
    }
}
