package lk.ac.ucsc.clientConnector.web_server.api;

import org.cometd.bayeux.server.SecurityPolicy;

import javax.servlet.Servlet;
import java.util.EventListener;
import java.util.Map;

/**
 * Controls the web server embedded with TRS in order to serve HTTP and WebSocket Requests <p/>
 * Date: Dec 26, 2012
 *
 * @author Chathura
 * @author AmilaS
 * @since 1.0
 */
public interface WebServer {

    /**
     * Starts the web server
     *
     * @throws WebServerException is the web server exception
     */
    void start() throws WebServerException;

    /**
     * Stops the web server
     *
     * @throws WebServerException is the web server exception
     */
    void stop() throws WebServerException;

    /**
     * Adds a new servlet to the server
     *
     * @param servlet     is the servlet to add
     * @param servletPath path
     * @param listeners   listeners for context (Eg: HttpSessionListener)
     * @throws WebServerException if server is already started
     */
    void addServlet(Servlet servlet, String contextPath, String servletPath, EventListener[] listeners) throws WebServerException;


    /**
     * Adds a new web socket to the server
     *
     * @param webSocket is the servlet to add
     * @param pathSpec  path
     * @throws WebServerException if server is already started
     */
    void addWebSocket(Class<?> webSocket, String pathSpec) throws WebServerException;

    /**
     * Adds a new web app to the server
     *
     * @param webAppPath path of the WAR file
     * @param contextPath context path of the web-app in server
     */
    void addWebApp(String webAppPath, String contextPath);

    void setPort(int port);


    void setHost(String host);


    void setTimeout(int timeoutSeconds);

    void setConsoleUsers(Map<String, String> consoleUsers);


}
