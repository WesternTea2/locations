package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    LocationService locationService;

    @InjectMocks
    LocationController locationController;

    @Test
    void getLocations() {
        List<Location> locations = List.of(
                new Location("Mallorca", 19.232421, -34.24215)
        );

        when(locationService.getLocations()).thenReturn(locations);

        String actual = locationController.getLocations();

        assertThat(actual).isEqualTo("[Location = 'Mallorca']");    }
    }