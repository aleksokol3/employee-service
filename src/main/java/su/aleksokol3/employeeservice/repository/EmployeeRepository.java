package su.aleksokol3.employeeservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import su.aleksokol3.employeeservice.model.api.filter.EmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, FilterEmployeeRepository {

//    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName AND e.age = :age AND e.salary = :salary AND e.hiringDate = :hiringDate")
//    List<Employee> findByFilter(EmployeeFilter filter);

//    List<Employee> findByFilterContaining(EmployeeFilter filter, Pageable pageable);
}
