package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPlaylistItemsResponse {

    @JsonProperty("snapshot_id")
    private String snapshotId;

}
