package retry;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class RetryStatistics {
    private RetryStatistics() {
    }
    //How many retry attempts occurred?
    private static final AtomicInteger totalRetryAttempts = new AtomicInteger();
    //How many unique test methods required at least one retry?
    private static final AtomicInteger totalRetriedTests = new AtomicInteger();

    private static final AtomicInteger passedAfterRetry  = new AtomicInteger();
    private static final AtomicInteger failedAfterRetry = new AtomicInteger();

    private static final ConcurrentHashMap<String, Integer> retryCountPerTest = new ConcurrentHashMap<>();

    private static void incrementRetryAttempts() {
        totalRetryAttempts.incrementAndGet();
    }

    private static void incrementRetriedTests() {
        totalRetriedTests.incrementAndGet();
    }

    public static void incrementPassedAfterRetry() {
        passedAfterRetry.incrementAndGet();
    }

    public static void incrementFailedAfterRetry() {
        failedAfterRetry.incrementAndGet();
    }

    /**
     * Records a retry event for the specified test.
     * <p>
     * Updates:
     * - Total retry attempts
     * - Retry count for the test
     * - Total unique retried tests (only on first retry)
     */
    public static void recordRetry(String testName) {
        if (testName == null || testName.isBlank()) {
            throw new IllegalArgumentException("Test name cannot be null or blank");
        }
        incrementRetryAttempts();
        int retryCount = retryCountPerTest.merge(testName, 1, Integer::sum);
        if(retryCount == 1){
            incrementRetriedTests();
        }

    }

    public static int getTotalRetryAttempts() {
        return totalRetryAttempts.get();
    }

    public static int getTotalRetriedTests() {
        return totalRetriedTests.get();
    }

    public static int getPassedAfterRetry() {
        return passedAfterRetry.get();
    }

    public static int getFailedAfterRetry() {
        return failedAfterRetry.get();
    }

    public static Map<String, Integer> getRetryCountPerTest() {
        return Collections.unmodifiableMap(retryCountPerTest);
    }

    public static int getRetryCount(String testName) {
        return retryCountPerTest.getOrDefault(testName, 0);
    }

    public static void reset() {
        totalRetryAttempts.set(0);
        totalRetriedTests.set(0);
        passedAfterRetry.set(0);
        failedAfterRetry.set(0);
        retryCountPerTest.clear();
    }

}
