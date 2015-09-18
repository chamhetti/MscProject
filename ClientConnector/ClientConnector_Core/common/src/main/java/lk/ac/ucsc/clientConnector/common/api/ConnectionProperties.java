package lk.ac.ucsc.clientConnector.common.api;

/**
 * Created by amilasilva on 6/18/14.
 */
public interface ConnectionProperties {
    String getName();

    void setName(String name);

    int getPort();

    void setPort(int port);

    String getIP();

    void setIP(String ip);

    ConnectorType getQueueType();

    void setQueueType(ConnectorType messageType);

    int getRetryCount();

    void setRetryCount(int retryCount);

    String getProtocol();

    void setProtocol(String protocol);
}
