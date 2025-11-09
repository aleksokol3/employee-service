package su.aleksokol3.employeeservice.model.api.filter;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EmployeeFilter(
        String firstName,
        String patronymic,
        String lastName,
        Integer age,
        BigDecimal salary,
        LocalDate hiringDate) {
}
