package su.aleksokol3.employeeservice.model.api.filter;

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

        @Size(min = 1, max = 256, message = "{firstname.size}")
        String firstName,

        @Size(min = 1, max = 256, message = "{patronymic.size}")
        String patronymic,

        @Size(min = 1, max = 256, message = "{lastname.size}")
        String lastName,

        @Min(value = 1, message = "{age.min}")
        Integer ageFrom,

        @Min(value = 1, message = "{age.min}")
        Integer ageTo,

        @Min(value = 0, message = "{salary.min}")
        BigDecimal salaryFrom,

        @Min(value = 0, message = "{salary.min}")
        BigDecimal salaryTo,

        LocalDate hiringDateFrom,

        LocalDate hiringDateTo) {
}
