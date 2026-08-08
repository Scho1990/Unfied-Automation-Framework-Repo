package api.client.spotify;

import api.client.BaseApiClient;
import api.constants.SpotifyEndPoints;
import api.models.spotify.request.CreatePlaylistRequest;
import exceptions.FrameworkException;
import io.restassured.response.Response;

import java.util.Map;

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
        return authenticatedPost(SpotifyEndPoints.CREATE_PLAYLIST, request);
    }

    public Response getPlaylist(String playlistId)
    {
        if(playlistId == null || playlistId.isEmpty()){
            throw new FrameworkException("Playlist ID cannot be null or empty.");
        }

        return authenticatedGet(SpotifyEndPoints.GET_PLAYLIST, Map.of("playlist_id", playlistId));
    }

}
