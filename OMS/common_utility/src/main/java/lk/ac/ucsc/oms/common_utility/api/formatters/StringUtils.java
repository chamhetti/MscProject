package lk.ac.ucsc.oms.common_utility.api.formatters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class StringUtils {
    private static Logger logger = LogManager.getLogger(StringUtils.class);

    private StringUtils() {

    }

    public static String getFromListToStringBySeparatorWithQuotes(List<String> stringList, String separator) {
        StringBuilder listStr = new StringBuilder();
        if ((stringList != null) && !stringList.isEmpty()) {
            for (String institution : stringList) {
                listStr.append('\'').append(institution).append('\'').append(separator);
            }
        }
        return listStr.toString().isEmpty() ? listStr.toString() : listStr.toString().substring(0, listStr.toString().length() - 1);
    }

    public static String getUniqueIdString(String jSonMessage) {
        String jsonReqUniqueId = "UNQ_REQ_ID";
        int jsonReqUniqueIdCharacterCount = 13;//length of  UNQ_REQ_ID":"
        String uniqueId = "";
        if (jSonMessage.contains(jsonReqUniqueId)) {
            String jSonMessage2 = jSonMessage.substring(jSonMessage.indexOf(jsonReqUniqueId) + jsonReqUniqueIdCharacterCount);
            uniqueId = jSonMessage2.substring(0, jSonMessage2.indexOf("\""));
        }
        return uniqueId;
    }


    public static String getUniqueID() {
        String uniqueID = null;
        try {
            String key = null;
            String value = null;
            if (value != null) {
                String firstSubString = value.substring(0, value.indexOf("{"));
                int length = firstSubString.length() + 1;
                uniqueID = value.substring(length, value.indexOf("}"));
            }
        } catch (Exception e) {
            logger.error("Error in getting Unique ID from the currently executing thread - {}", e);
        }
        return uniqueID;
    }
}
