package su.aleksokol3.employeeservice.model.api.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeFilter(
        String firstName,
        String patronymic,
        String lastName,
        Integer age,
        BigDecimal salary,
        LocalDate hiringDate) {
}
