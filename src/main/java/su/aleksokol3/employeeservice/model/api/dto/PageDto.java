package su.aleksokol3.employeeservice.model.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageDto<T> {
    List<T> employees;
    long total;
    Instant timestamp;
}
