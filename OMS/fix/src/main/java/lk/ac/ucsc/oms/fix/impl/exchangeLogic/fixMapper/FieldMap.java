package lk.ac.ucsc.oms.fix.impl.exchangeLogic.fixMapper;

import lk.ac.ucsc.oms.fix.api.exception.FIXOrderException;
import lk.ac.ucsc.oms.fix.impl.util.DataSeparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static lk.ac.ucsc.oms.fix.impl.util.FIXConstants.*;


public class FieldMap {
    private static Logger logger = LogManager.getLogger(FieldMap.class);
    private Map<Integer, String> map;
    private Map<Integer, String> customFields;
    private Map<Integer, Integer> replaceFields;
    private List<Integer> tagSequence;
    private String msg;
    private static final String DATA_SEPARATOR = "=";
    private String header;
    private String trailer;
    private String msgBody;


    public FieldMap(Map<Integer, String> customFields, Map<Integer, Integer> replaceFields, List<Integer> tagSequence, String msg) {
        this.customFields = customFields;
        this.replaceFields = replaceFields;
        this.tagSequence = tagSequence;
        this.msg = msg;
        addToFieldMap();
    }

    private void addToFieldMap() throws NumberFormatException {
        String sToken = null;
        String sTag = null;
        String sValue = null;
        StringTokenizer st = null;
        map = new TreeMap<>();
        DataSeparator dataSeparator = new DataSeparator(DATA_SEPARATOR);
        st = new StringTokenizer(msg, FIELD_SEPERATOR);
        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            sToken = sToken.trim();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            sValue = dataSeparator.getData();
            map.put(Integer.valueOf(sTag), sValue);
        }
    }


    public String getFixString() throws FIXOrderException {
        String fixMessage = null;
        if (customFields != null) {
            setCustomFields(customFields);
        }
        if (replaceFields != null) {
            setTagMapping(replaceFields);
        }
        if (tagSequence != null) {
            fixMessage = getOrder();
        } else {
            fixMessage = msg;
        }
        return fixMessage;
    }


    private void setCustomFields(Map<Integer, String> customMap) throws FIXOrderException {
        int key = -1;
        String value = null;
        Collection sCollection = null;
        sCollection = customMap.keySet();
        for (Object aSCollection : sCollection) {
            try {
                key = (Integer) aSCollection;
                value = customMap.get(key);
                map.put(key, value);
            } catch (Exception e) {
                logger.error("Issue occur in setting custom tags ", e);
                throw new FIXOrderException("Error in setting custom tags", e);
            }
        }
    }

    private void setTagMapping(Map<Integer, Integer> customMap) {
        int originalField;
        int replacingField;
        String origValue = null;
        Collection sCollection = null;
        sCollection = customMap.keySet();
        for (Object aSCollection : sCollection) {
            originalField = -1;
            replacingField = -1;
            try {
                originalField = (Integer) aSCollection;
                if (customMap.containsKey(originalField)) {
                    replacingField = customMap.get(originalField);
                }
                //if the replacing field is '-1' that mean original field has to removed from the fix message
                if (replacingField == -1) {
                    map.remove(originalField);
                } else {
                    if (map.get(originalField) != null) {
                        origValue = map.get(originalField);
                        map.remove(originalField);
                        map.put(replacingField, origValue);
                    }
                }
            } catch (Exception e) {
                //in the case of replacing filed is -1, that is intentionally set
                //if we need to remove a original filed from the fix message without set replace tag we just set the
                //replace tag as -1, due to this an exception can occur, but need to continue tag replacing of the other
                //tags ignoring the exception
                if (replacingField == -1) {
                    map.remove(originalField);
                }
                logger.error("Continue Replacing Tags ");
            }
        }
    }

    private String getOrder() {
        String fixMessage;
        header = createHeader();
        logger.debug("Message Header: " + header);
        trailer = createTrailer();
        logger.debug("Message Trailer: " + trailer);
        msgBody = createBody();
        logger.debug("Message Body: " + msgBody);
        fixMessage = header + msgBody + trailer;
        logger.debug("FIXConstants Message: " + fixMessage);

        map = null;

        return fixMessage;
    }

    private String createHeader() {
        Collection sCollection = null;
        sCollection = map.keySet();
        int key;
        String value;
        StringBuilder sb = new StringBuilder();
        List<Integer> keyList = new ArrayList<Integer>();
        int i = 0;
        for (Object aSCollection : sCollection) {
            key = (Integer) aSCollection;
            value = map.get(key);
            if (isHeaderField(key)) {
                sb.append(key);
                sb.append(TAG_VALUE_SEPERATOR);
                sb.append(value);
                sb.append(FIELD_SEPERATOR);
                keyList.add(key);
            }
        }
        while (i < keyList.size()) {
            map.remove(keyList.get(i));
            i++;
        }

        return sb.toString();
    }


    private String createTrailer() {
        Collection sCollection = null;
        sCollection = map.keySet();
        int key;
        String value;
        StringBuilder sb = new StringBuilder();
        List<Integer> keyList = new ArrayList<Integer>();
        int i = 0;
        for (Object aSCollection : sCollection) {
            key = (Integer) aSCollection;
            value = map.get(key);
            if (isTrailerField(key)) {
                sb.append(key);
                sb.append(TAG_VALUE_SEPERATOR);
                sb.append(value);
                sb.append(FIELD_SEPERATOR);
                keyList.add(key);
            }
        }

        while (i < keyList.size()) {
            map.remove(keyList.get(i));
            i++;
        }

        return sb.toString();
    }

    private String createBody() {
        Collection sCollection = null;
        sCollection = map.keySet();
        String value;
        int key;
        StringBuilder sb = new StringBuilder();
        List<Integer> keyList = new ArrayList<Integer>();
        int i = 0;
        for (Integer aTagOrder : tagSequence) {
            value = map.get(aTagOrder);
            if (value == null) {
                continue;
            }
            sb.append(aTagOrder);
            sb.append(TAG_VALUE_SEPERATOR);
            sb.append(value);
            sb.append(FIELD_SEPERATOR);
            keyList.add(aTagOrder);
        }
        while (i < keyList.size()) {
            map.remove(keyList.get(i));
            i++;
        }
        if (!map.isEmpty()) {
            for (Object aSCollection : sCollection) {
                key = (Integer) aSCollection;
                value = map.get(key);
                sb.append(key);
                sb.append(TAG_VALUE_SEPERATOR);
                sb.append(value);
                sb.append(FIELD_SEPERATOR);
                keyList.add(key);
            }
        }

        return sb.toString();
    }


    private boolean isHeaderField(int field) {
        switch (field) {
            case FIX_VERSION:
            case BODY_LENGTH:
            case MSG_SEQ_NO:
            case MSG_TYPE:
            case POSS_DUP_FLAG:
            case SENDER_COMP_ID:
            case SENDER_SUB_ID:
            case SENDING_TIME:
            case TARGET_COMP_ID:
            case TARGET_SUB_ID:
            case SECURE_DATA_LEN:
            case POSS_RESEND:
            case ON_BEHALFOF_COMPID:
            case ON_BEHALFOF_SUBID:
            case ORIG_SENDING_TIME:
            case SUB_ID:
            case DELIVER_TO_SUBID:
            case SENDER_LOCATION_ID:
            case TARGET_LOCATION_ID:
            case ON_BEHALFOF_LOCATION_ID:
            case DELIVER_TO_LOCATION_ID:
            case XML_DATA_LEN:
            case XML_DATA:
            case MESSAGE_ENCODING:
            case LAST_MSG_SEQNO:
            case ON_BEHAF_OF_SENDING_TIME:
                return true;
            default:
                return false;
        }
    }


    private boolean isTrailerField(int field) {
        switch (field) {
            case CHECK_SUM:
            case SIGNATURE:
            case SIGNATURE_LENGTH:
                return true;
            default:
                return false;
        }
    }


    public String getMsg() {
        return msg;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public String getHeader() {
        return header;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getMsgBody() {
        return msgBody;
    }
}
