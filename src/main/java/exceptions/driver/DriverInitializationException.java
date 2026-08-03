package exceptions.driver;

import exceptions.FrameworkException;

public final class DriverInitializationException extends FrameworkException {
    public DriverInitializationException(String message) {
        super(message);
    }

    public DriverInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
