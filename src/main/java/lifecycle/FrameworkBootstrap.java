package lifecycle;


import core.DirectoryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reports.ExtentManager;

public final class FrameworkBootstrap {

    private static final Logger logger = LogManager.getLogger(FrameworkBootstrap.class);

    private FrameworkBootstrap() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Initializes the UAF framework before test execution.
     */
    public static void initialize() {
        logger.info("====================================================");
        logger.info("Initializing UAF Framework...");
        logger.info("====================================================");

        // Create execution directories
        DirectoryManager.initializeExecutionDirectories();

        // Validate each required keys inside the config.properties file
        ConfigurationValidator.validate();
        // Initialize reporting
        ExtentManager.getExtentReports();
        logger.info("UAF Framework initialized successfully.");
    }

}
