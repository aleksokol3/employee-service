package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.openapitools.jackson.nullable.JsonNullable;
import su.aleksokol3.employeeservice.validation.NotBlankOrNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PatchEmployeeDto(
//        @NotBlank - аннотация над JsonNullable'ом - работает так же
        JsonNullable<@NotBlank(message = "{firstname.not.blank}") String> firstName,
        JsonNullable<@Size(min = 1, max = 256, message = "{patronymic.size}") @NotBlankOrNull(message = "{patronymic.not.blank.or.null}") String> patronymic,
        JsonNullable<@NotBlank(message = "{lastname.not.blank}") String> lastName,
        JsonNullable<@Min(value = 1, message = "{age.min}") Integer> age,
        JsonNullable<@Min(value = 0, message = "{salary.min}") BigDecimal> salary,
        JsonNullable<@PastOrPresent(message = "{hiringdate.past.or.present}") LocalDate> hiringDate
) {
}
