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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    LocationService locationService;

    @InjectMocks
    LocationController locationController;

    @Test
    void getLocations(Optional<String> prefix) {
        List<LocationDto> locations = List.of(
                new LocationDto(1L, "Mallorca", -52.24514, 241.42151)
        );
        when(locationService.getLocations(prefix)).thenReturn(locations);
        List<LocationDto> expected = locationController.getLocations(prefix);

        assertThat(expected)
                .hasSize(1)
                .extracting(LocationDto::getName)
                .containsOnly("Mallorca");
    }
}