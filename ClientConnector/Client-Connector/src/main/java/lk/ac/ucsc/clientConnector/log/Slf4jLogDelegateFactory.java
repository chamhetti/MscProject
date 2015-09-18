package lk.ac.ucsc.clientConnector.log;

import org.hornetq.spi.core.logging.LogDelegate;
import org.hornetq.spi.core.logging.LogDelegateFactory;

/**
 * User: amilas
 * Date: 9/21/14
 * Time: 1:25 PM
 */

public class Slf4jLogDelegateFactory implements LogDelegateFactory {
    @Override
    public LogDelegate createDelegate(Class clazz) {
        return new Slf4jLogDelegate(clazz);
    }
}