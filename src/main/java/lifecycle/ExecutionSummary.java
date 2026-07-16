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
}
