package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.*;
import lombok.Builder;
import su.aleksokol3.employeeservice.validation.NotBlankOrNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Presentation of employee entity to create a new employee.
 *
 * @param firstName Should be not blank, has size between 1 and 256 characters.
 * @param patronymic Can be null, else should be not blank, has size between 1 and 256 characters.
 * @param lastName Should be not blank, has size between 1 and 256 characters.
 * @param age Should be not null, has size equal or more than 1.
 * @param salary Should be not null, has size equal or more than 0.
 * @param hiringDate Should be not null, be the date in the past or in the present.
 */
@Builder
public record CreateEmployeeDto(
        @NotBlank(message = "{firstname.not.blank}")
        @Size(min = 1, max = 256, message = "{firstname.size}")
        String firstName,

        @NotBlankOrNull(message = "{patronymic.not.blank.or.null}")
        @Size(min = 1, max = 256, message = "{patronymic.size}")
        String patronymic,

        @NotBlank(message = "{lastname.not.blank}")
        @Size(min = 1, max = 256, message = "{lastname.size}")
        String lastName,

        @NotNull(message = "{age.not.null}")
        @Min(value = 1, message = "{age.min}")
        Integer age,

        @NotNull(message = "{salary.not.null}")
        @Min(value = 0, message = "{salary.min}")
        BigDecimal salary,

        @NotNull(message = "{hiringdate.not.null}")
        @PastOrPresent(message = "{hiringdate.past.or.present}")
        LocalDate hiringDate
) {
}
