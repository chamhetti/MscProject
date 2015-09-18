package lk.ac.ucsc.clientConnector.front_office_connector.api;

import lk.ac.ucsc.clientConnector.front_office_connector.api.exception.MessageSenderException;

import java.util.Date;


public interface MessageSender {

    void start();


    void stop();


    void sendMessage(String message, int retry) throws MessageSenderException;


    boolean isConnected();


    Date getConnectedTime();

    String getHostName();
}
