package exceptions.api;

public class ResponseValidationException extends ApiException{
    public ResponseValidationException(String message) {
        super(message);
    }

    public ResponseValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
