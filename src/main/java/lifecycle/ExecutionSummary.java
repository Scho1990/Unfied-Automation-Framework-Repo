package lifecycle;

import core.ExecutionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ExecutionSummary {

    private static final Logger logger = LogManager.getLogger(ExecutionSummary.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a", Locale.ENGLISH);

    private static final String HEADER_SEPARATOR =
            "============================================================";

    private static final String SECTION_SEPARATOR =
            "------------------------------------------------------------";

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
        int totalTests = getTotalTests(passed, failed, skipped);
        Duration executionDuration =getExecutionDuration(endTime);
        double successRate = calculateSuccessRate(passed,totalTests);
        String startTime = formatExecutionTime(ExecutionContext.getStartTime());
        String endTimeFormatted = formatExecutionTime(endTime);
        String duration = formatExecutionDuration(executionDuration);

        logger.info(HEADER_SEPARATOR);
        logger.info("                 UAF EXECUTION SUMMARY");
        logger.info(HEADER_SEPARATOR);

        logKeyValue("Suite Name", suiteName);
        logKeyValue("Execution ID", ExecutionContext.getExecutionId());
        logKeyValue("Framework Version", ExecutionContext.getFrameworkVersion());
        logKeyValue("Browser", ExecutionContext.getBrowser());
        logKeyValue("Environment", ExecutionContext.getEnvironment());
        logKeyValue("Java Version", ExecutionContext.getJavaVersion());
        logKeyValue("Operating System",
                ExecutionContext.getOsName() + " " + ExecutionContext.getOsVersion());
        logKeyValue("User", ExecutionContext.getUserName());

        logger.info(SECTION_SEPARATOR);

        logKeyValue("Start Time", startTime);
        logKeyValue("End Time", endTimeFormatted);
        logKeyValue("Duration", duration);

        logger.info(SECTION_SEPARATOR);

        logKeyValue("Total Tests", totalTests);
        logKeyValue("Passed", passed);
        logKeyValue("Failed", failed);
        logKeyValue("Skipped", skipped);
        logKeyValue("Success Rate",
                String.format("%.2f%%", successRate));

        logger.info(HEADER_SEPARATOR);
    }

    private static int getTotalTests(int passed,int failed,int skipped) {
        return passed+failed+skipped;
    }

    private static Duration getExecutionDuration(Instant endTime) {
        return Duration.between(
                ExecutionContext.getStartTime(),
                endTime);
    }

    private static double calculateSuccessRate(int passed, int total) {
        if (total == 0) {
            return 0.0;
        }
        return (passed * 100.0) / total;
    }

    private static String formatExecutionDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).toSeconds();
        return String.format("%02d min %02d sec", minutes, seconds);
    }

    private static String formatExecutionTime(Instant instant) {
        return DATE_TIME_FORMATTER
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }

    private static void logKeyValue(String key, Object value) {
        logger.info(String.format("%-20s : %s", key, value));
    }
}
