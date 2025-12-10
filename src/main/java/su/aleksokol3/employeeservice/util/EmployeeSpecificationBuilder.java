package su.aleksokol3.employeeservice.util;

import org.springframework.data.jpa.domain.Specification;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeSpecificationBuilder extends SpecificationBuilder<Employee> {

    public Specification<Employee> buildSearch(SearchEmployeeFilter filter) {
        return super
                .and(like("firstName", filter.firstName()))
                .and(like("patronymic", filter.patronymic()))
                .and(like("lastName", filter.lastName()))
                .and(between("age", filter.ageFrom(), filter.ageTo()))
                .and(between(
                        "salary",
                        filter.salaryFrom() != null ? new BigDecimal(filter.salaryFrom()) : null,
                        filter.salaryTo() != null ? new BigDecimal(filter.salaryTo()) : null
                ))
                .and(between(
                        "hiringDate",
                        filter.hiringDateFrom() != null ? LocalDate.parse(filter.hiringDateFrom()) : null,
                        filter.hiringDateTo() != null ? LocalDate.parse(filter.hiringDateTo()) : null
                ))
                .build();
    }

    public Specification<Employee> buildDelete(DeleteEmployeeFilter filter) {
        return super
                .and(like("firstName", filter.firstName()))
                .and(like("patronymic", filter.patronymic()))
                .and(like("lastName", filter.lastName()))
                .and(between("age", filter.ageFrom(), filter.ageTo()))
                .and(between(
                        "hiringDate",
                        filter.hiringDateFrom() != null ? LocalDate.parse(filter.hiringDateFrom()) : null,
                        filter.hiringDateTo() != null ? LocalDate.parse(filter.hiringDateTo()) : null
                ))
                .build();
    }
}
