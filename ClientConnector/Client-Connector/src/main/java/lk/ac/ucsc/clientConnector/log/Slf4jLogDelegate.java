package lk.ac.ucsc.clientConnector.log;

import org.hornetq.spi.core.logging.LogDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * User: amilas
 * Date: 9/21/14
 * Time: 1:10 PM
 */
public class Slf4jLogDelegate implements LogDelegate {
    private final Logger logger;
    public Slf4jLogDelegate(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }
    @Override
    public void debug(Object message) {
        logger.debug(message.toString());
    }
    @Override
    public void debug(Object message, Throwable t) {
        logger.debug(message.toString(), t);
    }
    @Override
    public void error(Object message) {
        logger.error(message.toString());
    }
    @Override
    public void error(Object message, Throwable t) {
        logger.error(message.toString(), t);
    }
    @Override
    public void fatal(Object message) {
        logger.error(message.toString());
    }
    @Override
    public void fatal(Object message, Throwable t) {
        logger.error(message.toString(), t);
    }
    @Override
    public void info(Object message) {
        logger.info(message.toString());
    }
    @Override
    public void info(Object message, Throwable t) {
        logger.info(message.toString(), t);
    }
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }
    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }
    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }
    @Override
    public void trace(Object message) {
        logger.trace(message.toString());
    }
    @Override
    public void trace(Object message, Throwable t) {
        logger.trace(message.toString(), t);
    }
    @Override
    public void warn(Object message) {
        logger.warn(message.toString());
    }
    @Override
    public void warn(Object message, Throwable t) {
        logger.warn(message.toString(), t);
    }

}
