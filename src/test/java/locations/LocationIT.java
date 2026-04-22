package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocationIT {

    @Autowired
    LocationController locationController;

    @Test
    void getLocations() {
        String locations = locationController.getLocations();

        assertThat(locations)
                .isEqualTo("[Location = 'Budapest', Location = 'Catania', Location = 'Taormina']");
    }
}