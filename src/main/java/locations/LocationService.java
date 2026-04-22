package locations;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {

    private List<Location> locations = Arrays.asList(
            new Location("Budapest", 47.497912, 19.040235),
            new Location("Catania", 41.497912, 14.040235),
            new Location("Taormina", 40.497912, 14.040235)
    );

    public List<Location> getLocations() {
        return locations;
    }
}
