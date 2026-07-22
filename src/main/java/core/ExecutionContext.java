package core;

import config.ConfigReader;
import constants.FrameworkConstants;
import enums.BrowserType;
import lifecycle.FrameworkVersion;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExecutionContext {

    private ExecutionContext() {}
    private static String executionId;
    private static Instant startTime;
    private static BrowserType browser;
    private static String environment;
    private static String executionMode;
    private static boolean headless;
    private static String frameworkVersion;
    private static String javaVersion;
    private static String osName;
    private static String osVersion;
    private static String userName;
    private static boolean initialized=false;

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }
        //executionId = UUID.randomUUID().toString();
        executionId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"))
                + "_" + ConfigReader.getPropertyOrSystem(FrameworkConstants.BROWSER)
                + "_" + ConfigReader.getPropertyOrSystem(FrameworkConstants.EXECUTION);
        startTime = Instant.now();
        browser = BrowserType.valueOf(ConfigReader.getPropertyOrSystem("browser").toUpperCase());
        environment = ConfigReader.getPropertyOrSystem("environment");
        executionMode = ConfigReader.getPropertyOrSystem("execution");
        headless = ConfigReader.getBooleanProperty("headless");
        javaVersion = System.getProperty("java.version");
        osName = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        userName = System.getProperty("user.name");
        frameworkVersion = FrameworkVersion.VERSION;
        initialized = true;
    }

    public static String getExecutionId() {
        if (!initialized) {
            throw new IllegalStateException("ExecutionContext has not been initialized.");
        }
        return executionId;
    }
    public static Instant getStartTime() {
        return startTime;
    }
    public static BrowserType getBrowser() {
        return browser;
    }
    public static String getEnvironment() {
        return environment;
    }
    public static String getExecutionMode() {return executionMode;}
    public static String getJavaVersion() {
        return javaVersion;
    }
    public static String getOsName() {
        return osName;
    }
    public static String getOsVersion() {
        return osVersion;
    }
    public static String getUserName() {
        return userName;
    }
    public static String getFrameworkVersion() {
        return frameworkVersion;
    }
    public static boolean isHeadless() {
        return headless;
    }
    public static boolean isInitialized() {
        return initialized;
    }

}
