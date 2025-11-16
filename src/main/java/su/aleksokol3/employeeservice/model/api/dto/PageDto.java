package su.aleksokol3.employeeservice.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

/**
 * Representation of the response to a request with filter and pagination
 * @param <T> type of entity
 */
@AllArgsConstructor
@Getter
@Setter
public class PageDto<T> {
    /**
     * List of employees
     */
    List<T> employees;
    /**
     * Total number of elements
     */
    long total;
    /**
     * Timestamp of PageDto creation
     */
    Instant timestamp;
}
