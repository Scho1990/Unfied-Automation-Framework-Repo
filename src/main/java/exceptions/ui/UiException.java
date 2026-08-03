package exceptions.ui;

import exceptions.FrameworkException;

public class UiException extends FrameworkException {
    public UiException(String message) {
        super(message);
    }

    public UiException(String message, Throwable cause) {
        super(message, cause);
    }
}
