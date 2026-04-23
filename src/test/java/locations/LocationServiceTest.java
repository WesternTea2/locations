package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationServiceTest {

    LocationService locationsService;

    @BeforeEach
    void init() {
        locationsService = new LocationService(new LocationMapper() {
            @Override
            public LocationDto toDto(Location location) {
                return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude());
            }

            @Override
            public List<LocationDto> toDto(List<Location> locations) {
                return locations.stream().map(this::toDto).toList();
            }
        });
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
}
