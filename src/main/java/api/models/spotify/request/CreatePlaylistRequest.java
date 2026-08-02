package api.models.spotify.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Request body for Spotify Create Playlist API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePlaylistRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("public")
    private Boolean isPublic;

    @JsonProperty("collaborative")
    private Boolean collaborative;

    @JsonProperty("description")
    private String description;

    public CreatePlaylistRequest() {
    }

    public CreatePlaylistRequest(String name,
                                 Boolean isPublic,
                                 Boolean collaborative,
                                 String description) {
        this.name = Objects.requireNonNull(name, "Playlist name cannot be null.");
        this.isPublic = isPublic;
        this.collaborative = collaborative;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Playlist name cannot be null.");;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CreatePlaylistRequest{" +
                "name='" + name + '\'' +
                ", public=" + isPublic +
                ", collaborative=" + collaborative +
                ", description='" + description + '\'' +
                '}';
    }


}
