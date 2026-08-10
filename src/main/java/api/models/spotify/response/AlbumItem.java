package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumItem {

    private String id;

    private String name;

    private String uri;
}
