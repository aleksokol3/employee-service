package su.aleksokol3.employeeservice.util;

import org.springframework.data.jpa.domain.Specification;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeSpecificationBuilder extends SpecificationBuilder<Employee> {

    public Specification<Employee> buildSearch(SearchEmployeeFilter filter, boolean notSorted) {
        return Specification.allOf(
                like("firstName", filter.firstName()),
                like("patronymic", filter.patronymic()),
                like("lastName", filter.lastName()),
                between("age", filter.ageFrom(), filter.ageTo()),
                between(
                        "salary",
                        filter.salaryFrom() != null ? new BigDecimal(filter.salaryFrom()) : null,
                        filter.salaryTo() != null ? new BigDecimal(filter.salaryTo()) : null
                ),
                between(
                        "hiringDate",
                        filter.hiringDateFrom() != null ? LocalDate.parse(filter.hiringDateFrom()) : null,
                        filter.hiringDateTo() != null ? LocalDate.parse(filter.hiringDateTo()) : null
                ),
                similarity(List.of("lastName", "firstName", "patronymic"), filter.query(), notSorted)
        );
    }

    public Specification<Employee> buildDelete(DeleteEmployeeFilter filter) {
        return Specification.allOf(
                like("firstName", filter.firstName()),
                like("patronymic", filter.patronymic()),
                like("lastName", filter.lastName()),
                between("age", filter.ageFrom(), filter.ageTo()),
                between(
                        "hiringDate",
                        filter.hiringDateFrom() != null ? LocalDate.parse(filter.hiringDateFrom()) : null,
                        filter.hiringDateTo() != null ? LocalDate.parse(filter.hiringDateTo()) : null
                )
        );
    }
}
