package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.openapitools.jackson.nullable.JsonNullable;
import su.aleksokol3.employeeservice.validation.NotBlankOrNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PatchEmployeeDto(
//        @NotBlank - аннотация над JsonNullable'ом - работает так же
        JsonNullable<@NotBlank String> firstName,
        JsonNullable<@Size(min = 1, max = 256) @NotBlankOrNull String> patronymic,
        JsonNullable<@NotBlank String> lastName,
        JsonNullable<@Min(1) Integer> age,
        JsonNullable<@Min(0) BigDecimal> salary,
        JsonNullable<LocalDate> hiringDate
) {
}
