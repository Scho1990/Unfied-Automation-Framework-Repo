package testscripts.spotify;

import api.client.spotify.SpotifyPlaylistApiClient;
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

   TokenManager.initialize();

 }
    @Test(description = "Verify user can create Spotify playlist", invocationCount = 30, threadPoolSize =  5, timeOut = 60000)
    public void verifyCreatePlaylist() {

        SpotifyPlaylistApiClient playlistApiClient = new SpotifyPlaylistApiClient();

        CreatePlaylistRequest request = new CreatePlaylistRequest(
                "UAF Santosh Automation Playlist"+ System.currentTimeMillis(),
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
