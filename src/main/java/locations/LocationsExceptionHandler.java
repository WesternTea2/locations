package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.UUID;

@ControllerAdvice
public class LocationsExceptionHandler {

    @ExceptionHandler(LocationNotFoundException.class)
    public ProblemDetail handleNotFoundException(LocationNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                ex.getMessage());
        problemDetail.setType(URI.create("locations/not-found"));
        problemDetail.setTitle("Location Not Found");
        problemDetail.setProperty("id", UUID.randomUUID().toString());

        return problemDetail;
    }
}
