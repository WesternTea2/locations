package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class LocationsProblemHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LocationNotFoundException.class)
    public ProblemDetail handleNotFoundException(LocationNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("locations/not-found"));
        problemDetail.setTitle("Location Not Found");
        problemDetail.setProperty("id", UUID.randomUUID().toString());
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problemDetail.setTitle("Constraint Violation");
        problemDetail.setType(URI.create("locations/validation-error"));
        problemDetail.setProperty("violations", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toViolation)
                .toList());
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private Map<String, String> toViolation(FieldError error) {
        return Map.of(
                "field", error.getField(),
                "message", error.getDefaultMessage() == null ? "Invalid value" : error.getDefaultMessage()
        );
    }
}
