package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LocationsControllerRestTemplateIT {

    @Autowired
    RestTestClient restTestClient;

    @Test
    void testListLocations() {
        restTestClient.post()
                .uri("/api/locations")
                .body(new CreateLocationCommand("Budapest", 12.321, 23.421))
                .exchange()
                .expectBody(LocationDto.class)
                .value(locationDto -> assertEquals("Budapest", locationDto.getName()));

        restTestClient.post()
                .uri("/api/locations")
                .body(new CreateLocationCommand("Catania", 15.342, 20.431))
                .exchange()
                .expectBody(LocationDto.class)
                .value(locationDto -> assertEquals("Catania", locationDto.getName()));


        restTestClient.get().uri("/api/locations").exchange().expectBody(new ParameterizedTypeReference<List<LocationDto>>() {
        })
                .value(employees -> assertThat(employees).extracting(LocationDto::getName).containsExactly("Budapest", "Catania"));

    }
}
