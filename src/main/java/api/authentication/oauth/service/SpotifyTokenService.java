package api.authentication.oauth.service;

import api.authentication.oauth.configuration.OAuthConfiguration;
import api.authentication.oauth.configuration.OAuthConstants;
import api.authentication.oauth.configuration.OAuthGrantType;
import api.authentication.oauth.model.OAuthToken;
import api.specifications.RequestSpecFactory;
import exceptions.api.OAuthException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class SpotifyTokenService {
    private static final Logger logger = LogManager.getLogger(SpotifyTokenService.class);
    private final OAuthConfiguration configuration;

    SpotifyTokenService(OAuthConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration, "OAuthConfiguration cannot be null."
        );
    }

    /**
     * Exchanges an authorization code for an OAuth token.
     */
    public OAuthToken exchangeAuthorizationCode(String authorizationCode){

        validateAuthorizationCode(authorizationCode);
        Map<String, String> formParameters = createFormParameters();
        formParameters.put(OAuthConstants.CODE, authorizationCode);
        formParameters.put(OAuthConstants.GRANT_TYPE, OAuthGrantType.AUTHORIZATION_CODE.getValue());
        formParameters.put(OAuthConstants.REDIRECT_URI, configuration.getRedirectUri());
        return exchangeToken(formParameters);
    }

    /**
     * Refreshes an expired access token.
     */
    public OAuthToken refreshAccessToken(String refreshToken) {

        validateRefreshToken(refreshToken);

        Map<String, String> formParameters = createFormParameters();

        formParameters.put(
                OAuthConstants.GRANT_TYPE,
                OAuthGrantType.REFRESH_TOKEN.getValue());

        formParameters.put(
                OAuthConstants.REFRESH_TOKEN,
                refreshToken);

        return exchangeToken(formParameters);
    }

    /**
     * Executes the token request.
     */
    private OAuthToken exchangeToken(Map<String, String> formParameters) {
        logger.info("Invoking Spotify OAuth Token endpoint.");
        Response response = RestAssured
                .given()
                .spec(RequestSpecFactory.getOAuthRequestSpecification())
                .formParams(formParameters)
                .post(configuration.getTokenUrl());

        validateTokenResponse(response);
        OAuthToken token = response.as(OAuthToken.class);
        logger.info("OAuth token successfully received.");
        return token;
    }



    /**
     * Validates Spotify token endpoint response.
     *
     * @param response RestAssured response
     */
    private void validateTokenResponse(Response response) {

        Objects.requireNonNull(response, "Response cannot be null.");
        logger.info("Validating token response.");
        if (response.getStatusCode() != HttpStatus.SC_OK) {
            throw new OAuthException(String.format("Spotify OAuth token exchange failed. HTTP Status %d%nResponse:%n%s.",
                    response.getStatusCode(),
                    response.getBody().asPrettyString()));
        }
    }

    private Map<String, String> createFormParameters(){
        return new LinkedHashMap<>();
    }

    private void validateAuthorizationCode(String authorizationCode) {

        if (authorizationCode == null || authorizationCode.isBlank()) {

            throw new OAuthException(
                    "Authorization code cannot be null or blank.");
        }
    }

    private void validateRefreshToken(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {

            throw new OAuthException(
                    "Refresh token cannot be null or blank.");
        }
    }

}
