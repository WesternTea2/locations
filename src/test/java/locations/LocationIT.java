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
        List<LocationDto> locations = locationController.getLocations(new QueryParameters());

        assertThat(locations)
                .hasSize(5)
                .extracting(LocationDto::getName)
                .containsExactly("Budapest", "BudaBuda", "Catania", "Taormina", "Sao Paulo");
    }
}
