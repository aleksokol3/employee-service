package su.aleksokol3.employeeservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
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

@RestControllerAdvice(basePackages = "su.aleksokol3.employeeservice.controller")
@RequiredArgsConstructor
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
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
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        String entityNameLocalized = messageSource.getMessage(ex.getEntityName(), new Object[0], req.getLocale());
        body.put("error", messageSource.getMessage(ex.getErrorMessageCode(), new Object[]{entityNameLocalized, ex.getEntityId()}, req.getLocale()));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setProperties(body);
        return ResponseEntity.of(problemDetail).build();
    }
}
