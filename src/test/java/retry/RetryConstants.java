package retry;

public final class RetryConstants {

    private RetryConstants() {}

    /**
     * Default retry count.
     * This value will be overridden by config.properties or Jenkins.
     */
    public static final int DEFAULT_RETRY_COUNT  = 1;
    public static final String RETRY_SCHEDULED = "RETRY_SCHEDULED";
}
