package lk.ac.ucsc.clientConnector.handler;

import lk.ac.ucsc.clientConnector.api.Client;
import lk.ac.ucsc.clientConnector.api.ClientManager;
import lk.ac.ucsc.clientConnector.client.AbstractClient;
import lk.ac.ucsc.clientConnector.common.api.TrsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class WebSessionHandler implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger(WebSessionHandler.class);
    private ClientManager clientManager;

    public WebSessionHandler(ClientManager clientManager) {
        this.clientManager = clientManager;
        logger.info("Web Session Handler Created");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        logger.info("Http session with session id:{} opened for: {}", session.getId(), session.getServletContext().getServletContextName());

        Client client = clientManager.createClient(session);
        session.setAttribute(TrsConstants.CLIENT_SESSION_TAG, client);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("Http session destroyed: {}", se.getSession().getId());
        AbstractClient client = (AbstractClient) se.getSession().getAttribute(TrsConstants.CLIENT_SESSION_TAG);
        if (client.isConnected()) {
            client.close("Session timeout", true);
        }

    }
}
