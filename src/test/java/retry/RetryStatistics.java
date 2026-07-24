package retry;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class RetryStatistics {
    private RetryStatistics() {
    }
    private static final AtomicInteger totalRetryAttempts = new AtomicInteger();
    private static final AtomicInteger totalRetriedTests = new AtomicInteger();
    private static final AtomicInteger passedAfterRetry  = new AtomicInteger();
    private static final AtomicInteger failedAfterRetry = new AtomicInteger();

    private static final ConcurrentHashMap<String, Integer> retryCountPerTest = new ConcurrentHashMap<>();

    public static void incrementRetryAttempts() {
        totalRetryAttempts.incrementAndGet();
    }

    public static void incrementRetriedTests() {
        totalRetriedTests.incrementAndGet();
    }

    public static void incrementPassedAfterRetry() {
        passedAfterRetry.incrementAndGet();
    }

    public static void incrementFailedAfterRetry() {
        failedAfterRetry.incrementAndGet();
    }

    public static void recordRetry(String testName) {
        retryCountPerTest.merge(testName, 1, Integer::sum);
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

    public static void reset() {
        totalRetryAttempts.set(0);
        totalRetriedTests.set(0);
        passedAfterRetry.set(0);
        failedAfterRetry.set(0);
        retryCountPerTest.clear();
    }

}
