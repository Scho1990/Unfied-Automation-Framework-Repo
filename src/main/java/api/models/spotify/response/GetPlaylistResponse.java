package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPlaylistResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("public")
    private Boolean isPublic;

    @JsonProperty("collaborative")
    private Boolean collaborative;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    @JsonProperty("uri")
    private String uri;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public String getUri() {
        return uri;
    }
}
