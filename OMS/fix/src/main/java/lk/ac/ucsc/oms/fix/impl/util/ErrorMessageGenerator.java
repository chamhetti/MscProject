package lk.ac.ucsc.oms.fix.impl.util;


public final class ErrorMessageGenerator {
    private static String moduleCode;

    private ErrorMessageGenerator() {

    }

    public static void setModuleCode(String moduleCode) {
        ErrorMessageGenerator.moduleCode = moduleCode;
    }

    public static String getErrorMsg(int errCode) {
        return moduleCode.concat(": ") + errCode;
    }
}
