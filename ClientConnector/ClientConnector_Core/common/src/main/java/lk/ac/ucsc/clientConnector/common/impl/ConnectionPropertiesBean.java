package lk.ac.ucsc.clientConnector.common.impl;

import lk.ac.ucsc.clientConnector.common.api.ConnectorType;
import lk.ac.ucsc.clientConnector.common.api.ConnectionProperties;

/**
 * {@inheritDoc}
 * User: Chathura
 * Date: Jan 7, 2013
 */
public class ConnectionPropertiesBean implements ConnectionProperties {
    private String name;
    private int port;
    private String ip;
    private ConnectorType queueType;
    private int retryCount;
    private String protocol;

    /**
     * @return the queue name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name name of the message destination at oms
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return is the queue connection port
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * @param port is the port
     */
    @Override
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the queue connection ip
     */
    @Override
    public String getIP() {
        return ip;
    }

    /**
     * @param ip is the ip of the queue
     */
    @Override
    public void setIP(String ip) {
        this.ip = ip;
    }

    /**
     * @return
     */
    @Override
    public ConnectorType getQueueType() {
        return queueType;
    }

    /**
     * @param messageType
     */
    @Override
    public void setQueueType(ConnectorType messageType) {
        this.queueType = messageType;
    }

    @Override
    public int getRetryCount() {
        return retryCount;
    }

    @Override
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
