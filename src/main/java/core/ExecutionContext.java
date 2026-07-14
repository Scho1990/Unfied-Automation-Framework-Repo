package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final  class ExecutionContext {
    private ExecutionContext() {}

    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final String EXECUTION_ID = START_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

    public static String getExecutionId() {
        return EXECUTION_ID;
    }
    public static LocalDateTime getStartTime() {
        return START_TIME;
    }
}
