package exceptions.api;

import exceptions.FrameworkException;

/**
 * Base exception for all API framework related exceptions.
 */
public class ApiException extends FrameworkException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
