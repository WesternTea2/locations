package locations;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationServiceTest {

    LocationService locationsService;

//    @Test
//    void testGetLocationsByPrefix() {
//        List<LocationDto> expected = locationsService.getLocations(Optional.of("B"));
//
//        assertThat(expected)
//                .hasSize(1)
//                .extracting(LocationDto::getName)
//                .containsExactly("Budapest");
//    }
//
//    @Test
//    void testGetLocations() {
//        List<LocationDto> expected = locationsService.getLocations(Optional.empty());
//
//        assertThat(expected)
//                .hasSize(3)
//                .extracting(LocationDto::getName)
//                .containsExactly("Budapest", "Catania", "Mallorca");
//    }
//
//    @Test
//    void testFindLocationById() {
//        LocationDto expected = locationsService.findLocationById(2);
//
//        assertEquals("Róma", expected.getName());
//    }
}
