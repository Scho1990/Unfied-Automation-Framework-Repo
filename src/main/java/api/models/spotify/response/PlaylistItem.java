package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistItem {

    @JsonProperty("added_at")
    private String addedAt;

    @JsonProperty("added_by")
    private PlaylistItemAddedBy addedBy;

    @JsonProperty("is_local")
    private String isLocal;

    @JsonProperty("primary_color")
    private String primaryColor;

    private TrackItem item;
}
