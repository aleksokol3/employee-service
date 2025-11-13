package su.aleksokol3.employeeservice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String errorMessageCode;
    private final String entityName;
    private final String entityId;

    public NotFoundException(String errorMessageCode, String entityName, String entityId) {
        super(errorMessageCode);
        this.errorMessageCode = errorMessageCode;
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
