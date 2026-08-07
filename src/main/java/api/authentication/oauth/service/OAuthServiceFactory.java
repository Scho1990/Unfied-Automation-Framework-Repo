package api.authentication.oauth.service;

import api.authentication.oauth.configuration.OAuthConfiguration;
import api.config.ApiConfig;

/**
 * Factory class responsible for creating OAuth service instances.
 *
 * <p>
 * This class centralizes the creation of OAuth services and ensures that
 * every service is initialized with the correct configuration.
 *
 * <p>
 * New OAuth providers (GitHub, Microsoft, Jira, Salesforce, etc.)
 * can be added here without affecting the rest of the framework.
 */
public final class OAuthServiceFactory {

    private static final OAuthConfiguration CONFIGURATION = ApiConfig.getSpotifyOAuthConfiguration();

    private OAuthServiceFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Creates a configured Spotify OAuth Service.
     *
     * @return configured SpotifyOAuthService
     */
    public static SpotifyOAuthService createSpotifyOAuthService() {

        return new SpotifyOAuthService(CONFIGURATION);
    }

    /**
     * Creates a configured Spotify Token Service.
     *
     * @return configured SpotifyOAuthService
     */
    public static SpotifyTokenService createSpotifyTokenService() {

        return new SpotifyTokenService(CONFIGURATION);
    }
}
