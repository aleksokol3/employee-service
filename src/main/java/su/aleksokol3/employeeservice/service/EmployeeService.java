package su.aleksokol3.employeeservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import su.aleksokol3.employeeservice.model.api.dto.PageDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;

import java.util.UUID;

/**
 * EmployeeService is the {@link Service} interface for employee.
 */
public interface EmployeeService {

    /**
     * Find employees by filter and pageable.
     *
     * @param filter {@link SearchEmployeeFilter} must not be {@literal null}.
     * @param pageable {@link Pageable} must not be {@literal null}.
     * @return {@link PageDto} of {@link ReadEmployeeDto}'s, matching the given filter and pageable.
     */
    PageDto<ReadEmployeeDto> findBy(SearchEmployeeFilter filter, Pageable pageable);

    /**
     * Find an employee by given id.
     *
     * @param id {@link UUID} of searching Employee.
     * @return Found employee as {@link ReadEmployeeDto}.
     */
    ReadEmployeeDto findById(UUID id);

    /**
     * Create un employee.
     *
     * @param dto {@link CreateEmployeeDto} with data to create employee.
     * @return {@link UUID} of creating employee.
     */
    UUID create(CreateEmployeeDto dto);

    /**
     * Update un employee.
     *
     * @param id {@link UUID} of employee to update.
     * @param dto {@link PatchEmployeeDto} with data to update employee.
     * @return Updated employee as {@link ReadEmployeeDto}.
     */
    ReadEmployeeDto update(UUID id, PatchEmployeeDto dto);

    /**
     * Delete an employee by given id.
     *
     * @param id {@link UUID} of employee to delete.
     */
    void deleteById(UUID id);

    /**
     * Delete an employee by given filter.
     *
     * @param filter {@link DeleteEmployeeFilter} must not be null.
     */
    void deleteBy(DeleteEmployeeFilter filter);
}
