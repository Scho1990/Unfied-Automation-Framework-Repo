package retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        logger.info(SEPARATOR);
        logger.info("                    RETRY SUMMARY");
        logger.info(SEPARATOR);

    }

    private static void logMetrics(){

        logger.info(
                "Total Retry Attempts      : {}",
                RetryStatistics.getTotalRetryAttempts());

        logger.info(
                "Unique Retried Tests      : {}",
                RetryStatistics.getTotalRetriedTests());

        logger.info(
                "Passed After Retry        : {}",
                RetryStatistics.getPassedAfterRetry());

        logger.info(
                "Failed After Retry        : {}",
                RetryStatistics.getFailedAfterRetry());

        logger.info(
                "Retry Success Rate        : {}%",
                String.format("%.2f", calculateRetrySuccessRate()));

        logger.info(SUB_SEPARATOR);
    }

    private static double calculateRetrySuccessRate(){

        int totalRetriedTests = RetryStatistics.getTotalRetriedTests();

        if (totalRetriedTests == 0){
            return 0.0;
        }

        return (RetryStatistics.getPassedAfterRetry()*100.0)/totalRetriedTests;
    }

    private static void logRetryBreakdown(){

        logger.info("Per-Test Retry Count");
        logger.info(SUB_SEPARATOR);

        Map<String, Integer> retryCountMap = RetryStatistics.getRetryCountPerTest();

        if(retryCountMap.isEmpty()){
            logger.info("No tests required retry.");
            return;
        }

        retryCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> logger.info(
                        String.format(
                                "%-35s : %d",
                                entry.getKey(),
                                entry.getValue())));
    }

    private static void logFooter(){
        logger.info(SEPARATOR);
    }
}
