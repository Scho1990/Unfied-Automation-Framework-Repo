package api.authentication.oauth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.Objects;

public class SpotifyOAuthService {
    private static final Logger logger = LogManager.getLogger(SpotifyOAuthService.class);
    private final OAuthConfiguration oAuthConfiguration;

    /**
     * Creates a new Spotify OAuth Service.
     *
     * @param oAuthConfiguration OAuth configuration
     */
     SpotifyOAuthService(OAuthConfiguration oAuthConfiguration) {
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

    public OAuthToken exchangeAuthorizationCode(String authorizationCode) {

        Objects.requireNonNull(authorizationCode, "Authorization Code cannot be null.");

        logger.info("Exchanging authorization code for OAuth access token.");

        Response response = RestAssured
                .given()
                   .contentType(ContentType.URLENC)
                   .formParam(OAuthConstants.GRANT_TYPE, OAuthGrantType.AUTHORIZATION_CODE.getValue())
                   .formParam(OAuthConstants.CODE, authorizationCode)
                   .formParam(OAuthConstants.REDIRECT_URI, oAuthConfiguration.getRedirectUri())
                   .formParam(OAuthConstants.CLIENT_ID, oAuthConfiguration.getClientId())
                   .formParam(OAuthConstants.CLIENT_SECRET, oAuthConfiguration.getClientSecret())
                .post(oAuthConfiguration.getTokenUrl())
                .then()
                   .extract()
                .response();

        validateTokenResponse(response);
        logger.info("Authorization code exchanged successfully.");

        return mapToken(response);
    }

    /**
     * Validates Spotify OAuth token response.
     *
     * @param response RestAssured response
     */
    private void validateTokenResponse(Response response) {

        Objects.requireNonNull(response, "Response cannot be null.");
        logger.info("Validating token response.");
        if (response.getStatusCode() != HttpStatus.SC_OK) {

            logger.error("Spotify OAuth token exchange failed. HTTP Status {}.", response.getStatusCode());

            throw new IllegalStateException(String.format("Spotify OAuth token exchange failed. HTTP Status %d.",
                    response.getStatusCode()));
        }
    }

    private OAuthToken mapToken(Response response) {

        OAuthToken token = response.as(OAuthToken.class);

        if (token == null) {
            throw new IllegalArgumentException("Failed to deserialize Spotify OAuth response.");
        }

        token.setIssuedAt(Instant.now());

        logger.info("OAuth Token received successfully.");

        return token;
    }
}
