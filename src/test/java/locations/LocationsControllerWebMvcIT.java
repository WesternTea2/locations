package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = LocationController.class)
public class LocationsControllerWebMvcIT {

    @MockitoBean
    LocationService locationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetLocations() throws Exception {
        when(locationService.getLocations(any())).thenReturn(List.of(new LocationDto(1L, "Taormina", 21.21314, 32.42141),
                new LocationDto(2L, "Mallorca", 31.42546, 22.72421)));

        mockMvc.perform(get("/api/locations"))
                //            .andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Taormina")))
                .andExpect(jsonPath("$[1].name", equalTo("Mallorca")));
    }
}
