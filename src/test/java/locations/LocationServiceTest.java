package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationServiceTest {

    LocationService locationsService;
    LocationMapper locationMapper;

    @BeforeEach
    void init() {
        locationMapper = new LocationMapper() {
            @Override
            public LocationDto toDto(Location location) {
                return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude());
            }

            @Override
            public List<LocationDto> toDto(List<Location> locations) {
                return locations.stream().map(this::toDto).toList();
            }
        };
        locationsService = new LocationService(locationMapper, true);
    }

    @Test
    void testGetLocationsByPrefix() {
        QueryParameters queryParameters = new QueryParameters();
        queryParameters.setPrefix("B");

        List<LocationDto> expected = locationsService.getLocations(queryParameters);

        assertThat(expected)
                .hasSize(2)
                .extracting(LocationDto::getName)
                .containsExactly("Budapest", "BudaBuda");
    }

    @Test
    void testGetLocations() {
        List<LocationDto> expected = locationsService.getLocations(new QueryParameters());

        assertThat(expected)
                .hasSize(5)
                .extracting(LocationDto::getName)
                .containsExactly("Budapest", "BudaBuda", "Catania", "Taormina", "Sao Paulo");
    }

    @Test
    void testFindLocationById() {
        LocationDto expected = locationsService.findLocationById(3);

        assertEquals("Catania", expected.getName());
    }

    @Test
    void testCreateLocationCapitalizesNameWhenEnabled() {
        CreateLocationCommand command = new CreateLocationCommand("budapest", 47.497912, 19.040235);

        LocationDto created = locationsService.createLocation(command);

        assertEquals("Budapest", created.getName());
    }

    @Test
    void testUpdateLocationCapitalizesNameWhenEnabled() {
        UpdateLocationCommand command = new UpdateLocationCommand();
        command.setName("taormina");
        command.setLatitude(40.497912);
        command.setLongitude(16.040235);

        LocationDto updated = locationsService.updateLocation(4, command);

        assertEquals("Taormina", updated.getName());
    }

    @Test
    void testCreateLocationKeepsNameWhenCapitalizationDisabled() {
        LocationService locationService = new LocationService(locationMapper, false);
        CreateLocationCommand command = new CreateLocationCommand("budapest", 47.497912, 19.040235);

        LocationDto created = locationService.createLocation(command);

        assertEquals("budapest", created.getName());
    }

    @Test
    void testUpdateLocationKeepsNameWhenCapitalizationDisabled() {
        LocationService locationService = new LocationService(locationMapper, false);
        UpdateLocationCommand command = new UpdateLocationCommand();
        command.setName("taormina");
        command.setLatitude(40.497912);
        command.setLongitude(16.040235);

        LocationDto updated = locationService.updateLocation(4, command);

        assertEquals("taormina", updated.getName());
    }
}
