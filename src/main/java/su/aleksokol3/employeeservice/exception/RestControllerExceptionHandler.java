package su.aleksokol3.employeeservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A subclass of {@link ResponseEntityExceptionHandler} designed for customizing exception responses.
 */
@RestControllerAdvice(basePackages = "su.aleksokol3.employeeservice.controller")
@RequiredArgsConstructor
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        List<String> errors = ex.getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
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
     * @param req the current request
     * @return a {@link ResponseEntity} for the response to use
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex, WebRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        String entityNameLocalized = messageSource.getMessage(ex.getEntityName(), new Object[0], req.getLocale());
      //  body.put(ex.getEntityName(), ex.getErrorMessageCode());
//        body.put("error", messageSource.getMessage(
//                  ex.getErrorMessageCode(), new Object[]{entityNameLocalized, ex.getEntityId()}, req.getLocale()));
        body.put("error", ex.getErrorMessageCode());
        body.put("description", "ID %s of %s is not found".formatted(ex.getEntityId(), ex.getEntityName()));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setProperties(body);
        return ResponseEntity.of(problemDetail).build();
    }
}
