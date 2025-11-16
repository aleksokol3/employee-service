package su.aleksokol3.employeeservice.model.api.dto.employee;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Presentation of employee entity, returning from repository.
 */
@Builder
public record ReadEmployeeDto(
        UUID id,
        String firstName,
        String patronymic,
        String lastName,
        Integer age,
        BigDecimal salary,
        LocalDate hiringDate) {
}
