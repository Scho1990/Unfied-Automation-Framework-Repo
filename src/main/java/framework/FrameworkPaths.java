package framework;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FrameworkPaths {

    private FrameworkPaths() {

    }

    public static final String PROJECT_ROOT = System.getProperty("user.dir");

    // Created only once per JVM execution
    public static final String EXECUTION_ID = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    public static String getProjectRoot() {
        return PROJECT_ROOT;
    }

    public static String getResourceDirectory() {
        return PROJECT_ROOT + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }
    public static String getConfigDirectory() {
        return getResourceDirectory() + File.separator + "config";
    }

    public static String getDataDirectory() {
        return getResourceDirectory() + File.separator + "data";
    }

    public static String getExecutionDirectory() {
        return getProjectRoot() + File.separator + "reports"+ File.separator + EXECUTION_ID;
    }

    public static String getReportPath() {
        return getExecutionDirectory() + File.separator + "ExtentReport.html";
    }

    public static String getScreenshotDirectory() {
        return getExecutionDirectory() + File.separator + "screenshots";
    }

    public static String getLogDirectory() {
        return getExecutionDirectory() + File.separator + "logs";
    }

    public static String getDownloadDirectory() {
        return getExecutionDirectory() + File.separator + "downloads";
    }

}
