package lk.ac.ucsc.clientConnector.sync_async_mapper.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This factory class is responsible for providing the mapper singleton instance.
 *
 * @author dasun
 *         Date: 12/26/12
 *         Time: 11:37 AM
 */
public final class MapperFactory {
    private static ApplicationContext ctx=new ClassPathXmlApplicationContext("/spring-config-sync_async_mapper.xml");
    private static MapperFactory factory = null;

    public static MapperFactory getInstance() {
        if (factory == null) {
            return getInstance(ctx);
        } else {
            return factory;
        }
    }

    public static synchronized MapperFactory getInstance(ApplicationContext context) {
        if (factory == null) {
            factory = new MapperFactory(context);
        }
        return factory;
    }

    private MapperFactory(ApplicationContext context) {
        ctx = context;
    }

    public Mapper getMapper() {
        return (Mapper) ctx.getBean("MapperImpl");
    }

    public Mapper createMapper(int timeout, int maxRequests){
        Mapper mapper = (Mapper) ctx.getBean("protMapper");
        mapper.setSleepTime(timeout);
        mapper.setMaxNoOfRequests(maxRequests);
        return mapper;
    }
}
