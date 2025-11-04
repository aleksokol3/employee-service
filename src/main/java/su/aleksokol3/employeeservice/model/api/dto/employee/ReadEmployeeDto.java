package su.aleksokol3.employeeservice.model.api.dto.employee;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ReadEmployeeDto(
        UUID id,
        String firstName,
        String patronymic,
        String lastName,
        Integer age,
        BigDecimal salary,
        LocalDate hiringDate) {
}
