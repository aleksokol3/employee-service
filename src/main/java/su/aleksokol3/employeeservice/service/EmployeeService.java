package su.aleksokol3.employeeservice.service;

import org.springframework.data.domain.Pageable;
import su.aleksokol3.employeeservice.model.api.dto.PageDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;

import java.util.UUID;

public interface EmployeeService {
    PageDto<ReadEmployeeDto> findBy(SearchEmployeeFilter filter, Pageable pageable);

    ReadEmployeeDto findById(UUID id);

    UUID create(CreateEmployeeDto dto);

    ReadEmployeeDto update(UUID id, PatchEmployeeDto dto);

    void deleteById(UUID id);

    void deleteBy(DeleteEmployeeFilter filter);
}
