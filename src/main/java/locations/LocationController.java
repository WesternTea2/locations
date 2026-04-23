package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

//    @GetMapping
//    public List<LocationDto> getLocations(@RequestParam Optional<String> prefix) {
//        return locationService.getLocations(prefix);
//    }
//
//    @GetMapping("/{id}")
//    public LocationDto getLocationsById(@PathVariable("id") long id) {
//        return locationService.findLocationById(id);
//    }

    @GetMapping
    public List<LocationDto> getLocations(QueryParameters queryParameters) {
        return locationService.getLocations(queryParameters);
    }

    @GetMapping("/{id}")
    public LocationDto findLocationById(@PathVariable("id") long id) {
        return locationService.findLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody CreateLocationCommand command) {
        return locationService.createLocation(command);
    }

    @PutMapping("/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id, @RequestBody UpdateLocationCommand updateLocationCommand) {
        return locationService.updateLocation(id, updateLocationCommand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationById(@PathVariable("id") long id) {
        locationService.deleteLocation(id);
    }
}
