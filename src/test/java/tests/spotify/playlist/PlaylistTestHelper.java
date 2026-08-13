package tests.spotify.playlist;

import api.client.spotify.SpotifyPlaylistApiClient;
import api.models.spotify.request.CreatePlaylistRequest;
import api.models.spotify.response.CreatePlaylistResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;

/**
 * Test-only helper for creating Spotify playlists required by playlist tests.
 *
 * <p>The helper centralizes the repeated test setup of creating a playlist,
 * validating the create response, extracting the playlist ID and registering
 * it for cleanup. Business assertions that are specific to an individual
 * test remain in the test method.</p>
 */
public final class PlaylistTestHelper {

    private static final Logger logger = LogManager.getLogger(PlaylistTestHelper.class);

    private PlaylistTestHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CreatePlaylistResponse createAndRegisterPlaylist(
            SpotifyPlaylistApiClient playlistApiClient,
            CreatePlaylistRequest request,
            ITestResult testResult,
            String cleanupAttributeKey) {

        Response createResponse = playlistApiClient.createPlaylist(request);

        logger.info("Create Playlist API Response:\n{}", createResponse.getBody().asPrettyString());

        Assert.assertEquals(createResponse.getStatusCode(), HttpStatus.SC_CREATED, "Create Playlist API should return HTTP 201."
        );

        CreatePlaylistResponse createdPlaylist = createResponse.as(CreatePlaylistResponse.class);

        String playlistId = createdPlaylist.getId();

        Assert.assertNotNull(playlistId, "Playlist ID should not be null."
        );

        testResult.setAttribute(cleanupAttributeKey, playlistId);

        logger.info("Created Playlist ID registered for cleanup: {}", playlistId);

        return createdPlaylist;
    }
}
