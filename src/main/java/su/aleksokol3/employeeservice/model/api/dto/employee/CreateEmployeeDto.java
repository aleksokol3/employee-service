package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CreateEmployeeDto(
        @NotBlank
        @Size(min = 1, max = 256)
        String firstName,

        @Size(min = 1, max = 256)
        String patronymic,

        @NotBlank
        @Size(min = 1, max = 256)
        String lastName,

        @NotNull
        @Min(1)
        Integer age,

        @NotNull
        @Min(0)
        BigDecimal salary,

        @NotNull
        LocalDate hiringDate
        ) {
}
