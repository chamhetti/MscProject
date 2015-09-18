package lk.ac.ucsc.clientConnector.web_server.api;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This factory class creates and returns a new web server instance according to given parameters <p/>
 * The TRS can decide on parameters according to its requirements. See the factory method createWebServer for details
 * <p/>
 * Date: Dec 26, 2012
 *
 * @author Chathura
 * @author AmilaS
 * @since 1.0
 */
public final class WebServerFactory {
    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("/implGeneral/spring-web_server.xml");

    private WebServerFactory() {
    }

    /**
     * This is the factory method for the web server creation
     *
     * @param host               ip address of the web server
     * @param port               port
       * @return the WebServer instance
     */
    public static WebServer createWebServer(String host, int port,  int timeout ) {
        WebServer server = CONTEXT.getBean("jettyServer", WebServer.class);

        server.setHost(host);
        server.setPort(port);
        server.setTimeout(timeout);

        return server;

    }
}
