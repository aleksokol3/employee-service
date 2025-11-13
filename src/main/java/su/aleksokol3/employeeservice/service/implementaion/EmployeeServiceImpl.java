package su.aleksokol3.employeeservice.service.implementaion;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.aleksokol3.employeeservice.model.api.dto.PageDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.exception.NotFoundException;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.mapper.EmployeeMapper;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.repository.EmployeeRepository;
import su.aleksokol3.employeeservice.service.EmployeeService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

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
        Specification<Employee> spec = buildSpecSearch(filter);
        Pageable pageableSortIgnoreCase = sortIgnoreCase(pageable);
        Page<Employee> employeesByFilterPage = employeeRepository.findAll(spec, pageableSortIgnoreCase);

        long totalElements = employeesByFilterPage.getTotalElements();
        List<ReadEmployeeDto> readEmployeeDtoList = employeesByFilterPage.stream()
                .map(employeeMapper::entityToReadDto)
                .toList();
        return new PageDto<>(readEmployeeDtoList, totalElements, Instant.now());
    }

    @Override
    public ReadEmployeeDto findById(UUID id) {
        log.info("Finding employee by id: {}", id);
        return employeeRepository.findById(id)
                .map(employeeMapper::entityToReadDto)
                .orElseThrow(() -> new NotFoundException("employee.not.found.exception", "employee", id.toString()));
    }

    @Transactional
    @Override
    public UUID create(CreateEmployeeDto dto) {
        log.info("Creating new employee: {}", dto);
        Employee employee = employeeMapper.createDtoToEntity(dto);
        employee.setHiringDate(LocalDate.from(Instant.now().atZone(ZoneOffset.UTC)));
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    @Transactional
    @Override
    public ReadEmployeeDto update(UUID id, PatchEmployeeDto dto) {
        log.info("Updating employee: {}", dto);
        return employeeRepository.findById(id)
                        .map(employee -> employeeMapper.updateEntity(dto, employee))
                        .map(employeeRepository::saveAndFlush)
                        .map(employeeMapper::entityToReadDto)
                        .orElseThrow(() -> new NotFoundException("employee.not.found.exception", "employee", id.toString()));
//        employeeRepository.findById(id).ifPresent(
//                employee -> {
//                    employeeMapper.updateEntity(dto, employee);
//                    employeeRepository.save(employee);
//                }
//        );
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
        Specification<Employee> spec = buildSpecDelete(filter);
        employeeRepository.delete(spec);
    }

    private Specification<Employee> buildSpecSearch(SearchEmployeeFilter filter) {
        return SpecBuilder.buildSpec(filter);
//        return (root, query, cb) -> cb.and(
//                filter.firstName() == null ? cb.conjunction() : cb.like(root.get("firstName"), filter.firstName()),
//                filter.patronymic() == null ? cb.conjunction() : cb.like(root.get("patronymic"), filter.patronymic()),
//                filter.lastName() == null ? cb.conjunction() : cb.like(root.get("lastName"), filter.lastName()),
//                filter.ageFrom() == null ? cb.conjunction() : cb.between(root.get("age"), filter.ageFrom(), filter.ageTo()),
//                filter.salaryFrom() == null ? cb.conjunction() : cb.between(root.get("salary"), filter.salaryFrom(), filter.salaryTo()),
//                filter.hiringDateFrom() == null ? cb.conjunction() : cb.between(root.get("hiringDate"), filter.hiringDateFrom(), filter.hiringDateTo())
//        );
    }
    private Specification<Employee> buildSpecDelete(DeleteEmployeeFilter filter) {
        return SpecBuilder.buildSpec(filter);
//        return (root, query, cb) -> cb.and(
//                filter.firstName() == null ? cb.conjunction() : cb.like(root.get("firstName"), filter.firstName()),
//                filter.patronymic() == null ? cb.conjunction() : cb.like(root.get("patronymic"), filter.patronymic()),
//                filter.lastName() == null ? cb.conjunction() : cb.like(root.get("lastName"), filter.lastName()),
//                filter.ageFrom() == null ? cb.conjunction() : cb.between(root.get("age"), filter.ageFrom(), filter.ageTo()),
//                filter.hiringDateFrom() == null ? cb.conjunction() : cb.between(root.get("hiringDate"), filter.hiringDateFrom(), filter.hiringDateTo())
//        );
    }

    private Pageable sortIgnoreCase(Pageable pageable) {
        List<Sort.Order> ordersIgnoreCase = pageable.getSort().stream()
                .map(Sort.Order::ignoreCase)
                .toList();
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(ordersIgnoreCase));
    }
}
