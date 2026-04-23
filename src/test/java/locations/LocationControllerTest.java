package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    LocationService locationService;

    @InjectMocks
    LocationController locationController;

//    @Test
//    void testGetLocations() {
//        List<LocationDto> locations = Arrays.asList(
//                new LocationDto(1L,"Sydney", -33.88223, 151.33140)
//        );
//        when(locationService.getLocations(any())).thenReturn(locations);
//        List<LocationDto> expected = locationController.getLocations(Optional.empty());
//
//        assertThat(expected)
//                .hasSize(1)
//                .extracting(LocationDto::getName)
//                .containsOnly("Sydney");
//    }

//    @Test
//    void testGetLocations() {
//        List<LocationDto> locations = Arrays.asList(
//                new LocationDto(1L,"Mallorca", -30.88223, 112.33140)
//        );
//        when(locationService.getLocations()).thenReturn(locations);
//        List<LocationDto> expected = locationController.getLocations();
//
//        assertThat(expected)
//                .hasSize(1)
//                .extracting(LocationDto::getName)
//                .containsOnly("Mallorca");
//    }
//
//    @Test
//    void testGetLocations() {
//        List<LocationDto> locations = Arrays.asList(
//                new LocationDto(1L, "Mallorca", -30.88223, 112.33140)
//        );
//        when(locationService.getLocations(any())).thenReturn(locations);
//        List<LocationDto> expected = locationController.getLocations(Optional.empty());
//
//        assertThat(expected)
//                .hasSize(1)
//                .extracting(LocationDto::getName)
//                .containsOnly("Mallorca");
//    }
}
