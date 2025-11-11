package su.aleksokol3.employeeservice.model.api.filter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import su.aleksokol3.employeeservice.validation.Age;
import su.aleksokol3.employeeservice.validation.HiringDate;

import java.time.LocalDate;

@Builder
@Age
@HiringDate
public record DeleteEmployeeFilter(

        @Size(min = 1, max = 256)
        String firstName,

        @Size(min = 1, max = 256)
        String patronymic,

        @Size(min = 1, max = 256)
        String lastName,

        @Min(1)
        Integer ageFrom,

        @Min(1)
        Integer ageTo,

        LocalDate hiringDateFrom,

        LocalDate hiringDateTo)  {
}
