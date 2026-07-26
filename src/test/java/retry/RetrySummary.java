package retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reports.ExtentLogger;

import java.util.Map;

public final class RetrySummary {
    private static final Logger logger = LogManager.getLogger(RetrySummary.class);
    private static final String SEPARATOR = "============================================================";
    private static final String SUB_SEPARATOR = "------------------------------------------------------------";
    private RetrySummary() {}

    public static void logSummary() {

          logHeader();
          logMetrics();
          logRetryBreakdown();
          logFooter();

    }

    private static void logHeader(){

        log(SEPARATOR);
        log("                    RETRY SUMMARY");
        log(SEPARATOR);

    }

    private static void logMetrics(){

        log(String.format(
                "Total Retry Attempts      : %d",
                RetryStatistics.getTotalRetryAttempts()));

        log(
                String.format("Unique Retried Tests      : %d",
                RetryStatistics.getTotalRetriedTests()));

        log(
                String.format("Passed After Retry        : %d",
                RetryStatistics.getPassedAfterRetry()));

        log(
                String.format("Failed After Retry        : %d",
                RetryStatistics.getFailedAfterRetry()));

        log(String.format(
                "Retry Success Rate        : %.2f%%",
                calculateRetrySuccessRate()));

        log(SUB_SEPARATOR);
    }

    private static double calculateRetrySuccessRate(){

        int totalRetriedTests = RetryStatistics.getTotalRetriedTests();

        if (totalRetriedTests == 0){
            return 0.0;
        }

        return (RetryStatistics.getPassedAfterRetry()*100.0)/totalRetriedTests;
    }

    private static void logRetryBreakdown(){

        log("Per-Test Retry Count");
        log(SUB_SEPARATOR);

        Map<String, Integer> retryCountMap = RetryStatistics.getRetryCountPerTest();

        if(retryCountMap.isEmpty()){
            log("No tests required retry.");
            return;
        }

        retryCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> log(
                        String.format(
                                "%-35s : %d",
                                entry.getKey(),
                                entry.getValue())));
    }

    /**
     * Logs the supplied message to both Log4j and Extent Report.
     *
     * @param message message to log
     */
    private static void log(String message){
        logger.info(message);
        ExtentLogger.info(message);
    }

    private static void logFooter(){
        logger.info(SEPARATOR);
    }
}
