package lk.ac.ucsc.clientConnector.handler;

import lk.ac.ucsc.clientConnector.client.AbstractClient;
import lk.ac.ucsc.clientConnector.common.api.TrsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class WebRequestHandler extends HttpServlet {
    private static final String REQUEST = "oms_request";
    private static Logger logger = LoggerFactory.getLogger(WebRequestHandler.class);
    private String name;

    public WebRequestHandler(String name) {
        this.name = name;
    }

    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String res = processRequest(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(res);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String res = processRequest(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(res);
    }

    private String processRequest(HttpServletRequest request) {
        String responseString = null;
        HttpSession session = request.getSession();
        logger.info("New " + name + " request received for session ID: " + session.getId());

        try {

            AbstractClient client = (AbstractClient) session.getAttribute(TrsConstants.CLIENT_SESSION_TAG);

            if (client != null) {
                String message = request.getParameter(REQUEST);
                if (message != null) {
                    if (client.isConnected()) {
                        if (client.isAuthenticated()) {
                            responseString = client.processUserRequest(message);

                        } else {
                            client.setClientAddress(request.getRemoteAddr());
                            responseString = client.authenticate(message);
                        }

                    } else {
                        logger.error("Web Client is already disconnected: " + client.getSessionID() + ". Closing session.");
                        session.invalidate();
                        responseString = "Session is invalidated, please reconnect";
                    }
                } else {
                    // this happens if no oms message is sent with the http request
                    logger.error("No oms message sent with http request");
                    responseString = "No message sent";
                }
            } else {
                // this should not generally happen either, since a client is always created when the session opens.
                logger.error("Error obtaining web client from session: " + session.getId() + ". Closing session.");
                session.invalidate();
                responseString = "Session is invalidated, please reconnect";
            }

        } catch (Exception e) {
            logger.error("Unhandled exception while serving web request", e);
            responseString = "Unexpected error";
        }

        logger.info("Returning " + name + " response");
        MDC.remove("tid");
        return responseString;
    }
}

