package lk.ac.ucsc.oms.boot_strapper.beans;


import lk.ac.ucsc.oms.common_utility.implGeneral.beans.InitializeNotifier;
import lk.ac.ucsc.oms.boot_strapper.helper.SystemInitializingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import static lk.ac.ucsc.oms.common_utility.api.GlobalLock.lock;



@Startup
@Singleton
public class SessionStartupBean {
    private Logger logger = LogManager.getLogger(SessionStartupBean.class);
    private boolean initialize = false;


    public SessionStartupBean() {
    }


    public boolean isInitialized() {
        return initialize;
    }

    @PostConstruct
    public void startUP() {
        synchronized (lock) {
            SystemInitializingHelper systemInitializingHelper = getSystemInitializingHelper();
            systemInitializingHelper.injectControllerImplementations();
            try {
                systemInitializingHelper.initializeModules();
                initialize = true;
                getInitializeNotifierInstance().setModuleInitialized(true);
                logger.info("***************************  OMS Started ***********************");
            } catch (Exception e) {
                logger.error("Module initialization failed - {} ", e);
                logger.fatal("***************************  OMS Failed to start ***********************");
                System.exit(1);
            }
        }
    }

    public InitializeNotifier getInitializeNotifierInstance() {
        return InitializeNotifier.getInstance();
    }

    public SystemInitializingHelper getSystemInitializingHelper() {
        return new SystemInitializingHelper();
    }
}
