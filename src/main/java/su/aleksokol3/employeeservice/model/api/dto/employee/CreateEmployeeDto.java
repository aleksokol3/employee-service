package su.aleksokol3.employeeservice.model.api.dto.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import su.aleksokol3.employeeservice.validation.BigDecimal;
import su.aleksokol3.employeeservice.validation.LocalDate;
import su.aleksokol3.employeeservice.validation.MinString;
import su.aleksokol3.employeeservice.validation.NotBlankOrNull;
import su.aleksokol3.employeeservice.validation.PastOrPresentString;

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

        @NotBlank(message = "firstname.not.blank")
        @Size(min = 1, max = 256, message = "firstname.size")
        String firstName,

        @NotBlankOrNull(message = "patronymic.not.blank.or.null")
        @Size(min = 1, max = 256, message = "patronymic.size")
        String patronymic,

        @NotBlank(message = "lastname.not.blank")
        @Size(min = 1, max = 256, message = "lastname.size")
        String lastName,

        @NotNull(message = "age.not.null")
        @Min(value = 1, message = "age.min")
        Integer age,

        @NotNull(message = "salary.not.null")
        @MinString(value = 0, message = "salary.min")
        @BigDecimal(message = "salary.big.decimal")
        String salary,

        @NotNull(message = "hiringdate.not.null")
        @PastOrPresentString(message = "hiringdate.past.or.present")
        @LocalDate(message = "hiringdate.local.date")
        String hiringDate
) {
}
