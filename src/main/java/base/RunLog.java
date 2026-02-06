package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.devtools.CdpVersionFinder;

public class RunLog {
    private static final Logger Log = LogManager.getLogger(RunLog.class);
    private static final Logger specialInfoLog = LogManager.getLogger("RunLog.specialInfo");

    public RunLog() {
    }

    public static void startTestCase(String sModuleName) {
        Log.info("#######################                 " + sModuleName + "       ##########################");
    }

    public static void endTestCase(String sModuleName) {
        Log.info("#######################         End of " + sModuleName + "      ##########################");
    }

    public static void info(String message) {
        Log.info(message);
    }

    public static void info(String message, Throwable exp) {
        Log.info(message, exp);
    }

    public static void warn(String message) {
        Log.warn(message);
    }

    public static void warn(String message, Throwable exp) {
        Log.warn(message, exp);
    }

    public static void error(String message) {
        Log.error(message);
    }

    public static void error(String message, Throwable exp) {
        Log.error(message, exp);
    }

    public static void fatal(String message) {
        Log.fatal(message);
    }

    public static void fatal(String message, Throwable exp) {
        Log.fatal(message, exp);
    }

    public static void debug(String message) {
        Log.debug(message);
    }

    public static void debug(String message, Throwable exp) {
        Log.debug(message, exp);
    }

    public static void trace(String message) {
        Log.trace(message);
    }

    public static void trace(String message, Throwable exp) {
        Log.trace(message, exp);
    }

    public static void specialInfo(String message) {
        specialInfoLog.info(message);
    }

}