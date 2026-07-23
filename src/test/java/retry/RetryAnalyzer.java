package retry;

import config.ConfigReader;
import constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private final int maxRetryCount = getMaxRetryCount();

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if(!RetryDecisionEngine.shouldRetry(throwable)) {
            logger.info("Retry skipped because exception is not retryable");
            return false;
        }

        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.warn(
                    "Retrying Test '{}' | Attempt {}/{} | Exception : {}",
                    result.getMethod().getMethodName(),
                    retryCount,
                    maxRetryCount,
                    throwable.getClass().getSimpleName()
            );
            return true;
        }
        return false;
    }

    private int getMaxRetryCount() {
        String retry = System.getProperty("retryCount",
                ConfigReader.getPropertyOrSystem(FrameworkConstants.RETRY_COUNT));
        return Integer.parseInt(retry);
    }
}
