package locations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, Double> {

    private Coordinate.Type type;

    @Override
    public void initialize(Coordinate constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return switch (type) {
            case LAT -> value >= -90 && value <= 90;
            case LON -> value >= -180 && value <= 180;
        };
    }
}
