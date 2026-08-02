package testscripts.spotify;

import api.authentication.oauth.OAuthServiceFactory;
import api.authentication.oauth.OAuthToken;
import api.authentication.oauth.SpotifyOAuthService;
import api.client.spotify.SpotifyPlaylistApiClient;
import api.config.ApiConfig;
import api.manager.TokenManager;
import api.models.spotify.request.CreatePlaylistRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtility;

import java.util.Scanner;

public class PlaylistsApiTest {
 private static final Logger logger = LogManager.getLogger(PlaylistsApiTest.class);
    @Test(description = "Verify user can create Spotify playlist", enabled = true)
    public void verifyCreatePlaylist() {
       SpotifyOAuthService oAuthService = OAuthServiceFactory.spotify();

      //  String authorizationUrl = oAuthService.getAuthorizationUrl();

        // Authorize manually once
        logger.info("--------------------------------");
        logger.info("Open below URL in browser");
       // logger.info(authorizationUrl);
        logger.info("--------------------------------");

        logger.info("Enter Authorization Code : ");

        String authorizationCode = ApiConfig.getAuthorizationCode();

        logger.info("Authorization Code : {}",authorizationCode);

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
        logger.info("createPlaylist Status Code : {}", response.statusCode());
        logger.info("createPlaylist Response Body : {}", response.asPrettyString());
        logger.info("createPlaylist Response Body Name Key Value : {}", response.jsonPath().getString("name"));
     logger.info("createPlaylist Response Body Description Key Value : {}", response.jsonPath().getString("description"));
     logger.info("createPlaylist Response Body public Key Value : {}", response.jsonPath().getBoolean("public"));

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

    @Test(enabled = false)
    public void directSpotifyCall() {

        SpotifyOAuthService oAuthService = OAuthServiceFactory.spotify();

       // String authorizationUrl = oAuthService.getAuthorizationUrl();

        // Authorize manually once
        logger.info("--------------------------------");
        logger.info("Open below URL in browser");
      //  logger.info(authorizationUrl);
        logger.info("--------------------------------");

        logger.info("Enter Authorization Code : ");

        String authorizationCode = ApiConfig.getAuthorizationCode();

        logger.info("Authorization Code : {}",authorizationCode);

        //  WaitUtility.sleep(120);

        OAuthToken token = oAuthService.exchangeAuthorizationCode(authorizationCode);

        TokenManager.setToken(token);

        Response response =
                RestAssured
                        .given()
                        .baseUri("https://api.spotify.com/v1")
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .header("Authorization",
                                "Bearer " + TokenManager.getAccessToken())
                        .body("""
                          {
                              "name":"Framework Test",
                              "public":false,
                              "description":"Test"
                          }
                          """)
                        .post("/me/playlists");

        response.prettyPrint();
    }
}
