package locations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Operations on locations")
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

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<LocationDto> getLocations(@ParameterObject @ModelAttribute QueryParameters queryParameters) {
        return locationService.getLocations(queryParameters);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public LocationDto findLocationById(@PathVariable("id") long id) {
        return locationService.findLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "creates a location")
    @ApiResponse(responseCode = "201", description = "location has been created")
    public LocationDto createLocation(@Valid @RequestBody CreateLocationCommand command) {
        return locationService.createLocation(command);
    }

    @PutMapping("/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id, @Valid @RequestBody UpdateLocationCommand updateLocationCommand) {
        return locationService.updateLocation(id, updateLocationCommand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationById(@PathVariable("id") long id) {
        locationService.deleteLocation(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllLocations() {
        locationService.deleteAllLocation();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Violation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                String.format(ex.getMessage()));
        problemDetail.setType(URI.create("locations/not-valid"));
        problemDetail.setTitle("Validation error");
        problemDetail.setProperty("id", UUID.randomUUID().toString());
        problemDetail.setProperty("violations", violations);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problemDetail);
    }
}
