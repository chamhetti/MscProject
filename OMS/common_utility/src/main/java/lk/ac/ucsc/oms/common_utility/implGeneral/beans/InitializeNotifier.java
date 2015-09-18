package lk.ac.ucsc.oms.common_utility.implGeneral.beans;

import java.util.Date;

public class InitializeNotifier {
    private boolean moduleInitialized = false;
    private static InitializeNotifier instance;
    private Date systemInitializedTime;

    private InitializeNotifier() {

    }

    public static synchronized InitializeNotifier getInstance() {
        if (instance == null) {
            instance = new InitializeNotifier();
        }
        return instance;
    }

    public boolean isModuleInitialized() {
        return moduleInitialized;
    }

    public void setModuleInitialized(boolean moduleInitialized) {
        this.moduleInitialized = moduleInitialized;
        if (moduleInitialized) {
            systemInitializedTime = new Date();
        }
    }

    public Date getSystemInitializedTime() {
        return systemInitializedTime;
    }
}
