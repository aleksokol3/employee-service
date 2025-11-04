package su.aleksokol3.employeeservice.model.api.dto.employee;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ReadEmployeeDto(
        UUID id,
        String firstName,
        String lastName,
        Integer age,
        BigDecimal salary,
        Instant hiringDate) {
}
