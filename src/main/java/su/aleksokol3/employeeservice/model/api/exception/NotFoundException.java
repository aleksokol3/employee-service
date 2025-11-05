package su.aleksokol3.employeeservice.model.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorMessage;
    public NotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
