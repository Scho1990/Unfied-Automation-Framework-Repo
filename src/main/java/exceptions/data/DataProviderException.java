package exceptions.data;

import exceptions.FrameworkException;

public final class DataProviderException extends FrameworkException {
    public DataProviderException(String message) {
        super(message);
    }

    public DataProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
