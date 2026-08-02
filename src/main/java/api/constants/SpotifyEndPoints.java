package api.constants;

/**
 * Spotify Web API endpoints.
 */
public final class SpotifyEndPoints {

    private SpotifyEndPoints() {
        throw new UnsupportedOperationException("Utility class.");
    }

    /**
     * Current user profile.
     */
    public static final String CURRENT_USER = "/me";

    /**
     * Create playlist for current user.
     */
    public static final String PLAYLISTS = "/me/playlists";
}
