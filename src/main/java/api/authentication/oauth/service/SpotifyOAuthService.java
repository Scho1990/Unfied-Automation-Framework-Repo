package api.authentication.oauth.service;

import api.authentication.oauth.utility.AuthorizationUrlBuilder;
import api.authentication.oauth.utility.OAuthStateGenerator;
import api.authentication.oauth.configuration.OAuthConfiguration;
import api.authentication.oauth.configuration.OAuthResponseType;
import api.authentication.oauth.model.OAuthToken;
import exceptions.api.OAuthException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

public class SpotifyOAuthService {
    private static final Logger logger = LogManager.getLogger(SpotifyOAuthService.class);
    private final OAuthConfiguration configuration;
    private final SpotifyTokenService tokenService;

    /**
     * Creates a new Spotify OAuth Service.
     *
     * @param configuration OAuth configuration
     */
     SpotifyOAuthService(OAuthConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration, "OAuthConfiguration cannot be null.");
        this.tokenService = new SpotifyTokenService(configuration);
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
                configuration.getAuthorizationUrl(),
                configuration.getClientId(),
                configuration.getRedirectUri(),
                configuration.getScope(),
                OAuthResponseType.CODE,
                state
        );
        logger.info("Spotify OAuth authorization URL generated successfully.");
        logger.debug("Spotify OAuth authorization URL generated successfully: {}", authorizationUrl);
        return authorizationUrl;
    }

    public OAuthToken exchangeAuthorizationCode(String authorizationCode) {

        if (authorizationCode == null || authorizationCode.isBlank()) {
            throw new OAuthException("Authorization code cannot be null or blank.");
        }
        logger.info("Exchanging authorization code for OAuth access token.");
        OAuthToken token = tokenService.exchangeAuthorizationCode(authorizationCode);
        logger.info("Authorization code exchanged successfully.");
        return token;
    }


}
