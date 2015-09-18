package lk.ac.ucsc.clientConnector.common.api;

/**
 * Created by amilasilva on 6/16/14.
 */
public enum ConnectorType {
    JMS("jms"), MQ("mq"), HORNETQ("hornetq"), SOCKET("socket");

    private ConnectorType(String code) {
    }

}
