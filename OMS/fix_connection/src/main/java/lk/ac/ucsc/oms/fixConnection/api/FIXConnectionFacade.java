package lk.ac.ucsc.oms.fixConnection.api;

import lk.ac.ucsc.oms.fixConnection.api.beans.ExchangeConnection;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean;

import java.util.List;
import java.util.Map;


public interface FIXConnectionFacade {

    void initialize() throws FIXConnectionException;

    FIXConnection getEmptyFIXConnection() throws FIXConnectionException;

    void create(FIXConnection connection) throws FIXConnectionException;

    void update(FIXConnection connection) throws FIXConnectionException;

    void markFIXConnectionAsDeleted(FIXConnection connection) throws FIXConnectionException;

    List<FIXConnection> getAll() throws FIXConnectionException;

    List<FIXConnection> getAll(List<String> exchangeList) throws FIXConnectionException;

    Map<Integer, String> getFIXConnectionData(String connectionID, String securityExchange) throws FIXConnectionException;

    String getModuleCode() throws FIXConnectionException;

    FIXConnection getFixConnection(String connectionId) throws FIXConnectionException;

    List<FIXConnectionBean> getFixConnectionBySessionQualifier(String sessionQualifier) throws FIXConnectionException;

    List<Integer> getOrderedFixTagList(String connectionID, String exchangeCode) throws FIXConnectionException;

    ExchangeConnection getEmptyExchangeConnection() throws FIXConnectionException;
}
