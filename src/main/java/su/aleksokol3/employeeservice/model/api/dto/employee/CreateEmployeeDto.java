package su.aleksokol3.employeeservice.model.api.dto.employee;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateEmployeeDto(
        String firstName,
        String lastName,
        Integer age,
        BigDecimal salary,
        Instant hiringDate
        ) {
}
