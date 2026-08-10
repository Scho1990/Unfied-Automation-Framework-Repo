package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistItemAddedBy {

    private String id;

    private String href;

    private String type;

    private String uri;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
