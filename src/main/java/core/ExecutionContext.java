package core;

import config.ConfigReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final  class ExecutionContext {
    private ExecutionContext() {}

    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final DateTimeFormatter EXECUTION_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    private static final String EXECUTION_ID = START_TIME.format(EXECUTION_FORMAT);

    public static String getExecutionId() {
        return EXECUTION_ID;
    }

    public static LocalDateTime getStartTime() {
        return START_TIME;
    }
}
