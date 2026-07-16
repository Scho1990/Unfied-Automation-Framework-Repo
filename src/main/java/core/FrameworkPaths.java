package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FrameworkPaths {
public static Logger logger = LogManager.getLogger(FrameworkPaths.class);
    private FrameworkPaths() {

    }

    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("user.dir"));

    public static Path getProjectRoot() {
        return PROJECT_ROOT;
    }

    public static Path getResourcesDirectory() {
        return PROJECT_ROOT.resolve("src").resolve("main").resolve("resources");
    }
    public static Path getConfigDirectory() {
        return getResourcesDirectory().resolve("config");
    }

    public static Path getDataDirectory() {
        return getResourcesDirectory().resolve("data");
    }

    public static Path getExecutionDirectory() {
        return PROJECT_ROOT.resolve("reports").resolve(ExecutionContext.getExecutionId());
    }

    public static Path getScreenshotDirectory() {
        return getExecutionDirectory().resolve("screenshots");
    }

    public static Path getLogDirectory() {
        return getExecutionDirectory().resolve("logs");
    }

    public static Path getDownloadDirectory() {
        return getExecutionDirectory().resolve("downloads");
    }

}
