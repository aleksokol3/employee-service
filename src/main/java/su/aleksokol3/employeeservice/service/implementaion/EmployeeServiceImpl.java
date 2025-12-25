package su.aleksokol3.employeeservice.service.implementaion;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.aleksokol3.employeeservice.model.api.dto.PageDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.exception.NotFoundException;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.mapper.EmployeeMapper;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.repository.EmployeeRepository;
import su.aleksokol3.employeeservice.service.EmployeeService;
import su.aleksokol3.employeeservice.util.EmployeeSpecificationBuilder;
import su.aleksokol3.employeeservice.util.PageUtils;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public PageDto<ReadEmployeeDto> findBy(SearchEmployeeFilter filter, Pageable pageable) {
        log.info("Finding employees by filter: {}", filter);
        Page<Employee> employeesByFilterPage = employeeRepository.findAll(
                new EmployeeSpecificationBuilder().buildSearch(filter, pageable.getSort().isEmpty()),
                PageUtils.preparePageable(pageable)
        );

        return PageDto.fromPage(employeesByFilterPage
                .map(employeeMapper::entityToReadDto));
    }

    @Override
    public ReadEmployeeDto findById(UUID id) {
        log.info("Finding employee by id: {}", id);
        return employeeRepository.findById(id)
                .map(employeeMapper::entityToReadDto)
                .orElseThrow(() -> new NotFoundException("not.found.exception", "employee", id.toString()));
    }

    @Transactional
    @Override
    public ReadEmployeeDto create(CreateEmployeeDto dto) {
        log.info("Creating new employee: {}", dto);
        Employee employee = employeeMapper.createDtoToEntity(dto);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.entityToReadDto(savedEmployee);
    }

    @Transactional
    @Override
    public ReadEmployeeDto update(UUID id, PatchEmployeeDto dto) {
        log.info("Updating employee: {}", dto);
        return employeeRepository.findById(id)
                        .map(employee -> employeeMapper.updateEntity(dto, employee))
                        .map(employeeRepository::saveAndFlush)
                        .map(employeeMapper::entityToReadDto)
                        .orElseThrow(() -> new NotFoundException("not.found.exception", "employee", id.toString()));
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        log.info("Deleting employee by id: {}", id);
        employeeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteBy(DeleteEmployeeFilter filter) {
        log.info("Deleting employee by filter: {}", filter);
        employeeRepository.delete(
                new EmployeeSpecificationBuilder().buildDelete(filter));
    }
}
