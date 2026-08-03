package exceptions.report;

import exceptions.FrameworkException;

public final class ReportException extends FrameworkException {
    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
