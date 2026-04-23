package locations;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinateValidatorTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldAcceptValidCoordinates() {
        CreateLocationCommand command = new CreateLocationCommand("Budapest", 47.4979, 19.0402);

        assertThat(validator.validate(command)).isEmpty();
    }

    @Test
    void shouldRejectInvalidLatitudeAndLongitude() {
        CreateLocationCommand command = new CreateLocationCommand("Budapest", 100.0, 200.0);

        assertThat(validator.validate(command))
                .extracting(violation -> violation.getPropertyPath().toString())
                .containsExactlyInAnyOrder("latitude", "longitude");
    }
}
