package api.constants;

//ApiConstants should contain only framework-wide API constants.
public final class ApiConstants {

    private ApiConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String BEARER = "Bearer ";
    public static final String API_KEY = "x-api-key";
    public static final String BASIC = "Basic ";
}
