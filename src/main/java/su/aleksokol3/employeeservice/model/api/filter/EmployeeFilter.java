package su.aleksokol3.employeeservice.model.api.filter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EmployeeFilter(
        @NotBlank
        @Size(min = 1, max = 256)
        String firstName,

        @Size(min = 1, max = 256)
        String patronymic,

        @NotBlank
        @Size(min = 1, max = 256)
        String lastName,

        @Min(1)
        Integer ageBefore,
        @Min(1)
        Integer ageAfter,

        @Min(0)
        BigDecimal salaryBefore,

        @Min(0)
        BigDecimal salaryAfter,

        LocalDate hiringDateBefore,

        LocalDate hiringDateAfter) {
}
