package exceptions.api;

/**
 * Thrown when OAuth token persistence fails.
 */
public class TokenStoreException extends ApiException {
    public TokenStoreException(String message) {
        super(message);
    }

    public TokenStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
