package api.models.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPlaylistItemsResponse {

    private String href;
    private List<PlaylistItem> items;
    private Integer limit;
    private String next;
    private String previous;
    private Integer offset;
    private Integer total;
}
