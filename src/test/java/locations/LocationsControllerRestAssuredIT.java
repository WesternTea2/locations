package locations;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@SpringBootTest
@AutoConfigureMockMvc
class LocationsControllerRestAssuredIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LocationService service;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.requestSpecification =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON);

        service.deleteAllLocation();
    }

    @Test
    void testCreateLocation() {
        with()
                .body(new CreateLocationCommand("Test City", 47.4979, 19.0402))
                .post("/api/locations")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test City"))
                .body("latitude", equalTo(47.4979F))
                .body("longitude", equalTo(19.0402F));
    }

    @Test
    void testGetLocations() {
        createLocation("Alpha City", 10.1, 20.2);
        createLocation("Beta City", 30.3, 40.4);

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("name", hasItem("Alpha City"))
                .body("name", hasItem("Beta City"))
                .body("$", hasSize(2));
    }

    @Test
    void testGetLocationsByPrefix() {
        createLocation("PrefixOne", 11.1, 22.2);
        createLocation("PrefixTwo", 33.3, 44.4);
        createLocation("OtherCity", 55.5, 66.6);

        with()
                .queryParam("prefix", "Prefix")
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("name", hasItem("PrefixOne"))
                .body("name", hasItem("PrefixTwo"))
                .body("name", not(hasItem("OtherCity")));
    }

    @Test
    void testFindLocationById() {
        long id = createLocation("Find Me", 12.34, 56.78);

        with()
                .get("/api/locations/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) id))
                .body("name", equalTo("Find Me"))
                .body("latitude", equalTo(12.34F))
                .body("longitude", equalTo(56.78F));
    }

    @Test
    void testUpdateLocation() {
        long id = createLocation("Before Update", 10.0, 20.0);

        UpdateLocationCommand command = new UpdateLocationCommand();
        command.setName("After Update");
        command.setLatitude(30.0);
        command.setLongitude(40.0);

        with()
                .body(command)
                .put("/api/locations/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) id))
                .body("name", equalTo("After Update"))
                .body("latitude", equalTo(30.0F))
                .body("longitude", equalTo(40.0F));
    }

    @Test
    void testDeleteLocationById() {
        long id = createLocation("Disposable City", 50.5, 60.6);

        with()
                .delete("/api/locations/{id}", id)
                .then()
                .statusCode(204);

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void testDeleteAllLocations() {
        createLocation("City One", 1.1, 2.2);
        createLocation("City Two", 3.3, 4.4);

        with()
                .delete("/api/locations/deleteAll")
                .then()
                .statusCode(200);

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void validation() {
        with()
                .body(new CreateLocationCommand("Test City", 47.4979, 19.0402))
                .post("/api/locations")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("location-dto.json"));
    }

    @Test
    void testCreateLocationValidationErrors() {
        with()
                .body(new CreateLocationCommand("", 100.0, 200.0))
                .post("/api/locations")
                .then()
                .statusCode(400)
                .contentType("application/problem+json")
                .body("status", is(400))
                .body("violations.field", hasItem("name"))
                .body("violations.field", hasItem("latitude"))
                .body("violations.field", hasItem("longitude"));
    }

    @Test
    void testUpdateLocationValidationErrors() {
        long id = createLocation("Original", 10.0, 20.0);

        UpdateLocationCommand command = new UpdateLocationCommand();
        command.setName("");
        command.setLatitude(-120.0);
        command.setLongitude(190.0);

        with()
                .body(command)
                .put("/api/locations/{id}", id)
                .then()
                .statusCode(400)
                .contentType("application/problem+json")
                .body("status", is(400))
                .body("violations.field", hasItem("name"))
                .body("violations.field", hasItem("latitude"))
                .body("violations.field", hasItem("longitude"));
    }

    private long createLocation(String name, double latitude, double longitude) {
        return with()
                .body(new CreateLocationCommand(name, latitude, longitude))
                .post("/api/locations")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");
    }
}
