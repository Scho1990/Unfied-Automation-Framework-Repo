package api.models.spotify.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request body for Spotify Remove Playlist Items API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemovePlaylistItemsRequest {

    private List<RemovePlaylistItem> items;

    @JsonProperty("snapshot_id")
    private String snapshotId;
}
