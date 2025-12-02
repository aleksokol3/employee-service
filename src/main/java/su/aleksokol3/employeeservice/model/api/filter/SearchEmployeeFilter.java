package su.aleksokol3.employeeservice.model.api.filter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import su.aleksokol3.employeeservice.validation.Age;
import su.aleksokol3.employeeservice.validation.HiringDate;
import su.aleksokol3.employeeservice.validation.Salary;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Filter to search employees
 *
 * @param firstName
 * @param patronymic
 * @param lastName
 * @param ageFrom
 * @param ageTo
 * @param salaryFrom
 * @param salaryTo
 * @param hiringDateFrom
 * @param hiringDateTo
 */

@Builder
@Age
@Salary
@HiringDate
public record SearchEmployeeFilter(

        @Parameter(example = "John")
        @Size(min = 1, max = 256, message = "{firstname.size}")
        String firstName,

        @Parameter(example = "Petrovich")
        @Size(min = 1, max = 256, message = "{patronymic.size}")
        String patronymic,

        @Parameter(example = "Johnson")
        @Size(min = 1, max = 256, message = "{lastname.size}")
        String lastName,

        @Parameter(example = "18")
        @Min(value = 1, message = "{age.min}")
        Integer ageFrom,

        @Parameter(example = "60")
        @Min(value = 1, message = "{age.min}")
        Integer ageTo,

        @Parameter(example = "0")
        @Min(value = 0, message = "{salary.min}")
        BigDecimal salaryFrom,

        @Parameter(example = "9876.54")
        @Min(value = 0, message = "{salary.min}")
        BigDecimal salaryTo,

        @Parameter(example = "2025-01-01")
        LocalDate hiringDateFrom,

        @Parameter(example = "2025-12-31")
        LocalDate hiringDateTo) {
}
