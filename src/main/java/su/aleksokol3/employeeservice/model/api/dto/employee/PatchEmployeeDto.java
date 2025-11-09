package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PatchEmployeeDto(
        @NotBlank
        JsonNullable<String> firstName,
        @Size(min = 1, max = 256)
        JsonNullable<String> patronymic,
        @NotBlank
        JsonNullable<String> lastName,
        @Min(1)
        JsonNullable<Integer> age,
        @Min(0)
        JsonNullable<BigDecimal> salary,
        JsonNullable<LocalDate> hiringDate
) {
}
