package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class LocationsControllerWebClientIT {

    @MockitoBean
    LocationService locationService;

    @Autowired
    WebTestClient webClient;

    @Test
    void testCreateEmployee() {
        when(locationService.createLocation(any()))
                .thenReturn(new LocationDto(1L, "Budapest", 12.53214, 23.5324));

        webClient
                .post()
                .uri("/api/locations")
                .bodyValue(new CreateLocationCommand("Budapest", 12.53214, 23.5324))
                .exchange()
                .expectStatus().isCreated()
                //.expectBody(String.class).value(s -> System.out.println(s));
                //.expectBody().jsonPath("name").isEqualTo("Budapest");
                .expectBody(LocationDto.class).value(l -> assertEquals("Budapest", l.getName()));
    }

    @Test
    void testFindLocationById() {
        when(locationService.findLocationById(1L))
                .thenReturn(new LocationDto(1L, "Budapest", 12.53214, 23.5324));

        webClient.get().uri("/api/locations/{id}", 1)
                .exchange()
                .expectBody(LocationDto.class).value(l -> assertEquals("Budapest", l.getName()));
    }

    @Test
    void testListLocation() {
        when(locationService.getLocations(any()))
                .thenReturn(List.of(
                        new LocationDto(1L, "Budapest", 23.4214, 23.42414),
                        new LocationDto(2L, "Mallorca", 20.4232, 13.42324)
                ));

        webClient
                .get()
                .uri("/api/locations")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocationDto.class).hasSize(2).contains(
                        new LocationDto(1L, "Budapest", 23.4214, 23.42414)
                );
    }
}
