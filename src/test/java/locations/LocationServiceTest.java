package locations;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class LocationServiceTest {

    @Test
    void getLocations() {
        LocationService locationService = new LocationService(new ModelMapper());

        List<LocationDto> locationList = locationService.getLocations();

        assertThat(locationList)
                .hasSize(3)
                .extracting(LocationDto::getName, LocationDto::getLatitude, LocationDto::getLongitude)
                .containsExactly(
                        tuple("Budapest", 47.497912, 19.040235),
                        tuple("Catania", 41.497912, 14.040235),
                        tuple("Taormina", 40.497912, 14.040235)
                );    }
}