package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.*;
import lombok.Builder;
import su.aleksokol3.employeeservice.validation.NotBlankOrNull;

import java.math.BigDecimal;
import java.time.LocalDate;

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
