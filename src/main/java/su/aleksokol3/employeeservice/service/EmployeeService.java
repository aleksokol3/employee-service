package su.aleksokol3.employeeservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import su.aleksokol3.employeeservice.model.api.dto.PageList;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;

import java.util.UUID;

@Transactional(readOnly = true)
public interface EmployeeService {
    PageList<ReadEmployeeDto> findBy(SearchEmployeeFilter filter, Pageable pageable);

    ReadEmployeeDto findById(UUID id);

    @Transactional
    UUID create(CreateEmployeeDto dto);

    @Transactional
    void update(UUID id, PatchEmployeeDto dto);

    @Transactional
    void deleteById(UUID id);

    @Transactional
    void deleteBy(DeleteEmployeeFilter filter);
}
