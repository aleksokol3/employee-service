package su.aleksokol3.employeeservice.model.api.exceptionhandler;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import su.aleksokol3.employeeservice.model.api.exception.NotFoundException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "su.aleksokol3.employeeservice.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());

        List<String> errors = ex.getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(field -> field.getField().toUpperCase() + ": " + field.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);
        body.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", ex.getHttpStatus().value());
        body.put("error", ex.getErrorMessage());
        body.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
