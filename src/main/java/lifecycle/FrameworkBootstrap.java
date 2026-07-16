package lifecycle;


import core.DirectoryManager;
import core.ExecutionContext;
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

        // Validate each required keys inside the config.properties file
        logger.info("Validating Configuration...");
        ConfigurationValidator.validate();

        logger.info("Initializing execution context...");
        ExecutionContext.initialize();

        logger.info("Creating directories...");
        DirectoryManager.initializeExecutionDirectories();

        // Initialize reporting
        ExtentManager.getExtentReports();
        logger.info("UAF Framework initialized successfully.");
    }

}
