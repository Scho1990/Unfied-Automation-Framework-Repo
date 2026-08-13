package tests.spotify.playlist;

import api.client.spotify.SpotifyPlaylistApiClient;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import java.util.List;

/**
 * Test-only cleanup component for Spotify playlists created by a test.
 * Cleanup is intentionally best-effort so that cleanup failures do not mask
 * the original test result.
 */
public final class PlaylistCleanupManager {

    private static final Logger logger = LogManager.getLogger(PlaylistCleanupManager.class);

    private final SpotifyPlaylistApiClient playlistApiClient;
    private final String cleanupAttributeKey;

    public PlaylistCleanupManager(SpotifyPlaylistApiClient playlistApiClient, String cleanupAttributeKey) {
        this.playlistApiClient = playlistApiClient;
        this.cleanupAttributeKey = cleanupAttributeKey;
    }

    public void cleanup(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Object playlistIdObject = result.getAttribute(cleanupAttributeKey);

        if (playlistIdObject == null) {
            logger.info("No playlist created by this test. Cleanup skipped for test {}.", testName);
            return;
        }

        String playlistId = playlistIdObject.toString();

        logger.info("Starting cleanup for test-created playlist: {} for test {}.", playlistId, testName);

        try {
            Response containsResponse = playlistApiClient.isPlaylistInCurrentUserLibrary(playlistId);

            if (containsResponse.getStatusCode() != HttpStatus.SC_OK) {
                logger.warn("Unable to determine library status for playlist [{}] for test {}. " + "Cleanup response status: {}", playlistId, testName, containsResponse.getStatusCode());
                return;
            }

            List<Boolean> libraryStatus = containsResponse.jsonPath().getList("", Boolean.class);

            if (libraryStatus.isEmpty()) {
                logger.warn("Library contains returned an empty response for playlist [{}] for test {}.", playlistId, testName);
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

            Response deleteResponse = playlistApiClient.removePlaylistFromLibrary(playlistId);

            if (deleteResponse.getStatusCode() == HttpStatus.SC_OK) {
                logger.info("Playlist [{}] successfully removed for test {} during cleanup.", playlistId, testName);
            } else {
                logger.error("Failed to remove playlist [{}] for test {}. HTTP Status: {}", playlistId, testName, deleteResponse.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Unexpected error while cleaning playlist [{}] for test {}.", playlistId, testName, e);
        }
    }
}
