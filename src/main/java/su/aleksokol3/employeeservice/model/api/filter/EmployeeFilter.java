package su.aleksokol3.employeeservice.model.api.filter;

import java.math.BigDecimal;
import java.time.Instant;

public record EmployeeFilter(String firstName, String lastName, Integer age, BigDecimal salary, Instant hiringDate, Integer limit, Integer offset) {
}
