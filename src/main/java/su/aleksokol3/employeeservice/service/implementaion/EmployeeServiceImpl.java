package su.aleksokol3.employeeservice.service.implementaion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.exception.NotFoundException;
import su.aleksokol3.employeeservice.model.api.filter.EmployeeFilter;
import su.aleksokol3.employeeservice.model.api.mapper.EmployeeMapper;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.repository.EmployeeRepository;
import su.aleksokol3.employeeservice.service.EmployeeService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    @Override
    public List<ReadEmployeeDto> findBy(EmployeeFilter filter, Pageable pageable) {
        List<Employee> byFilter = employeeRepository.findByFilter(filter, pageable);
        return byFilter.stream().map(employeeMapper::entityToReadDto).toList();
    }

    @Override
    public ReadEmployeeDto findById(UUID id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::entityToReadDto).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Пользователь с id %s не найден".formatted(id)));
    }

    @Override
    public UUID create(@Valid CreateEmployeeDto dto) {
        Employee employee = employeeMapper.createDtoToEntity(dto);
        employee.setHiringDate(LocalDate.from(Instant.now().atZone(ZoneOffset.UTC)));
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    @Override
    public void update(UUID id, @Valid PatchEmployeeDto dto) {
        employeeRepository.findById(id).ifPresent(
                employee -> {
                    dto.firstName().ifPresent(employee::setFirstName);
                    dto.lastName().ifPresent(employee::setLastName);
                    dto.patronymic().ifPresent(employee::setPatronymic);
                    dto.age().ifPresent(employee::setAge);
                    dto.salary().ifPresent(employee::setSalary);
                    dto.hiringDate().ifPresent(employee::setHiringDate);

                    employeeRepository.save(employee);
                }
        );
    }

    @Override
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteBy(EmployeeFilter filter) {
        employeeRepository.deleteByFilter(filter);
    }
}
