package exceptions;

public final class ExcelOperationException extends FrameworkException {
    public ExcelOperationException(String message) {
        super(message);
    }

    public ExcelOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
