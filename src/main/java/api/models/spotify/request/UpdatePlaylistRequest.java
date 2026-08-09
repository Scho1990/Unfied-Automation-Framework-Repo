package api.models.spotify.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for Spotify Update Playlist API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePlaylistRequest {

    private String name;

    @JsonProperty("public")
    private Boolean isPublic;

    private Boolean collaborative;

    private String description;
}
