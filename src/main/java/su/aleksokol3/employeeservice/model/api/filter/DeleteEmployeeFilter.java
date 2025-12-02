package su.aleksokol3.employeeservice.model.api.filter;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import su.aleksokol3.employeeservice.validation.Age;
import su.aleksokol3.employeeservice.validation.HiringDate;

import java.time.LocalDate;

/**
 * Filter to delete employees
 * @param firstName
 * @param patronymic
 * @param lastName
 * @param ageFrom
 * @param ageTo
 * @param hiringDateFrom
 * @param hiringDateTo
 */
@Builder
@Age
@HiringDate
public record DeleteEmployeeFilter(

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

        @Parameter(example = "25")
        @Min(value = 1, message = "{age.min}")
        Integer ageTo,

        @Parameter(example = "2023-01-01")
        LocalDate hiringDateFrom,

        @Parameter(example = "2023-12-31")
        LocalDate hiringDateTo)  {
}
