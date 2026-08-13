package api.models.spotify.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPlaylistItemsQueryParams {

    private String market;

    private String fields;

    private Integer limit;

    private Integer offset;

    private String additionalTypes;
}
