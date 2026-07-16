package lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Instant;

public final class ExecutionSummary {

    private static final Logger logger = LogManager.getLogger(ExecutionSummary.class);

    private ExecutionSummary() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Logs the execution summary after completion of a test suite.
     *
     * @param suiteName Test suite name
     * @param passed    Number of passed tests
     * @param failed    Number of failed tests
     * @param skipped   Number of skipped tests
     * @param endTime   Execution end time
     */
    public static void logExecutionSummary(
            String suiteName,
            int passed,
            int failed,
            int skipped,
            Instant endTime) {
        logger.info("Generating execution summary...");

    }
}
