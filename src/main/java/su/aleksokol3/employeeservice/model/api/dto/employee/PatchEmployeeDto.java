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

/**
 * Presentation of employee entity to update an employee.
 *
 * @param firstName Should be not blank, has size between 1 and 256 characters.
 * @param patronymic Can be null, else should be not blank, has size between 1 and 256 characters.
 * @param lastName Should be not blank, has size between 1 and 256 characters.
 * @param age Should be not null, has size equal or more than 1.
 * @param salary Should be not null, has size equal or more than 0.
 * @param hiringDate Should be not null, be the date in the past or in the present.
 */
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
