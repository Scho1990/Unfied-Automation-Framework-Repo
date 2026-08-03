package exceptions.ui;

import exceptions.FrameworkException;

public class WaitTimeoutException extends UiException {
    public WaitTimeoutException(String message) {
        super(message);
    }
    public WaitTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
