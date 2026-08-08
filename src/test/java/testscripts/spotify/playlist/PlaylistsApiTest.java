package testscripts.spotify.playlist;

import api.client.spotify.SpotifyPlaylistApiClient;
import api.authentication.oauth.manager.TokenManager;
import api.models.spotify.request.CreatePlaylistRequest;
import api.models.spotify.response.GetPlaylistResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class PlaylistsApiTest {
 private static final Logger logger = LogManager.getLogger(PlaylistsApiTest.class);
 SpotifyPlaylistApiClient playlistApiClient;

 @BeforeSuite(alwaysRun = true)
 public void initializeSpotifyToken() {

   TokenManager.initialize();

 }

 @BeforeClass(alwaysRun = true)
 public void setUP(){
     playlistApiClient = new SpotifyPlaylistApiClient();
 }

    @Test(description = "Verify user can create Spotify playlist", invocationCount = 1, threadPoolSize =  1, timeOut = 60000,enabled = false)
    public void verifyCreatePlaylist() {

        //Arrange
        CreatePlaylistRequest createRequest = new CreatePlaylistRequest(
                "UAF Santosh Automation Playlist"+ System.currentTimeMillis(),
                false,
                false,
                "Created by UAF Automation Framework");

        /*CreatePlaylistRequest createRequest = CreatePlaylistRequest.builder()
                .name("Automation Playlist" + System.currentTimeMillis())
                .isPublic(false)
                .collaborative(false)
                .description("Created for Get Playlist API validation.")
                .build();*/

        //Create Playlist
        Response createResponse = playlistApiClient.createPlaylist(createRequest);

       // logger.info("Response: "+response.getBody().asPrettyString());
        logger.info("Playlist ID: "+createResponse.jsonPath().getString("id"));

        Assert.assertEquals(
                createResponse.getStatusCode(),
                HttpStatus.SC_CREATED,
                "Playlist creation failed."
        );

        Assert.assertEquals(
                createResponse.jsonPath().getString("name"),
                createRequest.getName()
        );

        Assert.assertEquals(
                createResponse.jsonPath().getString("description"),
                createRequest.getDescription()
        );

        Assert.assertFalse(
                createResponse.jsonPath().getBoolean("public")
        );

    }

    @Test(description = "Verify user is able to get the Spotify playlist")
    public void verifyGetPlaylist() {
        SpotifyPlaylistApiClient playlistApiClient = new SpotifyPlaylistApiClient();

        CreatePlaylistRequest request = new CreatePlaylistRequest(
                "UAF Santosh Automation Playlist"+ System.currentTimeMillis(),
                false,
                false,
                "Created by UAF Automation Framework");

        Response createResponse = playlistApiClient.createPlaylist(request);

        logger.info("Playlist ID: "+createResponse.jsonPath().getString("id"));

        String playlist_id = createResponse.jsonPath().getString("id");

        Response getResponse = playlistApiClient.getPlaylist(playlist_id);
        logger.info("Get Response: "+getResponse.getBody().asPrettyString());
       // logger.info("Playlist ID: "+response.jsonPath().getString("id"));

        GetPlaylistResponse playlistResponse = getResponse.getBody().as(GetPlaylistResponse.class);

        Assert.assertEquals(
                getResponse.statusCode(), HttpStatus.SC_OK, "Get Playlist failed."
        );

        Assert.assertEquals(
                createResponse.jsonPath().getString("id"), playlist_id,"Playlist ID does not match.");

        Assert.assertEquals(
                playlistResponse.getName(), request.getName(),"Name does not match.");

        Assert.assertEquals(
                playlistResponse.getDescription(), request.getDescription(),"Description does not match.");

        Assert.assertTrue(
                playlistResponse.getCollaborative().equals(request.getCollaborative()),"Description does not match.");

    }
}
