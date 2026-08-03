package exceptions.ui;

import exceptions.FrameworkException;

public final class ScreenshotException extends UiException {
    public ScreenshotException(String message) {
        super(message);
    }

    public ScreenshotException(String message, Throwable cause) {
        super(message, cause);
    }
}
