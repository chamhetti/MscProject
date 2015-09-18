package lk.ac.ucsc.oms.fixConnection.implGeneral.facade;

import lk.ac.ucsc.oms.fixConnection.api.ConnectionStatus;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFacade;
import lk.ac.ucsc.oms.fixConnection.api.beans.ExchangeConnection;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import lk.ac.ucsc.oms.fixConnection.implGeneral.beans.ExchangeConnectionBean;
import lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean;
import lk.ac.ucsc.oms.fixConnection.implGeneral.facade.cache.FIXConnectionCacheFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FIXConnectionFacadeImpl implements FIXConnectionFacade {
    private String moduleCode;
    private FIXConnectionCacheFacade cacheFacade;
    private static Logger logger = LogManager.getLogger(FIXConnectionFacadeImpl.class);

    @Override
    public void create(FIXConnection connection) throws FIXConnectionException {
        validateConnection(connection);
        cacheFacade.add((FIXConnectionBean) connection);
    }


    @Override
    public void update(FIXConnection connection) throws FIXConnectionException {
        validateConnection(connection);
        cacheFacade.update((FIXConnectionBean) connection);
    }

    @Override
    public void markFIXConnectionAsDeleted(FIXConnection connection) throws FIXConnectionException {
        validateConnectionID(connection);
        cacheFacade.delete((FIXConnectionBean) connection);
    }

    @Override
    public List<FIXConnection> getAll() throws FIXConnectionException {
        return cacheFacade.getAll();
    }


    public List<FIXConnection> getAll(List<String> exchangeList) throws FIXConnectionException {
        return cacheFacade.getAll(exchangeList);
    }


    @Override
    public Map<Integer, String> getFIXConnectionData(String connectionID, String securityExchange) throws FIXConnectionException {
        validateConnectionID(new FIXConnectionBean(connectionID));
        FIXConnection fixConnection = cacheFacade.getFIXConnection(connectionID);
        Map<Integer, String> fixConnectionSpecificTags = fixConnection.getFixTags();
        //single fix connection can have multiple exchange connections. There may be situations where specific exchanges
        //have their own custom fix tags. Sometimes exchange might have different values for the fix connection values
        //so the fix connections values will be always overwrite by the exchange connection values
        if (fixConnection.getExchangeConnections() != null && fixConnection.getExchangeConnections().containsKey(securityExchange)) {
            ExchangeConnection exchangeConnection = fixConnection.getExchangeConnections().get(securityExchange);
            if (exchangeConnection != null) {
                fixConnectionSpecificTags.putAll(exchangeConnection.getExchangeCustomTags());
            }
        }
        return fixConnectionSpecificTags;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getModuleCode() throws FIXConnectionException {
        return this.moduleCode;
    }


    @Override
    public FIXConnection getFixConnection(String connectionId) throws FIXConnectionException {
        validateConnectionID(new FIXConnectionBean(connectionId));
        return cacheFacade.getFIXConnection(connectionId);
    }

    @Override
    public List<FIXConnectionBean> getFixConnectionBySessionQualifier(String sessionQualifier) throws FIXConnectionException {
        return cacheFacade.getFIXConnectionBySessionQualifier(sessionQualifier);
    }

    @Override
    public List<Integer> getOrderedFixTagList(String connectionID, String exchangeCode) throws FIXConnectionException {
        validateConnectionID(new FIXConnectionBean(connectionID));
        ExchangeConnection exchangeConnection = cacheFacade.getFIXConnection(connectionID).getExchangeConnections().get(exchangeCode);
        Map<String, String> fixTagOrder = exchangeConnection.getOrderedFixTags();
        String tagOrder = fixTagOrder.get(exchangeCode);
        List<Integer> fixTagList = new ArrayList<>();
        if (tagOrder != null && !tagOrder.isEmpty()) {
            for (String s : tagOrder.split(",")) {
                fixTagList.add(Integer.parseInt(s));
            }
        }
        return fixTagList;
    }

    @Override
    public ExchangeConnection getEmptyExchangeConnection() throws FIXConnectionException {
        return new ExchangeConnectionBean();
    }

    @Override
    public void initialize() throws FIXConnectionException {
        //loading all the fix connections to the cache
        cacheFacade.initialize();
    }

    @Override
    public FIXConnection getEmptyFIXConnection() throws FIXConnectionException {
        return new FIXConnectionBean();
    }

    private void validateConnection(FIXConnection con) throws FIXConnectionException {
        validateConnectionID(con);
        if (con.getConnectionStatus() == null) {
            logger.error("Invalid FIXConnection.Connection status cannot be null.must be -{}", printStatus());
            throw new FIXConnectionException("Invalid FIXConnection.Connection status cannot be null");
        }
        if (con.getSessionQualifier() == null || ("").equalsIgnoreCase(con.getSessionQualifier())) {
            logger.error("Invalid FIXConnection. Session Qualifier cannot be null or empty");
            throw new FIXConnectionException("Invalid FIXConnection. Session Qualifier cannot be null or empty");
        }
    }

    private void validateConnectionID(FIXConnection connection) throws FIXConnectionException {
        if (connection == null) {
            logger.error("FIXConnection object is null");
            throw new FIXConnectionException("con object is null");
        }
        if (connection.getConnectionID() == null || connection.getConnectionID().length() == 0) {
            logger.error("Invalid FIXConnection.Connection ID is null or empty");
            throw new FIXConnectionException("Invalid FIXConnection.connection ID is null or empty");
        }
    }

    private String printStatus() {
        StringBuilder buf = new StringBuilder();
        String s = "";
        for (ConnectionStatus c : ConnectionStatus.values()) {
            buf.append(' ').append(c.toString());
        }
        s = buf.toString();
        return "[" + s + "]";
    }


    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public void setCacheFacade(FIXConnectionCacheFacade cacheFacade) {
        this.cacheFacade = cacheFacade;
    }
}
