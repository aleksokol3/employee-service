package su.aleksokol3.employeeservice.model.api.filter;

import lombok.Builder;
import su.aleksokol3.employeeservice.validation.LocalDate;

/**
 * Filter to delete employees
 * @param firstName
 * @param patronymic
 * @param lastName
 * @param ageFrom
 * @param ageTo
 * @param hiringDateFrom
 * @param hiringDateTo
 */
@Builder
public record DeleteEmployeeFilter(

        String firstName,

        String patronymic,

        String lastName,

        Integer ageFrom,

        Integer ageTo,

        @LocalDate(message = "hiringdate.local.date")
        String hiringDateFrom,

        @LocalDate(message = "hiringdate.local.date")
        String hiringDateTo
) {
}
