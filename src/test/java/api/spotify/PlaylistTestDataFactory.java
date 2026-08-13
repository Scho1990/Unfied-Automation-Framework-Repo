package api.spotify;

import api.models.spotify.request.CreatePlaylistRequest;
import api.models.spotify.request.UpdatePlaylistRequest;

public final class PlaylistTestDataFactory {

    private PlaylistTestDataFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CreatePlaylistRequest privatePlaylist() {
        return CreatePlaylistRequest.builder()
                .name("Automation Playlist" + System.currentTimeMillis())
                .isPublic(false)
                .collaborative(false)
                .description("Created by API automation test.")
                .build();
    }

    public static CreatePlaylistRequest publicPlaylist() {
        return CreatePlaylistRequest.builder()
                .name("Automation Playlist" + System.currentTimeMillis())
                .isPublic(true)
                .collaborative(false)
                .description("Created by API automation test.")
                .build();
    }

    public static UpdatePlaylistRequest updatePlaylist() {
        return UpdatePlaylistRequest.builder()
                .name("Updated Automation Playlist" + System.currentTimeMillis())
                .isPublic(false)
                .description("Updated Playlist description")
                .build();
    }
}
