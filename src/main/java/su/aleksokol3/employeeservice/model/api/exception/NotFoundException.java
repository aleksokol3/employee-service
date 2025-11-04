package su.aleksokol3.employeeservice.model.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    public NotFoundException(HttpStatus httpStatus) {
        super(httpStatus.toString());
    }
}
