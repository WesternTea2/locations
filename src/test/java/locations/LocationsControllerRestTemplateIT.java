package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testListLocations() {
        LocationDto locationDto = restTemplate.postForObject(
                "/api/locations",
                new CreateLocationCommand("Budapest", 12.321, 23.421),
                LocationDto.class
        );

        assertEquals("Budapest", locationDto.getName());
    }
}
