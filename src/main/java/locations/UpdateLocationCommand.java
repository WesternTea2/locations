package locations;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLocationCommand {

    @NotBlank(message = "Name can not be blank")
    private String name;

    @Coordinate(type = Coordinate.Type.LAT, message = "Latitude must be between -90 and 90")
    private double latitude;

    @Coordinate(type = Coordinate.Type.LON, message = "Longitude must be between -180 and 180")
    private double longitude;
}
