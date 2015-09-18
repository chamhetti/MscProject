package lk.ac.ucsc.clientConnector.web_server.impl;

import lk.ac.ucsc.clientConnector.web_server.api.WebServer;
import lk.ac.ucsc.clientConnector.web_server.api.WebServerException;
import org.cometd.annotation.AnnotationCometDServlet;
import org.cometd.bayeux.server.SecurityPolicy;
import org.cometd.server.transport.JSONTransport;
import org.cometd.websocket.server.JettyWebSocketTransport;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.util.*;

/**
 * {@inheritDoc}
 *
 * @author Chathura
 * @author AmilaS
 */
public class JettyWebServer implements WebServer {
    private static Logger logger = LoggerFactory.getLogger(JettyWebServer.class);

    private int timeout;
    private Server server;
    private boolean initialized = false;
    private boolean running = false;
    private boolean webAppAdded = false;
    private String host;
    private int port;
    private WebAppContext webAppContext = new WebAppContext();
    private List<Handler> handlerList = new ArrayList<>();

    public JettyWebServer() {
        logger.debug("Creating Jetty web server");
    }

    /**
     * {@inheritDoc}
     */

    private void initialize() throws WebServerException {
        logger.debug("Initializing Jetty web server");

        if (running) {
            throw new WebServerException("Server is already running");
        }
        server = getNewServer();

        ServerConnector httpConnector = getServerConnector(getServer(), getHttpConnectionFactory(getHttpConfiguration()));
        httpConnector.setPort(port);
        getServer().addConnector(httpConnector);

        // jetty handlers configuration
        HandlerCollection handlerCollection = getHandlerCollection();
        handlerCollection.setHandlers(getHandlerList().toArray(new Handler[0]));

        if (webAppAdded) {
            handlerCollection.addHandler(getWebAppContext());
        }


        // === jetty-request-log ===
        NCSARequestLog requestLog = getNCSARequestLog();
        requestLog.setFilename("./yyyy_mm_dd.request.log");
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setAppend(true);
        RequestLogHandler requestLogHandler = getRequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        handlerCollection.addHandler(requestLogHandler);

        getServer().setHandler(handlerCollection);

        initialized = true;
    }

    /**
     * created for testing
     *
     * @return
     */
    public Server getServer() {
        return server;
    }

    /**
     * created for testing
     *
     * @return
     */
    public Credential getCredential(String s) {
        return Credential.getCredential(s);
    }

    /**
     * created for testing
     *
     * @return
     */
    public RequestLogHandler getRequestLogHandler() {
        return new RequestLogHandler();
    }

    /**
     * created for testing
     *
     * @return
     */
    public NCSARequestLog getNCSARequestLog() {
        return new NCSARequestLog();
    }

    /**
     * created for testing
     *
     * @return
     */
    public Server getNewServer() {
        return new Server();
    }

    /**
     * created for testing
     *
     * @return
     */
    public HashLoginService getHashLoginService(String name) {
        return new HashLoginService(name);
    }

    /**
     * created for testing
     *
     * @return
     */
    public HandlerCollection getHandlerCollection() {
        return new HandlerCollection();
    }

    /**
     * created for testing
     *
     * @return
     */
    public ServerConnector getServerConnector(Server server, HttpConnectionFactory httpConnectionFactory) {
        return new ServerConnector(server, httpConnectionFactory);
    }

    /**
     * created for testing
     *
     * @return
     */
    public ServerConnector getServerConnector(Server server, SslConnectionFactory sslConnectionFactory, HttpConnectionFactory sslHttpConnectionFactory) {
        return new ServerConnector(server, sslConnectionFactory, sslHttpConnectionFactory);
    }

    /**
     * created for testing
     *
     * @return
     */
    public HttpConnectionFactory getHttpConnectionFactory(HttpConfiguration httpsConfiguration) {
        return new HttpConnectionFactory(httpsConfiguration);
    }

    /**
     * created for testing
     *
     * @return
     */
    public HttpConfiguration getHttpConfiguration(HttpConfiguration httpConfiguration) {
        return new HttpConfiguration(httpConfiguration);
    }

    /**
     * created for testing
     *
     * @return
     */
    public HttpConfiguration getHttpConfiguration() {
        return new HttpConfiguration();
    }

    /**
     * created for testing
     *
     * @return
     */
    public SslConnectionFactory getSslConnectionFactory(SslContextFactory sslContextFactory, String httpVersion) {
        return new SslConnectionFactory(sslContextFactory, httpVersion);
    }

    /**
     * created for testing
     *
     * @return
     */
    public SslContextFactory getSslContextFactory() {
        return new SslContextFactory();
    }

    /**
     * created for testing
     *
     * @return
     */
    public WebAppContext getWebAppContext() {
        return webAppContext;
    }

    /**
     * created for testing
     *
     * @return
     */
    public List<Handler> getHandlerList() {
        return handlerList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addServlet(Servlet servlet, String contextPath, String servletPath, EventListener[] listeners) {
        if (!running) {
            logger.debug("Adding new servlet: {}, path: {}", servlet.getServletInfo(), servletPath);

            ServletContextHandler servletContextHandler = getServletContextHandler(ServletContextHandler.SESSIONS);
            servletContextHandler.addServlet(getServletHolder(servlet), servletPath);

            // context handling
            servletContextHandler.setContextPath(contextPath);
            SessionManager sessionManager = getHashSessionManager();
            sessionManager.setMaxInactiveInterval(timeout);
            SessionHandler sessionHandler = getSessionHandler(sessionManager);

            servletContextHandler.setSessionHandler(sessionHandler);

            if (listeners != null) {
                for (EventListener listener : listeners) {
                    logger.debug("Adding new listener: {}", listener);
                    sessionManager.addEventListener(listener);
                }
            }
            getHandlerList().add(servletContextHandler);
        }
    }




    /**
     * created for testing
     *
     * @param sessionManager
     * @return
     */
    public SessionHandler getSessionHandler(SessionManager sessionManager) {
        return new SessionHandler(sessionManager);
    }

    /**
     * created for testing
     *
     * @return
     */
    public SessionManager getHashSessionManager() {
        return new HashSessionManager();
    }

    /**
     * created for testing
     *
     * @param servlet
     * @return
     */
    public ServletHolder getServletHolder(Servlet servlet) {
        return new ServletHolder(servlet);
    }

    /**
     * created for testing
     *
     * @param sessions
     * @return
     */
    public ServletContextHandler getServletContextHandler(int sessions) {
        return new ServletContextHandler(sessions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWebSocket(final Class<?> webSocket, String pathSpec) {
        logger.debug("Adding new web-socket: {}, path: {}", webSocket.getCanonicalName(), pathSpec);
        WebSocketHandler wsHandler = getWebSocketHandler(webSocket);
        ContextHandler wsContextHandler = getContextHandler();
        wsContextHandler.setHandler(wsHandler);
        wsContextHandler.setContextPath(pathSpec);  // this context path doesn't work ftm
        getHandlerList().add(wsHandler);
    }

    public ContextHandler getContextHandler() {
        return new ContextHandler();
    }

    public WebSocketHandler getWebSocketHandler(final Class<?> webSocket) {
        return new WebSocketHandler() {

            @Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(webSocket);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWebApp(String webAppPath, String contextPath) {
        logger.debug("Adding new webapp: {}", webAppPath);
        getWebAppContext().setContextPath(contextPath);
        getWebAppContext().setWar(webAppPath);
        webAppAdded = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws WebServerException {

        initialize();

        if (!initialized) {
            throw new WebServerException("Server not initialized");
        }

        logger.info("Starting jetty web server");
        try {
            getServer().start();
            running = true;
        } catch (Exception e) {
            logger.error("Error while starting jetty web server", e);
            throw new WebServerException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() throws WebServerException {
        try {
            logger.info("Stopping jetty web server");
            stopIt();
            getServer().join();
        } catch (Exception e) {
            throw new WebServerException(e.toString(), e);
        } finally {
            running = false;
        }
    }

    /**
     * created for testing
     *
     * @return
     */
    public void stopIt() throws WebServerException {
        try {
            getServer().stop();
        } catch (Exception e) {
            throw new WebServerException("Error stopping server", e);
        }
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public void setHost(String host) {
        this.host = host;
    }


    @Override
    public void setTimeout(int timeoutSeconds) {
        this.timeout = timeoutSeconds;
    }

    @Override
    public void setConsoleUsers(Map<String, String> consoleUsers) {
//        this.consoleUsers = consoleUsers;
    }

    public boolean isStarted() {
        return server.isStarted();
    }

    public boolean isStopped() {
        return server.isStopped();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


}
