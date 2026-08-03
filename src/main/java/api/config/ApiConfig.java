package api.config;

import api.authentication.oauth.configuration.OAuthConfiguration;
import api.authentication.oauth.storage.TokenStoreType;
import config.ConfigReader;
import exceptions.api.ApiConfigurationException;

public final class ApiConfig {

    private static OAuthConfiguration spotifyOAuthConfiguration;
    private ApiConfig() {
    }

    public static String getBaseUrl() {
        return ConfigReader.getPropertyOrSystem("api.base.url");
    }

    public static String getSpotifyAuthorizationUrl() {
        return ConfigReader.getPropertyOrSystem("spotify.authorization.url");
    }

    public static String getAuthorizationCode() {
        return ConfigReader.getProperty("spotify.authorization.code");
    }

    public static String getSpotifyTokenUrl() {
        return ConfigReader.getPropertyOrSystem("spotify.token.url");
    }

    public static String getSpotifyClientId() {
        return ConfigReader.getPropertyOrSystem("spotify.client.id");
    }

    public static String getSpotifyClientSecret() {
        return ConfigReader.getPropertyOrSystem("spotify.client.secret");
    }

    public static String getSpotifyRedirectUri() {
        return ConfigReader.getPropertyOrSystem("spotify.redirect.uri");
    }

    public static String getSpotifyScope() {
        return ConfigReader.getPropertyOrSystem("spotify.scopes");
    }

    public static String getSpotifyRefreshToken() {
        return ConfigReader.getPropertyOrSystem("spotify.refresh.token");
    }

    public static String getContentType() {
        return ConfigReader.getProperty("api.content.type");
    }
    public static String getAccept() {
        return ConfigReader.getProperty("api.accept");
    }

    public static int getConnectionTimeout() {
        return ConfigReader.getIntProperty("api.connect.timeout");
    }

    public static int getReadTimeout() {
        return ConfigReader.getIntProperty("api.read.timeout");
    }

    public static boolean isRequestLoggingEnabled() {
        return ConfigReader.getBooleanProperty("api.log.request");
    }

    public static boolean isResponseLoggingEnabled() {
        return ConfigReader.getBooleanProperty("api.log.response");
    }

    public static String getApiKey(){
        return ConfigReader.getProperty("api.key");
    }

    public static TokenStoreType getTokenStoreType() {
        String value = ConfigReader
                .getProperty("oauth.token.store.type");
        try {
            return TokenStoreType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiConfigurationException("Unsupported OAuth Token Store Type: " + value,e);
        }
    }

    public static String getOAuthTokenStorePath() {

        return ConfigReader
                .getProperty("oauth.token.store.path")
                .trim();
    }

    public static OAuthConfiguration getSpotifyOAuthConfiguration() {

        if (spotifyOAuthConfiguration == null) {

            spotifyOAuthConfiguration = new OAuthConfiguration(getSpotifyAuthorizationUrl(),
                    getSpotifyTokenUrl(),
                    getSpotifyClientId(),
                    getSpotifyClientSecret(),
                    getSpotifyRedirectUri(),
                    getSpotifyScope());
        }
        return spotifyOAuthConfiguration;
    }

}

