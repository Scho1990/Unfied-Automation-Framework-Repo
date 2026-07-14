package exceptions;

public final class ScreenshotException extends FrameworkException{
    public ScreenshotException(String message) {
        super(message);
    }

    public ScreenshotException(String message, Throwable cause) {
        super(message, cause);
    }
}
