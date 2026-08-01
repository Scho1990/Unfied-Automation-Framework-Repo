package api.authentication.oauth;

public enum OAuthResponseType {

    CODE("code");

    private final String value;

    OAuthResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
