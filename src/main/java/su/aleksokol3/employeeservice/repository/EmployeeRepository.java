package su.aleksokol3.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.util.UUID;

/**
 * EmployeeRepository is the {@link Repository} interface for employee.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
}
