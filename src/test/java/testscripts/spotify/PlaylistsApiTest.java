package testscripts.spotify;

import api.authentication.oauth.service.OAuthServiceFactory;
import api.authentication.oauth.service.SpotifyOAuthService;
import api.authentication.oauth.model.OAuthToken;
import api.client.spotify.SpotifyPlaylistApiClient;
import api.config.ApiConfig;
import api.authentication.oauth.manager.TokenManager;
import api.models.spotify.request.CreatePlaylistRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class PlaylistsApiTest {
 private static final Logger logger = LogManager.getLogger(PlaylistsApiTest.class);

 @BeforeSuite(alwaysRun = true)
 public void initializeSpotifyToken() {
  SpotifyOAuthService service = OAuthServiceFactory.createSpotifyOAuthService();

  OAuthToken token = service.refreshAccessToken(
          ApiConfig.getSpotifyRefreshToken()
  );

  TokenManager.storeToken(token);
 }
    @Test(description = "Verify user can create Spotify playlist")
    public void verifyCreatePlaylist() {
       SpotifyOAuthService oAuthService = OAuthServiceFactory.createSpotifyOAuthService();

        String authorizationUrl = oAuthService.getAuthorizationUrl();

        String authorizationCode = ApiConfig.getAuthorizationCode();

        logger.info("Authorization Code : {}",authorizationCode);
        logger.info("authorizationUrl : {}",authorizationUrl);

      //  WaitUtility.sleep(120);

        OAuthToken token = oAuthService.exchangeAuthorizationCode(authorizationCode);

        TokenManager.storeToken(token);

        SpotifyPlaylistApiClient playlistApiClient = new SpotifyPlaylistApiClient();

        CreatePlaylistRequest request = new CreatePlaylistRequest(
                "UAF Automation Playlist"+ System.currentTimeMillis(),
                false,
                false,
                "Created by UAF Automation Framework");

        Response response = playlistApiClient.createPlaylist(request);

        Assert.assertEquals(
                response.statusCode(),
                HttpStatus.SC_CREATED,
                "Playlist creation failed."
        );

        Assert.assertEquals(
                response.jsonPath().getString("name"),
                request.getName()
        );

        Assert.assertEquals(
                response.jsonPath().getString("description"),
                request.getDescription()
        );

        Assert.assertFalse(
                response.jsonPath().getBoolean("public")
        );

    }
}
