package su.aleksokol3.employeeservice.repository;

import org.springframework.data.domain.Pageable;
import su.aleksokol3.employeeservice.model.api.filter.EmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.util.List;

public interface FilterEmployeeRepository {

    List<Employee> findByFilter(EmployeeFilter filter, Pageable pageable);

    void deleteByFilter(EmployeeFilter filter);
}
