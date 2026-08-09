package api.client.spotify;

import api.client.BaseApiClient;
import api.constants.SpotifyEndPoints;
import api.models.spotify.request.CreatePlaylistRequest;
import api.models.spotify.request.UpdatePlaylistRequest;
import exceptions.FrameworkException;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Objects;

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
        if (request == null) {
            throw new FrameworkException(
                    "Create Playlist request cannot be null.");
        }

        return authenticatedPost(SpotifyEndPoints.CREATE_PLAYLIST, request);
    }

    public Response getPlaylist(String playlistId)
    {
        if(playlistId == null || playlistId.isBlank()){
            throw new FrameworkException("Playlist ID cannot be null or empty.");
        }

        return authenticatedGetWithPathParams(SpotifyEndPoints.GET_PLAYLIST, Map.of("playlist_id", playlistId));
    }

    public Response updatePlaylist(UpdatePlaylistRequest request ,String playlistId){

        if(playlistId == null || playlistId.isBlank()){
            throw new FrameworkException("Playlist ID cannot be null or empty.");
        }

        if (request == null) {
            throw new FrameworkException(
                    "Update Playlist request cannot be null.");
        }
        return authenticatedPut(SpotifyEndPoints.UPDATE_PLAYLIST, request, Map.of("playlist_id",playlistId));
    }

    public Response removePlaylistFromLibrary(String playlistId){

        if(playlistId == null || playlistId.isBlank()){
            throw new FrameworkException("Playlist ID cannot be null or empty.");
        }

        String playlistUri = "spotify:playlist:" + playlistId;

        return authenticatedDelete(SpotifyEndPoints.REMOVE_FROM_LIBRARY,Map.of("uris", playlistUri));

    }

    public Response isPlaylistInCurrentUserLibrary(String playlistId){

        if(playlistId == null || playlistId.isBlank()){
            throw new FrameworkException("Playlist ID cannot be null or empty.");
        }

        String playlistUri = "spotify:playlist:" + playlistId;

        return authenticatedGetWithQueryParams(SpotifyEndPoints.CHECK_LIBRARY_CONTAINS ,Map.of("uris", playlistUri));

    }

}
