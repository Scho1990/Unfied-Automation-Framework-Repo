package testscripts.spotify;

import api.authentication.oauth.OAuthServiceFactory;
import api.authentication.oauth.OAuthToken;
import api.authentication.oauth.SpotifyOAuthService;
import api.client.spotify.SpotifyPlaylistApiClient;
import api.config.ApiConfig;
import api.manager.TokenManager;
import api.models.spotify.request.CreatePlaylistRequest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaylistsApiTest {
 private static final Logger logger = LogManager.getLogger(PlaylistsApiTest.class);
    @Test(description = "Verify user can create Spotify playlist")
    public void verifyCreatePlaylist() {
       SpotifyOAuthService oAuthService = OAuthServiceFactory.spotify();

      //  String authorizationUrl = oAuthService.getAuthorizationUrl();

        String authorizationCode = ApiConfig.getAuthorizationCode();

        logger.info("Authorization Code : {}",authorizationCode);
     //   logger.info("authorizationUrl : {}",authorizationUrl);

      //  WaitUtility.sleep(120);

        OAuthToken token = oAuthService.exchangeAuthorizationCode(authorizationCode);

        TokenManager.setToken(token);

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
