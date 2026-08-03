package api.authentication.oauth.configuration;

public enum OAuthGrantType {

    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials");

    private final String value;

    OAuthGrantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
