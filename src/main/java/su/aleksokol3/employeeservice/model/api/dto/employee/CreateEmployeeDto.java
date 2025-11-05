package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

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
        BigDecimal salary
        ) {
}
