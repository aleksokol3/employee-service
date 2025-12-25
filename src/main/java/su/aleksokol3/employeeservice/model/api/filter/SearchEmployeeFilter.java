package su.aleksokol3.employeeservice.model.api.filter;

import lombok.Builder;
import su.aleksokol3.employeeservice.validation.BigDecimal;
import su.aleksokol3.employeeservice.validation.LocalDate;

/**
 * Filter to search employees
 *
 * @param firstName
 * @param patronymic
 * @param lastName
 * @param ageFrom
 * @param ageTo
 * @param salaryFrom
 * @param salaryTo
 * @param hiringDateFrom
 * @param hiringDateTo
 */

@Builder
public record SearchEmployeeFilter(

        String firstName,

        String patronymic,

        String lastName,

        Integer ageFrom,

        Integer ageTo,

        @BigDecimal(message = "salary.big.decimal")
        String salaryFrom,

        @BigDecimal(message = "salary.big.decimal")
        String salaryTo,

        @LocalDate(message = "hiringdate.local.date")
        String hiringDateFrom,

        @LocalDate(message = "hiringdate.local.date")
        String hiringDateTo,

        String query
) {
}
