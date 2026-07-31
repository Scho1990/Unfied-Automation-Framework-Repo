package api.constants;

//ApiConstants should contain only framework-wide API constants.
public final class ApiConstants {

    private ApiConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String ACCEPT_JSON = "application/json";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String APPLICATION_JSON = "application/json";
}
