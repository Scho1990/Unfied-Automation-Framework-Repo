package core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FrameworkPaths {

    private FrameworkPaths() {

    }

    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("user.dir"));

    public static Path getProjectRoot() {
        return PROJECT_ROOT;
    }

    public static Path getResourceDirectory() {
        return PROJECT_ROOT.resolve("src").resolve("main").resolve("resources");
    }
    public static Path getConfigDirectory() {
        return getResourceDirectory().resolve("config");
    }

    public static Path getDataDirectory() {
        return getResourceDirectory().resolve("data");
    }

    public static Path getExecutionRoot() {
        return PROJECT_ROOT.resolve("reports").resolve(ExecutionContext.getExecutionId());
    }

    public static Path getReportPath() {
        return getExecutionRoot().resolve("ExtentReport.html");
    }

    public static Path getScreenshotDirectory() {
        return getExecutionRoot().resolve("screenshots");
    }

    public static Path getLogDirectory() {
        return getExecutionRoot().resolve("logs");
    }

    public static Path getDownloadDirectory() {
        return getExecutionRoot().resolve("downloads");
    }

}
