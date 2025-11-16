package su.aleksokol3.employeeservice.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.RestController;

/**
 * NotFoundException is the {@link RuntimeException} class for exceptions when an entity is not found in the repository.
 */
@Getter
public class NotFoundException extends RuntimeException {

    /**
     * Code of the exception. Located in message.properties.
     */
    private final String errorMessageCode;
    /**
     * Name of the entity, that not was found in the repository.
     */
    private final String entityName;
    /**
     * ID of the entity, that not was found in the repository.
     */
    private final String entityId;

    public NotFoundException(String errorMessageCode, String entityName, String entityId) {
        super(errorMessageCode);
        this.errorMessageCode = errorMessageCode;
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
