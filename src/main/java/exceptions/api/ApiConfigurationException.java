package exceptions.api;

public class ApiConfigurationException extends ApiException{
    public ApiConfigurationException(String message) {
        super(message);
    }

    public ApiConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
