package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocationIT {

    @Autowired
    LocationController locationController;

    @Test
    void getLocations() {
        List<LocationDto> locations = locationController.getLocations(Optional.empty());

        assertThat(locations)
                .hasSize(3)
                .extracting(LocationDto::getName)
                .containsExactly("Budapest", "Catania", "Taormina");
    }
}