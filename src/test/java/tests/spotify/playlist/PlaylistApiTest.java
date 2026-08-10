package tests.spotify.playlist;

import api.client.spotify.SpotifyPlaylistApiClient;
import api.models.spotify.request.*;
import api.models.spotify.response.*;
import api.spotify.PlaylistTestDataFactory;
import base.BaseApiTest;
import io.restassured.response.Response;
import org.apache.commons.math3.analysis.function.Add;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistApiTest extends BaseApiTest {

 private static final Logger logger = LogManager.getLogger(PlaylistApiTest.class);

 private SpotifyPlaylistApiClient playlistApiClient;

 //for cleanup newly created id's
 private static final String CREATED_PLAYLIST_ID = "createdPlaylistId";

 @BeforeClass(alwaysRun = true)
 public void setUp(){
     playlistApiClient = new SpotifyPlaylistApiClient();
 }

   // @Test(description = "Verify authenticated user can create a private Spotify playlist.")
    public void verifyCreatePlaylist() {

        // Arrange - Create a playlist for this test
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.privatePlaylist();

        //Act
        Response response = playlistApiClient.createPlaylist(createRequest);

        logger.info("Create Playlist API Response:\n{}", response.getBody().asPrettyString());

        //Assert
        Assert.assertEquals(
                response.getStatusCode(),
                HttpStatus.SC_CREATED,
                "Create Playlist API should return HTTP 201."
        );

        //Deserialize
        CreatePlaylistResponse createPlaylistResponse = response.as(CreatePlaylistResponse.class);

        String playlistId = createPlaylistResponse.getId();

        logger.info("Playlist ID: {}", playlistId);

        //Register for cleanup
        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        Assert.assertNotNull(playlistId,"Playlist ID should not be null.");

        Assert.assertEquals(
                createPlaylistResponse.getName(),
                createRequest.getName(),
                "Playlist Name does not match."
        );

        Assert.assertEquals(
                createPlaylistResponse.getDescription(),
                createRequest.getDescription(),
                "Playlist description does not match."
        );

        Assert.assertEquals(
                createPlaylistResponse.getCollaborative(),
                createRequest.getCollaborative(),
                "Playlist collaborative flag does not match."
        );

        Assert.assertNotNull(createPlaylistResponse.getSnapshotId(),"Snapshot ID should not be null.");

    }


   // @Test(description = "Verify authenticated user can retrieve an existing Spotify playlist.")
    public void verifyGetPlaylist() {

        // Arrange - Create a playlist for this test
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.privatePlaylist();

        Response createResponse = playlistApiClient.createPlaylist(createRequest);

        Assert.assertEquals(createResponse.getStatusCode(),HttpStatus.SC_CREATED,"Create Playlist API should return HTTP 201.");

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        //Register for cleanup
        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        Assert.assertNotNull(playlistId,"Playlist ID should not be null.");

        logger.info("Created Playlist ID for Get Playlist test: {}", playlistId);

        // Act - Get Playlist
        Response getResponse = playlistApiClient.getPlaylist(playlistId);

        logger.info("Get Playlist API Response:\n{}", getResponse.getBody().asPrettyString());

        Assert.assertEquals(
                getResponse.getStatusCode(), HttpStatus.SC_OK, "Get Playlist API should return HTTP 200.");

        // Deserialize response
        GetPlaylistResponse playlistResponse = getResponse.getBody().as(GetPlaylistResponse.class);

        // Assert - Business validations
        Assert.assertEquals(
                playlistResponse.getId(), playlistId,"Playlist ID does not match.");

        Assert.assertEquals(
                playlistResponse.getName(), createRequest.getName(),"Playlist Name does not match.");

        Assert.assertEquals(
                playlistResponse.getDescription(), createRequest.getDescription(),"Playlist Description does not match.");

        Assert.assertEquals(
                playlistResponse.getCollaborative(),createRequest.getCollaborative(),"Collaborative flag does not match.");

        logger.info("Get Playlist validation completed successfully for Playlist ID: {}", playlistId);

    }

   // @Test(description = "Verify authenticated user can update an existing Spotify playlist.")
    public void verifyUpdatePlaylist() {
        // Arrange - Create a playlist for this test
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.privatePlaylist();

        Response createResponse = playlistApiClient.createPlaylist(createRequest);

        Assert.assertEquals(createResponse.getStatusCode(),HttpStatus.SC_CREATED,"Create Playlist API should return HTTP 201.");

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        //Register for cleanup
        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        Assert.assertNotNull(playlistId,"Playlist ID should not be null.");

        logger.info("Created Playlist ID for Update Playlist test: {}", playlistId);

        // Arrange - Update request
        UpdatePlaylistRequest updateRequest = PlaylistTestDataFactory.updatePlaylist();

        // Act - Update Playlist
        Response updateResponse = playlistApiClient.updatePlaylist(updateRequest, playlistId);

        logger.info("Update Playlist API Response:\n{}", updateResponse.getBody().asPrettyString());

        // Assert - Update response
        Assert.assertEquals(updateResponse.getStatusCode(),HttpStatus.SC_OK,"Update Playlist API should return HTTP 200.");

        // Act - Get updated playlist
        Response getResponse = playlistApiClient.getPlaylist(playlistId);

        logger.info("Get Updated Playlist API Response:\n{}", getResponse.getBody().asPrettyString());

        Assert.assertEquals(
                getResponse.getStatusCode(), HttpStatus.SC_OK, "Get Playlist API should return HTTP 200.");

        // Deserialize GET response
        GetPlaylistResponse getPlaylistResponse = getResponse.getBody().as(GetPlaylistResponse.class);

        // Assert - Business validations
        Assert.assertEquals(
                getPlaylistResponse.getId(), playlistId,"Playlist ID does not match.");

        Assert.assertEquals(
                getPlaylistResponse.getName(), updateRequest.getName(),"Playlist Name does not match.");

        Assert.assertEquals(
                getPlaylistResponse.getDescription(), updateRequest.getDescription(),"Playlist Description does not match.");

        logger.info("Update Playlist validation completed successfully for Playlist ID: {}", playlistId);
    }

   // @Test(description = "Verify authenticated user can remove a playlist from the library.")
    public void verifyDeletePlaylist() {
        // Arrange - Create playlist
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.publicPlaylist();

        Response createResponse = playlistApiClient.createPlaylist(createRequest);

        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_CREATED, "Create Playlist API should return HTTP 201."
        );

        logger.info("Create Playlist API Response:\n{}", createResponse.getBody().asPrettyString());

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        //Register for cleanup
        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        Assert.assertNotNull(playlistId, "Playlist ID should not be null.");

        logger.info("Created Playlist ID for Delete Playlist test: {}", playlistId);

        Response checkPlaylistResponse = playlistApiClient.isPlaylistInCurrentUserLibrary(playlistId);

        logger.info("Check User's Playlist API Response:\n{}", checkPlaylistResponse.getBody().asPrettyString());

        Assert.assertEquals(checkPlaylistResponse.getStatusCode(), HttpStatus.SC_OK, "Check Library Contains API should return HTTP 200."
        );

        List<Boolean> beforeDelete = checkPlaylistResponse.jsonPath().getList("", Boolean.class);

        Assert.assertTrue(beforeDelete.get(0), "Playlist should exist in user's library before deletion."
        );

        // Act - Remove playlist from user's library
        Response deleteResponse =playlistApiClient.removePlaylistFromLibrary(playlistId);

        logger.info("Remove Playlist response status: {}", deleteResponse.getStatusCode());

        // Assert
        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK, "Remove Playlist from Library API should return HTTP 200.");

        // Act - Check whether playlist is still in user's library
        Response containsResponse = playlistApiClient.isPlaylistInCurrentUserLibrary(playlistId);

        logger.info("Check Library Contains Response:\n{}", containsResponse.getBody().asPrettyString());

        // Assert - Contains response
        Assert.assertEquals(containsResponse.getStatusCode(), HttpStatus.SC_OK, "Check Library Contains API should return HTTP 200.");

        List<Boolean> libraryStatus = containsResponse.jsonPath().getList("", Boolean.class);

        Assert.assertNotNull(libraryStatus, "Library contains response should not be null.");

        Assert.assertEquals(libraryStatus.size(), 1, "Expected exactly one library status.");

        Assert.assertFalse(libraryStatus.get(0), "Playlist should no longer exist in the user's library.");

        logger.info("Playlist [{}] successfully removed from user's library.", playlistId);

    }

   // @Test(description = "Verify authenticated user can add items to a Spotify playlist.")
    public void verifyAddPlaylistItems() {
        // Arrange - Create playlist
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.privatePlaylist();
        Response createResponse = playlistApiClient.createPlaylist(createRequest);
        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_CREATED, "Create Playlist API should return HTTP 201."
        );

        logger.info("Create Playlist API Response:\n{}", createResponse.getBody().asPrettyString());

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        Assert.assertNotNull(playlistId, "Playlist ID should not be null.");

        //Register playlist for cleanup
        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        logger.info("Created Playlist ID for Add Playlist RemovePlaylistItem test: {}", playlistId);

        //Arrange - Add playlist items request
        List<String> uris = List.of(
                "spotify:track:4iV5W9uYEdYUVa79Axb7Rh",
                "spotify:track:1301WleyT98MSxVHPZCA6M"
        );

        AddPlaylistItemsRequest addItemsRequest = AddPlaylistItemsRequest.builder()
                .uris(uris)
                .position(0)
                .build();

        //Act - Add playlist items response
        Response addItemsResponse = playlistApiClient.addPlaylistItems(addItemsRequest, playlistId);

        logger.info("Add Playlist RemovePlaylistItem Response:\n{}", addItemsResponse.getBody().asPrettyString());

        // Assert - HTTP
        Assert.assertEquals(addItemsResponse.getStatusCode(), HttpStatus.SC_CREATED, "Add Playlist RemovePlaylistItem API should return HTTP 201.");

        //Deserialize response
        AddPlaylistItemsResponse response = addItemsResponse.as(AddPlaylistItemsResponse.class);

        //Assert - Business
        Assert.assertNotNull(response.getSnapshotId(), "Snapshot ID should not be null.");

        Assert.assertFalse(response.getSnapshotId().isBlank(), "Snapshot ID should not be blank.");

        logger.info("RemovePlaylistItem added successfully. Snapshot ID: {}", response.getSnapshotId());

    }

    @Test(description = "Verify authenticated user can retrieve playlist items with nested response details.")
    public void verifyGetPlaylistItems() {
        //Arrange - Create Playlist
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.privatePlaylist();

        Response createResponse = playlistApiClient.createPlaylist(createRequest);

        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_CREATED, "Create Playlist API should return HTTP 201.");

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        Assert.assertNotNull(playlistId, "Playlist ID should not be null.");

        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        logger.info("Created Playlist ID for Get Playlist RemovePlaylistItem test: {}", playlistId);
        // Arrange - Add playlist items
        List<String> uris = List.of(
                "spotify:track:4iV5W9uYEdYUVa79Axb7Rh",
                "spotify:track:1301WleyT98MSxVHPZCA6M"
        );

        AddPlaylistItemsRequest addItemsRequest = AddPlaylistItemsRequest.builder()
                .uris(uris)
                .position(0)
                .build();

        Response addItemsResponse = playlistApiClient.addPlaylistItems(addItemsRequest, playlistId);

        Assert.assertEquals(addItemsResponse.getStatusCode(), HttpStatus.SC_CREATED, "Add Playlist RemovePlaylistItem API should return HTTP 201.");

        //Act - Get Playlist items
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("market","IS");
        queryParams.put("limit",10);

        Response getItemsResponse = playlistApiClient.getPlaylistItems(playlistId, queryParams);

        logger.info("Get Playlist RemovePlaylistItem Response:\n{}", getItemsResponse.getBody().asPrettyString());

        //Assert - HTTP
        Assert.assertEquals(getItemsResponse.getStatusCode(), HttpStatus.SC_OK, "Get Playlist RemovePlaylistItem API should return HTTP 200.");

        //Deserialize nested response
        GetPlaylistItemsResponse playlistItemsResponse = getItemsResponse.as(GetPlaylistItemsResponse.class);

        //Top-Level assertions
        Assert.assertNotNull(playlistItemsResponse.getItems(),"Playlist RemovePlaylistItem should not be null.");

        Assert.assertEquals(playlistItemsResponse.getTotal(),2,"Playlist should contain two items.");

        Assert.assertEquals(playlistItemsResponse.getItems().size(),2,"Expected two playlist items in the response.");

        //First nested item
        PlaylistItem firstItem = playlistItemsResponse.getItems().get(0);

        Assert.assertNotNull(firstItem.getAddedAt(),"Added timestamp should not be null.");

        Assert.assertNotNull(firstItem.getAddedBy(),"AddedBy Object should not be null.");

        Assert.assertNotNull(firstItem.getAddedBy().getId(),"AddedBy User ID should not be null.");

        Assert.assertNotNull(firstItem.getAddedBy().getExternalUrls(),"AddedBy external URLs should not be null.");

        Assert.assertNotNull(firstItem.getAddedBy().getExternalUrls().getSpotify(),"AddedBy Spotify URL should not be null.");

        // Nested track assertions
        Assert.assertNotNull(
                firstItem.getItem(),
                "Track item should not be null."
        );

        Assert.assertNotNull(
                firstItem.getItem().getId(),
                "Track ID should not be null."
        );

        Assert.assertNotNull(
                firstItem.getItem().getName(),
                "Track name should not be null."
        );

        Assert.assertNotNull(
                firstItem.getItem().getUri(),
                "Track URI should not be null."
        );

        // Nested album assertions
        Assert.assertNotNull(
                firstItem.getItem().getAlbum(),
                "Album should not be null."
        );

        Assert.assertNotNull(
                firstItem.getItem()
                        .getAlbum()
                        .getId(),
                "Album ID should not be null."
        );

        Assert.assertNotNull(
                firstItem.getItem()
                        .getAlbum()
                        .getName(),
                "Album name should not be null."
        );

        Assert.assertNotNull(
                firstItem.getItem()
                        .getAlbum()
                        .getUri(),
                "Album URI should not be null."
        );

        logger.info(
                "Nested playlist item validation completed successfully for Playlist ID: {}",
                playlistId
        );


    }

    @Test(description = "Verify authenticated user can remove playlist items from a Spotify playlist.")
    public void verifyRemovePlaylistItems() {
        //Arrange - Create Playlist
        CreatePlaylistRequest createRequest = PlaylistTestDataFactory.privatePlaylist();

        Response createResponse = playlistApiClient.createPlaylist(createRequest);

        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_CREATED, "Create Playlist API should return HTTP 201.");

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        Assert.assertNotNull(playlistId, "Playlist ID should not be null.");

        Reporter.getCurrentTestResult().setAttribute(CREATED_PLAYLIST_ID, playlistId);

        logger.info("Created Playlist ID for Remove Playlist Remove Playlist Items test: {}", playlistId);

        // Arrange - Add two playlist items
        List<String> uris = List.of(
                "spotify:track:4iV5W9uYEdYUVa79Axb7Rh",
                "spotify:track:1301WleyT98MSxVHPZCA6M"
        );

        AddPlaylistItemsRequest addItemsRequest = AddPlaylistItemsRequest.builder()
                .uris(uris)
                .position(0)
                .build();

        Response addItemsResponse = playlistApiClient.addPlaylistItems(addItemsRequest, playlistId);

        Assert.assertEquals(addItemsResponse.getStatusCode(), HttpStatus.SC_CREATED, "Add Playlist Remove Playlist Item API should return HTTP 201.");

        AddPlaylistItemsResponse addResponse = addItemsResponse.as(AddPlaylistItemsResponse.class);

        String snapshotId = addResponse.getSnapshotId();

        Assert.assertNotNull(snapshotId, "Snapshot ID should not be null after adding items."
        );

        logger.info("Snapshot ID after adding items: {}", snapshotId);


        //Act - Get Playlist items
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("market","IS");
        queryParams.put("limit",10);

        // Act - Remove first item
        RemovePlaylistItem items = RemovePlaylistItem.builder()
                .uri(uris.getFirst())
                .build();

        RemovePlaylistItemsRequest removeItemsRequest = RemovePlaylistItemsRequest.builder()
                .items(List.of(items))
                .snapshotId(snapshotId)
                .build();

        Response removeResponse = playlistApiClient.removePlaylistItems(removeItemsRequest, playlistId);

        logger.info("Remove Playlist Items Response:\n{}", removeResponse.getBody().asPrettyString());

        logger.info("Remove playlist item validation completed successfully for Playlist ID: {}", playlistId);

        // Assert - HTTP status
        Assert.assertEquals(
                removeResponse.getStatusCode(),
                HttpStatus.SC_OK,
                "Remove Playlist Items API should return HTTP 200."
        );

        // Deserialize response
        RemovePlaylistItemsResponse removeItemsResponse =
                removeResponse.as(RemovePlaylistItemsResponse.class);

        // Assert - new snapshot ID
        Assert.assertNotNull(
                removeItemsResponse.getSnapshotId(),
                "Snapshot ID should not be null after removing items."
        );

        Assert.assertFalse(
                removeItemsResponse.getSnapshotId().isBlank(),
                "Snapshot ID should not be blank."
        );

        Assert.assertNotEquals(
                removeItemsResponse.getSnapshotId(),
                snapshotId,
                "Snapshot ID should change after playlist modification."
        );

        logger.info(
                "New Snapshot ID after removal: {}",
                removeItemsResponse.getSnapshotId()
        );


    }

    @AfterMethod(alwaysRun = true)
    public void cleanupPlaylist(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Object playlistIdObject = result.getAttribute(CREATED_PLAYLIST_ID);

        if (playlistIdObject == null) {
            logger.info("No playlist created by this test. Cleanup skipped for test {}.", testName);
            return;
        }

        String playlistId = playlistIdObject.toString();

        logger.info("Starting cleanup for test-created playlist: {} for test {}.", playlistId,testName);

        try {
            Response containsResponse = playlistApiClient.isPlaylistInCurrentUserLibrary(playlistId);

            if (containsResponse.getStatusCode() != HttpStatus.SC_OK) {

                logger.warn("Unable to determine library status for playlist [{}] for test {}. Cleanup response status: {}", playlistId, testName, containsResponse.getStatusCode());

                return;
            }

            List<Boolean> libraryStatus = containsResponse.jsonPath().getList("", Boolean.class);

            if (libraryStatus.isEmpty()) {
                logger.warn("Library contains empty response for playlist [{}] for test {}. Cleanup response status: {}", playlistId, testName, containsResponse.getStatusCode());

                return;
            }

            if (libraryStatus.size() != 1) {
                logger.warn("Expected exactly one library status for playlist [{}] for test {}, but received {}.", playlistId, testName, libraryStatus.size());
                return;
            }

            boolean playlistExists = Boolean.TRUE.equals(libraryStatus.get(0));

            if (!playlistExists) {
                logger.info("Playlist [{}] already removed for test {}. Cleanup not required.", playlistId, testName);

                return;
            }

            Response deleteResponse =playlistApiClient.removePlaylistFromLibrary(playlistId);

            if (deleteResponse.getStatusCode() == HttpStatus.SC_OK) {
                logger.info("Playlist [{}] successfully removed for test {} during cleanup.", playlistId, testName);
            }
            else {
                logger.error("Failed to remove playlist [{}] for test {}. HTTP Status: {}", playlistId, testName, deleteResponse.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Unexpected error while cleaning playlist [{}] for test {}.", playlistId, testName, e);
        }
    }
}
