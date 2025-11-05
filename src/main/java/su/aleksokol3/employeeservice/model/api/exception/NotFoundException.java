package su.aleksokol3.employeeservice.model.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
