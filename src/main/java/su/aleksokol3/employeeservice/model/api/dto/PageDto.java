package su.aleksokol3.employeeservice.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

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

    /**
     * creates a new PageDto object from {@link Page}
     *
     * @param page {@link Page}
     * @return new PageDto object
     * @param <T> type of read DTO
     */

    public static <T> PageDto<T> fromPage(Page<T> page) {
        return new PageDto<>(page.toList(), page.getTotalElements(), Instant.now());
    }
}
