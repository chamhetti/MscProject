package lk.ac.ucsc.oms.system_configuration.api;

import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.system_configuration.implGeneral.scheduler.SysParamScheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;


public class SystemConfigurationFactory {
    private static Logger logger = LogManager.getLogger(SystemConfigurationFactory.class);
    private static SystemConfigurationFactory factory;
    private static ApplicationContext ctx;
    private static SchedulerInterface sh;

    /**
     * Default Private Constructor
     */
    private SystemConfigurationFactory() throws SysConfigException {
        synchronized (this) {
            if (ctx == null) {
                ctx = new ClassPathXmlApplicationContext("/implGeneral/spring-config-systemconfig.xml");
            }
        }
        if (sh == null) {
            try {
                sh = SchedulerFactory.getScheduler();
            } catch (SchedulingException e) {
                logger.debug("Error in Scheduling.:" + e.getMessage(), e);
                throw new SysConfigException("Error with Scheduler - Constructor failed", e);
            }
        }
    }

    /**
     * Get the instance of the factory
     *
     * @return SystemConfigurationFactory
     */

    public static SystemConfigurationFactory getInstance() throws SysConfigException {
        if (factory == null) {
            return createInstance();
        }
        return factory;
    }

    /**
     * method to create System Configuration Factory Instance
     * @return SystemConfigurationFactory
     */
    private static synchronized SystemConfigurationFactory createInstance() throws SysConfigException {
        if (factory != null) {
            return factory;
        }
        factory = new SystemConfigurationFactory();
        try{
            SysParamScheduler sch = ctx.getBean("sysParamScheduler", SysParamScheduler.class);
            sh.scheduleThisEvent(sch);
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return factory;
    }

    public static void setCtx(ApplicationContext ctx) {
        SystemConfigurationFactory.ctx = ctx;
    }

    public static void setSh(SchedulerInterface sh) {
        SystemConfigurationFactory.sh = sh;
    }

    /**
     * Get the instance of DataSource
     *
     * @return DataSource
     */
    public DataSource getDatasource() {
        return ctx.getBean("dataSource", javax.sql.DataSource.class);
    }

    /**
     * Get the instance of basic DataSource
     *
     * @return DataSource
     */
    public DataSource getBasicDataSource() {
        return ctx.getBean("dataSource", org.apache.commons.dbcp.BasicDataSource.class);
    }



    /**
     * Get the implementation of the service interface
     *
     * @return SysLevelParamManager
     */
    public SysLevelParaManager getSysLevelParamManager() {
        return ctx.getBean("sysLevelParamManager", SysLevelParaManager.class);
    }



    /**
     * Initialize all the service facades of the module
     *
     * @throws SysConfigException
     */
    public void initialize() throws SysConfigException {
        getSysLevelParamManager().initialize();
        //create history data schedule after all the modules initialized
    }



}
