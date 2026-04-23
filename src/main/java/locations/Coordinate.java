package locations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoordinateValidator.class)
public @interface Coordinate {

    Type type();

    String message() default "Invalid coordinate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    enum Type {
        LAT, LON
    }
}
