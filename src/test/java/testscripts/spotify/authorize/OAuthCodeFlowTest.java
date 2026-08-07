package testscripts.spotify.authorize;

import api.authentication.oauth.manager.TokenManager;
import api.authentication.oauth.model.OAuthToken;
import api.authentication.oauth.service.OAuthServiceFactory;
import api.authentication.oauth.service.SpotifyOAuthService;
import api.config.ApiConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class OAuthCodeFlowTest {
    private static final Logger logger = LogManager.getLogger(OAuthCodeFlowTest.class);

    @Test(description = "Get the authorization code and update the oauth/token.json with new refresh and access token")
    public void verifyCreatePlaylist() {
        SpotifyOAuthService oAuthService = OAuthServiceFactory.createSpotifyOAuthService();

        String authorizationUrl = oAuthService.getAuthorizationUrl();

        String authorizationCode = ApiConfig.getAuthorizationCode();

        logger.info("Authorization Code : {}", authorizationCode);
        logger.info("authorizationUrl : {}", authorizationUrl);

        OAuthToken token = oAuthService.exchangeAuthorizationCode(authorizationCode);

        TokenManager.storeToken(token);
    }
}
