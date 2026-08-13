package api.client.spotify;

import api.client.BaseApiClient;
import api.constants.SpotifyEndPoints;
import api.models.spotify.request.*;
import exceptions.FrameworkException;
import io.restassured.response.Response;
import java.util.LinkedHashMap;
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
        if (request == null) {
            throw new FrameworkException(
                    "Create Playlist request cannot be null.");
        }

        return authenticatedPost(SpotifyEndPoints.CREATE_PLAYLIST, request);
    }

    public Response getPlaylist(String playlistId)
    {
        validatePlaylistId(playlistId);
        return authenticatedGetWithPathParams(SpotifyEndPoints.GET_PLAYLIST, Map.of("playlist_id", playlistId));
    }

    public Response updatePlaylist(UpdatePlaylistRequest request ,String playlistId){

        validatePlaylistId(playlistId);
        if (request == null) {
            throw new FrameworkException(
                    "Update Playlist request cannot be null.");
        }
        return authenticatedPutWithPathParams(SpotifyEndPoints.UPDATE_PLAYLIST, request, Map.of("playlist_id",playlistId));
    }

    public Response removePlaylistFromLibrary(String playlistId){

        validatePlaylistId(playlistId);
        String playlistUri = "spotify:playlist:" + playlistId;
        return authenticatedDelete(SpotifyEndPoints.REMOVE_FROM_LIBRARY, Map.of("uris", playlistUri));
    }

    public Response isPlaylistInCurrentUserLibrary(String playlistId){

        validatePlaylistId(playlistId);
        String playlistUri = "spotify:playlist:" + playlistId;
        return authenticatedGetWithQueryParams(SpotifyEndPoints.CHECK_LIBRARY_CONTAINS, Map.of("uris", playlistUri));
    }

    public Response addPlaylistItems(AddPlaylistItemsRequest request,String playlistId){

        validatePlaylistId(playlistId);
        if (request == null) {
            throw new FrameworkException(
                    "Add Playlist Items request cannot be null.");
        }
        return authenticatedPostWithPathParams(SpotifyEndPoints.ADD_PLAYLIST_ITEMS, request, Map.of("playlist_id",playlistId));
    }

    public Response getPlaylistItems(String playlistId, GetPlaylistItemsQueryParams queryParams){

        validatePlaylistId(playlistId);
        /*Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("market",market);
        queryParams.put("fields",fields);
        queryParams.put("limit",limit);
        queryParams.put("offset",offset);
        queryParams.put("additional_types",additionalTypes);*/
        Map<String,Object> queryParameters = buildGetPlaylistItemsQueryParams(queryParams);
        return authenticatedGetWithPathAndQueryParams(SpotifyEndPoints.GET_PLAYLIST_ITEMS, Map.of("playlist_id",playlistId),queryParameters);
    }

    public Response removePlaylistItems(RemovePlaylistItemsRequest request , String playlistId){

        validatePlaylistId(playlistId);
        if (request == null) {
            throw new FrameworkException(
                    "Remove Playlist Items request cannot be null.");
        }
        return authenticatedDeleteWithPathParamsAndBody(SpotifyEndPoints.REMOVE_PLAYLIST_ITEMS, request, Map.of("playlist_id",playlistId));
    }

    private Map<String, Object> buildGetPlaylistItemsQueryParams(GetPlaylistItemsQueryParams params) {

        if (params == null) {
            return Map.of();
        }

        Map<String, Object> queryParams = new LinkedHashMap<>();

        if (params.getMarket() != null && !params.getMarket().isBlank()) {
            queryParams.put("market", params.getMarket());
        }

        if (params.getFields() != null && !params.getFields().isBlank()) {
            queryParams.put("fields", params.getFields());
        }

        if (params.getLimit() != null) {
            queryParams.put("limit", params.getLimit());
        }

        if (params.getOffset() != null) {
            queryParams.put("offset", params.getOffset());
        }

        if (params.getAdditionalTypes() != null
                && !params.getAdditionalTypes().isBlank()) {
            queryParams.put("additional_types", params.getAdditionalTypes());
        }

        return queryParams;
    }


    private void validatePlaylistId(String playlistId){
        if(playlistId == null || playlistId.isBlank()){
            throw new FrameworkException("Playlist ID cannot be null or empty.");
        }
    }

}
