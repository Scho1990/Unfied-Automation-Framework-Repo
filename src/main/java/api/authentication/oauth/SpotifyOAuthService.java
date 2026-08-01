package api.authentication.oauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class SpotifyOAuthService {
    private static final Logger logger = LogManager.getLogger(SpotifyOAuthService.class);
    private final OAuthConfiguration oAuthConfiguration;

    /**
     * Creates a new Spotify OAuth Service.
     *
     * @param oAuthConfiguration OAuth configuration
     */
    public SpotifyOAuthService(OAuthConfiguration oAuthConfiguration) {
        this.oAuthConfiguration = Objects.requireNonNull(
                oAuthConfiguration,
                "OAuthConfiguration cannot be null."
        );
    }

    /**
     * Generates the Spotify OAuth Authorization URL.
     *
     * @return OAuth Authorization URL
     */
    public String getAuthorizationUrl() {
        logger.info("Generating Spotify OAuth authorization URL.");
        String state = OAuthStateGenerator.generateState();

        String authorizationUrl = AuthorizationUrlBuilder.buildAuthorizationUrl(
                oAuthConfiguration.getAuthorizationUrl(),
                oAuthConfiguration.getClientId(),
                oAuthConfiguration.getRedirectUri(),
                oAuthConfiguration.getScope(),
                OAuthResponseType.CODE,
                state
        );
        logger.info("Spotify OAuth authorization URL generated successfully.");
        logger.debug("Spotify OAuth authorization URL generated successfully: {}", authorizationUrl);
        return authorizationUrl;
    }
}
