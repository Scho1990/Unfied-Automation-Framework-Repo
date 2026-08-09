package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePlaylistResponse {

    private String id;

    private String name;

    private String description;

    @JsonProperty("public")
    private Boolean isPublic;

    private Boolean collaborative;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    private String href;

    private String uri;
}
