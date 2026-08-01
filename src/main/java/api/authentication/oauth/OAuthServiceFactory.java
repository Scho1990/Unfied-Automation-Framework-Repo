package api.authentication.oauth;

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

    private OAuthServiceFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Creates a configured Spotify OAuth Service.
     *
     * @return configured SpotifyOAuthService
     */
    public static SpotifyOAuthService spotify(){

        return new SpotifyOAuthService(ApiConfig.getSpotifyOAuthConfiguration());
    }
}
