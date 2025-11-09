package su.aleksokol3.employeeservice.service.implementaion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.api.exception.NotFoundException;
import su.aleksokol3.employeeservice.model.api.filter.EmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.repository.EmployeeRepository;
import su.aleksokol3.employeeservice.util.DataUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test find by id functionality")
    void givenId_whenFindById_thenEmployeeIsReturned() {
        // given
        Employee juanRodriguezPersisted = DataUtils.getJuanRodriguezPersisted();
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(juanRodriguezPersisted));
        // when
        ReadEmployeeDto obtainedEmployee = serviceUnderTest.findById(juanRodriguezPersisted.getId());
        // then
        assertThat(obtainedEmployee).isNotNull();
    }

    @Test
    @DisplayName("Test find by id functionality")
    void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        UUID incorrectId = UUID.randomUUID();
        Employee juanRodriguezPersisted = DataUtils.getJuanRodriguezPersisted();
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenThrow(NotFoundException.class);
        // when - then
        assertThrows(NotFoundException.class, () -> serviceUnderTest.findById(incorrectId));
    }

    @Test
    void givenFilter_whenFindByFilter_thenEmployeesAreReturned() {
        // given
        EmployeeFilter filter = EmployeeFilter.builder().build();
        Pageable pageable = PageRequest.of(0, 10);
        List<Employee> employees = List.of(
                DataUtils.getJuanRodriguezPersisted(),
                DataUtils.getIvanAlonsoPersisted(),
                DataUtils.getMariaAlonsoPersisted()
        );
        Mockito.when(employeeRepository.findByFilter(any(EmployeeFilter.class), any(Pageable.class)))
                .thenReturn(employees);
        // when
        List<ReadEmployeeDto> obtainedDtos = serviceUnderTest.findBy(filter, pageable);
        // then
        assertThat(obtainedDtos).isNotNull();
        assertThat(obtainedDtos.size()).isEqualTo(3);
    }

    @Test
    void givenFilter_whenFindByFilter_thenEmployeesAreNotFound() {
        // given
        EmployeeFilter filter = EmployeeFilter.builder().build();
        Pageable pageable = PageRequest.of(0, 10);
        List<Employee> employees = Collections.emptyList();
        Mockito.when(employeeRepository.findByFilter(any(EmployeeFilter.class), any(Pageable.class)))
                .thenReturn(employees);
        // when
        List<ReadEmployeeDto> obtainedDtos = serviceUnderTest.findBy(filter, pageable);
        // then
        assertThat(CollectionUtils.isEmpty(obtainedDtos)).isTrue();
    }

    @Test
    @DisplayName("Test save employee functionality")
    void givenEmployee_whenSaveEmployee_thenRepositoryIsCalled() {
        // given
//        Employee employeeToSave = DataUtils.getJuanRodriguezTransient();
        CreateEmployeeDto createEmployeeDto = DataUtils.getJuanRodriguezCreateDto();
        Employee employeePersisted = DataUtils.getJuanRodriguezPersisted();
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employeePersisted);
        // when
        UUID savedEmployeeId = serviceUnderTest.create(createEmployeeDto);
        // then
        assertThat(savedEmployeeId).isNotNull();
    }

//    @Test
//    @DisplayName("Test save employee with negative age functionality")
//    void givenEmployeeDtoWithNegativeAge_whenSaveEmployee_thenExceptionIsThrown() {
//        // given
//        CreateEmployeeDto createEmployeeDto = DataUtils.getJuanRodriguezInvalidCreateDto();
//        Mockito.when(employeeRepository.save(any(Employee.class)))
//                .thenThrow(MethodArgumentNotValidException.class);
//        // when - then
//        assertThrows(MethodArgumentNotValidException.class, () -> serviceUnderTest.create(createEmployeeDto));
//        verify(employeeRepository, never()).save(any(Employee.class));
//    }

    @Test
    @DisplayName("Test update functionality")
    void givenPatchEmployeeDto_whenUpdate_thenRepositoryIsCalled() {
        // given
        PatchEmployeeDto patchEmployeeDto = PatchEmployeeDto.builder()
                .firstName(JsonNullable.of("Иван"))
                .patronymic(JsonNullable.of(null))
                .lastName(JsonNullable.of("Иванов"))
                .age(JsonNullable.undefined())
                .salary(JsonNullable.of(BigDecimal.valueOf(14.59)))
                .hiringDate(JsonNullable.undefined())
                .build();
        Employee employeePersisted = DataUtils.getJuanRodriguezPersisted();
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(employeePersisted));
        Mockito.when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employeePersisted);
        // when
//        serviceUnderTest.update(employeePersisted.getId(), patchEmployeeDto);
        Employee updatedEmployee = employeeRepository.findById(employeePersisted.getId()).orElse(null);

        // then
        assertThat(updatedEmployee).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    @Test
    @DisplayName("Test update with incorrect id functionality")
    void givenPatchEmployeeDtoWithIncorrectId_whenUpdate_thenNothingHappened() {
        // given
        PatchEmployeeDto patchEmployeeDto = PatchEmployeeDto.builder()
                .firstName(JsonNullable.of("Иван"))
                .lastName(JsonNullable.of("Иванов"))
                .build();
        UUID incorrectId = UUID.randomUUID();
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());
        // when
        serviceUnderTest.update(incorrectId, patchEmployeeDto);
        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void givenId_whenDeleteByFilter_thenDeleteMethodCalls() {
        // given
        UUID id = UUID.randomUUID();
        Mockito.doNothing().when(employeeRepository).deleteById(any(UUID.class));
        // when
        serviceUnderTest.deleteById(id);
        // then
        verify(employeeRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void givenFilter_whenDeleteByFilter_thenDeleteMethodCalls() {
        // given
        EmployeeFilter filter = EmployeeFilter.builder().build();
        Mockito.doNothing().when(employeeRepository).deleteByFilter(filter);
        // when
        serviceUnderTest.deleteBy(filter);
        // then
        verify(employeeRepository, times(1)).deleteByFilter(any(EmployeeFilter.class));
    }
}