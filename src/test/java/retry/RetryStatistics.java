package retry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class RetryStatistics {
    private RetryStatistics() {
    }
    private static final AtomicInteger totalRetryAttempts = new AtomicInteger();
    private static final AtomicInteger totalRetriedTests = new AtomicInteger();
    private static final AtomicInteger passedAfterRetry  = new AtomicInteger();
    private static final AtomicInteger failedAfterRetry = new AtomicInteger();

    ConcurrentHashMap<String, Integer> retryCountPerTest = new ConcurrentHashMap<>();
}
