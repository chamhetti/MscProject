package lk.ac.ucsc.clientConnector.client;

import lk.ac.ucsc.clientConnector.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class UserManager {
    private static Logger logger = LoggerFactory.getLogger(UserManager.class);
    private static UserManager singleton = new UserManager();
    private Map<String, Client> clientMapBySessionId;
    private Map<String, Client> clientMapByUserId;

    private UserManager() {
        clientMapBySessionId = Collections.synchronizedMap(new HashMap<String, Client>());
        clientMapByUserId = Collections.synchronizedMap(new HashMap<String, Client>());
    }


    public static UserManager getInstance() {
        return singleton;
    }


    public void addClientToClientsTable(AbstractClient client) {
        clientMapBySessionId.put(client.getSessionID(), client);
        logger.debug("Adding new client:" + client.getClientAddress() + " SessionID " + client.getSessionID() + " | Connected client count : " + clientMapBySessionId.size());
    }

    public void clientLoggedIn(AbstractClient client) {
        String userId = client.getUserID();
        clientMapByUserId.put(userId, client);
        logger.debug("Adding new client:" + client.getClientAddress() + " SessionID " + client.getSessionID() + " | Connected client count : " + clientMapBySessionId.size());
    }


    public void removeClientFromClientsTable(String sessionID) {
        AbstractClient client = (AbstractClient) clientMapBySessionId.remove(sessionID);
        if (client != null) {
            String userId = client.getUserID();
            clientMapByUserId.remove(userId);
        }
        logger.debug("Connected client count : " + clientMapBySessionId.size());
    }

    public Client getClientBySessionId(String sessionID) {
        return clientMapBySessionId.get(sessionID);
    }

    public Client getClientByUserId(String userId) {
        return clientMapByUserId.get(userId);
    }



}
