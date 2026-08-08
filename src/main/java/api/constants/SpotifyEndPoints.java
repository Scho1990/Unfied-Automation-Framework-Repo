package api.constants;

/**
 * Spotify Web API endpoints.
 */
public final class SpotifyEndPoints {

    private SpotifyEndPoints() {
        throw new UnsupportedOperationException("Utility class.");
    }

    /*
     * Playlist Endpoints
     */
    public static final String CREATE_PLAYLIST = "/me/playlists";
    public static final String GET_PLAYLIST = "/playlists/{playlist_id}";
}
