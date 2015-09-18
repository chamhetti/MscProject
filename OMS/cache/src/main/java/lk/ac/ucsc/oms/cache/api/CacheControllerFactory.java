package lk.ac.ucsc.oms.cache.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CacheControllerFactory {

    protected static ApplicationContext context = new ClassPathXmlApplicationContext("/impl/spring-config-cache.xml");

    public static CacheControllerInterface getCacheController(CachePersister cp, String cacheName) {
        return (CacheControllerInterface) context.getBean("cacheController", cp, cacheName);
    }

}
