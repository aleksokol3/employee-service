package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record CreateEmployeeDto(
        @NotNull
        @Size(min = 1, max = 256)
        String firstName,

        @Size(min = 1, max = 256)
        String patronymic,

        @NotNull
        @Size(min = 1, max = 256)
        String lastName,

        @Min(1)
        Integer age,

        @Min(0)
        BigDecimal salary
        ) {
}
