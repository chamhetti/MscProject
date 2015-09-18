package lk.ac.ucsc.oms.fixConnection.api.beans;

import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.fixConnection.api.ConnectionStatus;

import java.util.Map;


public interface FIXConnection {

    String getConnectionID();

    void setConnectionID(String connectionID);

   ConnectionStatus getConnectionStatus();

    void setConnectionStatus(ConnectionStatus connectionStatus);

    String getDescription();

    void setDescription(String description);

    RecordStatus getStatus();

    void setStatus(RecordStatus status);

    Map<Integer, String> getFixTags();

    void setFixTags(Map<Integer, String> fixTags);

    void setExchangeCode(String exchangeCode);

    String getExchangeCode();

    Map<Integer, Integer> getReplaceFixTags();


    void setReplaceFixTags(Map<Integer, Integer> replaceFixTags);

    Map<Integer, Integer> getCancelRequestReplaceTags(String exchangeCode);

    void setCancelRequestReplaceTags(Map<Integer, Integer> cancelRequestReplaceTags, String exchangeCode);

    Map<Integer, Integer> getAmendRequestReplaceTags(String exchangeCode);

    void setAmendRequestReplaceTags(Map<Integer, Integer> amendRequestReplaceTags, String exchangeCode);

    Map<Integer, Integer> getExchangeReplaceTags(String exchangeCode);


    void setExchangeReplaceTags(Map<Integer, Integer> exchangeReplaceFixTags, String exchangeCode);

    Map<String, ExchangeConnection> getExchangeConnections();

    void setExchangeConnections(Map<String, ExchangeConnection> exchangeConnections);

    String getSessionQualifier();

    void setSessionQualifier(String sessionQualifier);


    int getStatusRequestAllow();

    void setStatusRequestAllow(int statusRequestAllow);
}
