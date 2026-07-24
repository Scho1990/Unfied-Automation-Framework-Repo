package retry;

import config.ConfigReader;
import constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private final int maxRetryCount = RetryDecisionEngine.getMaxRetryCount();

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String testName = TestIdentifier.getTestKey(result);
        if(!RetryDecisionEngine.shouldRetry(throwable)) {
            result.setAttribute(RetryConstants.RETRY_SCHEDULED, Boolean.FALSE);
            logger.info("Retry skipped because exception is not retryable for : {} " ,testName);
            return false;
        }

        if (retryCount < maxRetryCount) {
            retryCount++;
            RetryStatistics.recordRetry(testName);
            logger.warn(
                    "Retrying Test '{}' | Attempt {}/{} | Exception : {}",
                    testName,
                    retryCount,
                    maxRetryCount,
                    throwable.getClass().getSimpleName()
            );
            result.setAttribute(RetryConstants.RETRY_SCHEDULED,Boolean.TRUE);
            return true;
        }
        logger.info(
                "Maximum retry attempts ({}) exhausted for test '{}'",
                maxRetryCount,
                testName
        );
        result.setAttribute(RetryConstants.RETRY_SCHEDULED,Boolean.FALSE);
        return false;
    }
}
