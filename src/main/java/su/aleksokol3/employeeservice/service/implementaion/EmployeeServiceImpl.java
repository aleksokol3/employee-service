package su.aleksokol3.employeeservice.service.implementaion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private static final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    @Override
    public List<ReadEmployeeDto> findBy(EmployeeFilter filter, Pageable pageable) {
//        List<Object> predicates = new ArrayList<>();
//        if (filter.firstName() != null) predicates.add(filter.firstName());
//        if (filter.lastName() != null) predicates.add(filter.lastName());
//        if (filter.age() != null) predicates.add(filter.age());
//        if (filter.salary() != null) predicates.add(filter.salary());
//        if (filter.hiringDate() != null) predicates.add(filter.hiringDate());
//        if (filter.limit() != null) predicates.add(filter.limit());
//        if (filter.offset() != null) predicates.add(filter.offset());
//        predicates = predicates.stream().filter(Objects::nonNull).toList();

//        long offset = pageable.getOffset();
//        int pageNumber = pageable.getPageNumber();
//        int pageSize = pageable.getPageSize();
//
//        employeeRepository.findAll(pageable);
        List<Employee> byFilter = employeeRepository.findByFilter(filter, pageable);
        return byFilter.stream().map(employeeMapper::entityToReadDto).toList();
    }

    @Override
    public ReadEmployeeDto findById(UUID id) {
        return employeeRepository.findById(id).map(employeeMapper::entityToReadDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public UUID create(@Valid CreateEmployeeDto dto) {
        Employee employee = employeeMapper.createDtoToEntity(dto);
        employee.setHiringDate(LocalDate.from(Instant.now().atZone(ZoneOffset.UTC)));
        employeeRepository.saveAndFlush(employee);
        return employee.getId();
    }

    @Override
    public void update(UUID id, @Valid PatchEmployeeDto dto) {   // JsonNullable
//        employeeRepository.findById(id)
//                .map(employee -> employeeMapper.patchDtoToEntity(dto))
//                .map(employeeRepository::saveAndFlush);        // Это вообще так работает, будет ли update, а не create???

//        Optional<Employee> optEmployee = employeeRepository.findById(id);
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
//        if (optEmployee.isPresent()) {
//            Employee employee = optEmployee.get();
//
//            dto.firstName().ifPresent(employee::setFirstName);
//            dto.lastName().ifPresent(employee::setLastName);
//            dto.age().ifPresent(employee::setAge);
//            dto.salary().ifPresent(employee::setSalary);
//            dto.hiringDate().ifPresent(employee::setHiringDate);
//
//            employeeRepository.save(employee);
//        }
    }

    @Override
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteBy(EmployeeFilter filter) {
//        List<Employee> employeesToDelete = employeeRepository.findByFilter(filter);
//        employeesToDelete.forEach(employee -> employeeRepository.deleteById(employee.getId()));
        employeeRepository.deleteByFilter(filter);
    }
}
