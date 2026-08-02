package api.client.spotify;

import api.client.BaseApiClient;
import api.constants.SpotifyEndPoints;
import api.models.spotify.request.CreatePlaylistRequest;
import io.restassured.response.Response;

public class SpotifyPlaylistApiClient extends BaseApiClient
{
    /**
     * Creates a new Spotify playlist.
     *
     * @param request playlist request
     * @return API response
     */
    public Response createPlaylist(CreatePlaylistRequest request)
    {
        return authenticatedPost(SpotifyEndPoints.PLAYLISTS, request);
    }

}
