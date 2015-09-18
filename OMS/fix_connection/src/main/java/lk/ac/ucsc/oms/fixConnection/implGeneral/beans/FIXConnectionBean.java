package lk.ac.ucsc.oms.fixConnection.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.fixConnection.api.ConnectionStatus;
import lk.ac.ucsc.oms.fixConnection.api.beans.ExchangeConnection;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


@Indexed
public class FIXConnectionBean extends CacheObject implements FIXConnection {
    @Id
    @Field
    private String connectionID;
    @Field
    private ConnectionStatus connectionStatus;
    @Field
    private String description;

    private Map<Integer, String> fixTags;

    private Map<Integer, Integer> replaceFixTags;
    @Field
    private RecordStatus status;
    @Field
    private String exchangeCode;

    private Map<String, String> cancelRequestReplaceFixTags;

    private Map<String, String> amendRequestReplaceFixTags;

    private Map<String, String> exchangeReplaceFixTags = new HashMap<>();

    @IndexedEmbedded
    private Map<String, ExchangeConnection> exchangeConnections = new HashMap<>();

    @Field
    private String sessionQualifier;
    @Field
    private int moveToFo;

    @Field
    private int statusRequestAllow;

    public FIXConnectionBean(String connectionID) {
        this.connectionID = connectionID;
        this.setPrimaryKeyObject(connectionID);
    }


    public FIXConnectionBean() {

    }

    @Override
    public String getConnectionID() {
        return connectionID;
    }

    @Override
    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    @Override
    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    @Override
    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public Map<Integer, String> getFixTags() {
        return fixTags;
    }

    @Override
    public void setFixTags(Map<Integer, String> fixTags) {
        this.fixTags = fixTags;
    }


    @Override
    public RecordStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    @Override
    public String getExchangeCode() {
        return exchangeCode;
    }

    @Override
    public Map<Integer, Integer> getReplaceFixTags() {
        return replaceFixTags;
    }

    @Override
    public void setReplaceFixTags(Map<Integer, Integer> replaceFixTags) {
        this.replaceFixTags = replaceFixTags;
    }

    @Override
    public Map<Integer, Integer> getCancelRequestReplaceTags(String exchangeCode) {
        String cancelRequestReplaceData = cancelRequestReplaceFixTags.get(exchangeCode);

        Map<Integer, Integer> map = new HashMap<>();
        StringTokenizer st = null;
        String dataSet = null;
        int token1 = -1;
        int token2 = -1;
        if (cancelRequestReplaceData != null) {
            for (String s : cancelRequestReplaceData.split(",")) {
                dataSet = s;
                st = new StringTokenizer(dataSet, ":");
                while (st.hasMoreElements()) {
                    try {
                        token1 = Integer.parseInt(st.nextToken());
                        token2 = Integer.parseInt(st.nextToken());
                        map.put(token1, token2);
                    } catch (Exception e) {
                        map.put(token1, null);
                    }
                }
            }
        }
        return map;
    }


    @Override
    public void setCancelRequestReplaceTags(Map<Integer, Integer> cancelRequestReplaceTags, String exchangeCode) {
        Collection sCollection = null;
        sCollection = cancelRequestReplaceTags.keySet();
        int key;
        String value;
        StringBuilder sb = new StringBuilder();
        for (Object aSCollection : sCollection) {
            key = (Integer) aSCollection;
            try {
                value = String.valueOf(cancelRequestReplaceTags.get(key));
            } catch (Exception e) {
                value = null;
            }
            sb.append(key);
            sb.append(':');
            sb.append(value);
            sb.append(',');
        }
        cancelRequestReplaceFixTags.put(exchangeCode, sb.toString());
    }

    public Map<String, String> getCancelRequestReplaceFixTags() {
        return cancelRequestReplaceFixTags;
    }

    public void setCancelRequestReplaceFixTags(Map<String, String> cancelRequestReplaceData) {
        this.cancelRequestReplaceFixTags = cancelRequestReplaceData;
    }


    @Override
    public Map<Integer, Integer> getAmendRequestReplaceTags(String exchangeCode) {
        String amendReplaceTags = amendRequestReplaceFixTags.get(exchangeCode);

        Map<Integer, Integer> map = new HashMap<>();
        StringTokenizer st = null;
        String dataSet = null;
        int token1 = -1;
        int token2 = -1;
        if (amendReplaceTags != null) {
            for (String s : amendReplaceTags.split(",")) {
                dataSet = s;
                st = new StringTokenizer(dataSet, ":");
                while (st.hasMoreElements()) {
                    try {
                        token1 = Integer.parseInt(st.nextToken());
                        token2 = Integer.parseInt(st.nextToken());
                        map.put(token1, token2);
                    } catch (Exception e) {
                        map.put(token1, null);
                    }
                }
            }
        }
        return map;
    }

    @Override
    public void setAmendRequestReplaceTags(Map<Integer, Integer> amendRequestReplaceTags, String exchangeCode) {
        Collection sCollection = null;
        sCollection = amendRequestReplaceTags.keySet();
        int key;
        String value;
        StringBuilder sb = new StringBuilder();
        for (Object aSCollection : sCollection) {
            key = (Integer) aSCollection;
            try {
                value = String.valueOf(amendRequestReplaceTags.get(key));
            } catch (Exception e) {
                value = null;
            }
            sb.append(key);
            sb.append(':');
            sb.append(value);
            sb.append(',');
        }
        amendRequestReplaceFixTags.put(exchangeCode, sb.toString());
    }

    public Map<String, String> getAmendRequestReplaceFixTags() {
        return amendRequestReplaceFixTags;
    }


    public void setAmendRequestReplaceFixTags(Map<String, String> amendRequestReplaceData) {
        this.amendRequestReplaceFixTags = amendRequestReplaceData;
    }


    @Override
    public Map<Integer, Integer> getExchangeReplaceTags(String exchangeCode) {
        String exchangeReplaceTags = exchangeReplaceFixTags.get(exchangeCode);

        Map<Integer, Integer> map = new HashMap<>();
        StringTokenizer st = null;
        String dataSet = null;
        int token1 = -1;
        int token2 = -1;
        if (exchangeReplaceTags != null) {
            for (String s : exchangeReplaceTags.split(",")) {
                dataSet = s;
                st = new StringTokenizer(dataSet, ":");
                while (st.hasMoreElements()) {
                    try {
                        token1 = Integer.parseInt(st.nextToken());
                        token2 = Integer.parseInt(st.nextToken());
                        map.put(token1, token2);
                    } catch (Exception e) {
                        map.put(token1, null);
                    }
                }
            }
        }
        return map;
    }

    @Override
    public void setExchangeReplaceTags(Map<Integer, Integer> exchangeReplaceTags, String exchangeCode) {
        Collection sCollection = null;
        sCollection = exchangeReplaceTags.keySet();
        int key;
        String value;
        StringBuilder sb = new StringBuilder();
        for (Object aSCollection : sCollection) {
            key = (Integer) aSCollection;
            try {
                value = String.valueOf(exchangeReplaceTags.get(key));
            } catch (Exception e) {
                value = null;
            }
            sb.append(key);
            sb.append(':');
            sb.append(value);
            sb.append(',');
        }
        exchangeReplaceFixTags.put(exchangeCode, sb.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ExchangeConnection> getExchangeConnections() {
        return exchangeConnections;
    }

    @Override
    public void setExchangeConnections(Map<String, ExchangeConnection> exchangeConnections) {
        this.exchangeConnections = exchangeConnections;
    }


    @Override
    public String getSessionQualifier() {
        return sessionQualifier;
    }

    @Override
    public void setSessionQualifier(String sessionQualifier) {
        this.sessionQualifier = sessionQualifier;
    }

    public Map<String, String> getExchangeReplaceFixTags() {
        return exchangeReplaceFixTags;
    }

    public void setExchangeReplaceFixTags(Map<String, String> exchangeReplaceFixTags) {
        this.exchangeReplaceFixTags = exchangeReplaceFixTags;
    }


    @Override
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FIXConnectionBean that = (FIXConnectionBean) o;
        if (!connectionID.equals(that.connectionID)) {
            return false;
        }
        if (connectionStatus != that.connectionStatus) {
            return false;
        }
        if (description != null) {
            if (!description.equals(that.description)) {
                return false;
            }
        } else {
            if (that.description != null) {
                return false;
            }
        }
        if (!checkEql(fixTags, that.fixTags)) {
            return false;
        }
        return status == that.status;
    }

    private boolean checkEql(Map<Integer, String> thisMap, Map<Integer, String> thatMap) {
        if (thisMap != null) {
            if (thatMap != null) {
                for (int i : thisMap.keySet()) {
                    if (!thisMap.get(i).equals(thatMap.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return thatMap == null;
    }

    @Override
    public int getStatusRequestAllow() {
        return statusRequestAllow;
    }

    @Override
    public void setStatusRequestAllow(int statusRequestAllow) {
        this.statusRequestAllow = statusRequestAllow;
    }

    @Override
    public int hashCode() {
        return connectionID.hashCode();
    }

    @Override
    public String toString() {
        return "FIXConnectionBean{" +
                "connectionID='" + connectionID + '\'' +
                ", connectionStatus=" + connectionStatus +
                ", description='" + description + '\'' +
                ", fixTags=" + fixTags +
                ", replaceFixTags=" + replaceFixTags +
                ", status=" + status +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", cancelRequestReplaceFixTags=" + cancelRequestReplaceFixTags +
                ", amendRequestReplaceFixTags=" + amendRequestReplaceFixTags +
                ", exchangeReplaceFixTags=" + exchangeReplaceFixTags +
                ", exchangeConnections=" + exchangeConnections +
                ", sessionQualifier='" + sessionQualifier + '\'' +
                ", moveToFo=" + moveToFo +
                ", statusRequestAllow=" + statusRequestAllow +
                '}';
    }
}
