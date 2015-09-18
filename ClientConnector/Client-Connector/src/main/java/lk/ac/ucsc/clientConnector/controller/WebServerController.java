package lk.ac.ucsc.clientConnector.controller;

import lk.ac.ucsc.clientConnector.api.Controller;
import lk.ac.ucsc.clientConnector.web_server.api.WebServer;
import lk.ac.ucsc.clientConnector.web_server.api.WebServerException;
import lk.ac.ucsc.clientConnector.web_server.api.WebServerFactory;
import lk.ac.ucsc.clientConnector.handler.WebRequestHandler;
import lk.ac.ucsc.clientConnector.handler.WebSessionHandler;
import org.cometd.bayeux.server.SecurityPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventListener;


public class WebServerController implements Controller {
    private Logger logger = LoggerFactory.getLogger(WebServerController.class);
    private WebServer webServer;
    private String host;
    private int port;
    private WebRequestHandler webRequestHandler;
    private WebSessionHandler webSessionHandler;
    private String webContextPath;
    private String servletPath;
    private int timeout;


    public void setWebRequestHandler(WebRequestHandler webRequestHandler) {
        this.webRequestHandler = webRequestHandler;
    }
    public void setWebSessionHandler(WebSessionHandler webSessionHandler) {
        this.webSessionHandler = webSessionHandler;
    }
    public void setWebContextPath(String webContextPath) {
        this.webContextPath = webContextPath;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.timeout = sessionTimeout;
    }


    @Override
    public void initController() throws WebServerException {
        webServer = getWebServer();
        webServer.addServlet(webRequestHandler, webContextPath, servletPath, new EventListener[]{webSessionHandler});
    }

    @Override
    public boolean startController() {
        try {
            logger.info("Starting primary web server");
            webServer.start();
            return true;
        } catch (WebServerException e) {
            logger.error("Error starting web server", e);
        }
        return false;
    }

    @Override
    public boolean stopController() {
        try {
            webServer.stop();
            return true;
        } catch (WebServerException e) {
            logger.error("Error stopping web server", e);
        }
        return false;
    }

    public WebServer getWebServer(){
        return WebServerFactory.createWebServer(host, port,  timeout);
    }


}
