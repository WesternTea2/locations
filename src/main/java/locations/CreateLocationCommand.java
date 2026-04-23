package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationCommand {

    @Schema(description = "name of city", example = "Budapest")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Coordinate(type = Coordinate.Type.LAT, message = "Latitude must be between -90 and 90")
    private double latitude;

    @Coordinate(type = Coordinate.Type.LON, message = "Longitude must be between -180 and 180")
    private double longitude;
}
