package locations;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationDto> getLocations(@RequestParam Optional<String> prefix) {
        return locationService.getLocations(prefix);
    }

    @GetMapping("/{id}")
    public LocationDto getLocationsById(@PathVariable("id") long id) {
        return locationService.getLocationById(id);
    }
}
