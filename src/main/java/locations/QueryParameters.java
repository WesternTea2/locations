package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryParameters {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String prefix;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String suffix;
}
