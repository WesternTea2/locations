package locations;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class LocationServiceTest {

    @Test
    void getLocations() {
        LocationService locationService = new LocationService();

        List<Location> locationList = locationService.getLocations();

        assertThat(locationList)
                .hasSize(3)
                .extracting(Location::getName, Location::getLatitude, Location::getLongitude)
                .containsExactly(
                        tuple("Budapest", 47.497912, 19.040235),
                        tuple("Catania", 41.497912, 14.040235),
                        tuple("Taormina", 40.497912, 14.040235)
                );    }
}