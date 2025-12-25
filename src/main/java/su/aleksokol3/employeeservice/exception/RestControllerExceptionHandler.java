package su.aleksokol3.employeeservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A subclass of {@link ResponseEntityExceptionHandler} designed for customizing exception responses.
 */
@RestControllerAdvice(basePackages = "su.aleksokol3.employeeservice.controller")
@RequiredArgsConstructor
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        Map<String, List<String>> errors = ex.getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        body.put("errors", errors);
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setProperties(body);
        return ResponseEntity.of(problemDetail).build();
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        List<String> errors = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
        body.put("errors", errors);
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setProperties(body);
        return ResponseEntity.of(problemDetail).build();
    }
    /**
     * Customize the handling of {@link PropertyReferenceException}
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} for the response to use
     */
    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<ProblemDetail> handlePropertyReferenceException(PropertyReferenceException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("error", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setProperties(body);
        return ResponseEntity.of(problemDetail).build();
    }

    /**
     * Customize the handling of {@link NotFoundException}
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} for the response to use
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("error", ex.getErrorMessageCode());
        body.put("description", "ID %s of %s is not found".formatted(ex.getEntityId(), ex.getEntityName()));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setProperties(body);
        return ResponseEntity.of(problemDetail).build();
    }
}
