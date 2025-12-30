package su.aleksokol3.employeeservice.service.implementaion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import su.aleksokol3.employeeservice.util.DataUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test find by id functionality")
    void givenId_whenFindById_thenEmployeeIsReturned() {
        // given
        Employee entity = DataUtils.getJuanRodriguezPersisted();
        ReadEmployeeDto readDto = DataUtils.getJuanRodriguezReadDto();
        Mockito.when(employeeMapper.entityToReadDto(any(Employee.class)))
                        .thenReturn(readDto);
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(entity));
        // when
        ReadEmployeeDto obtainedEmployee = serviceUnderTest.findById(entity.getId());
        // then
        assertThat(obtainedEmployee).isNotNull();
        assertThat(obtainedEmployee.id()).isEqualTo(entity.getId());
    }

    @Test
    @DisplayName("Test find by incorrect id functionality")
    void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        UUID incorrectId = UUID.randomUUID();
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenThrow(NotFoundException.class);
        // when - then
        assertThrows(NotFoundException.class, () -> serviceUnderTest.findById(incorrectId));
    }

    @Test
    @DisplayName("Test find by filter functionality")
    void givenFilter_whenFindByFilter_thenEmployeesAreReturned() {
        // given
        SearchEmployeeFilter filter = SearchEmployeeFilter.builder().build();
        Pageable pageable = PageRequest.of(0, 10);
        List<Employee> employees = List.of(
                DataUtils.getJuanRodriguezPersisted(),
                DataUtils.getIvanAlonsoPersisted(),
                DataUtils.getMariaAlonsoPersisted()
        );
        Page<Employee> employeesPage = new PageImpl<>(employees);
        Mockito.when(employeeRepository.findAll(ArgumentMatchers.<Specification<Employee>>any(), any(Pageable.class)))
                .thenReturn(employeesPage);
        // when
        PageDto<ReadEmployeeDto> pageDto = serviceUnderTest.findBy(filter, pageable);
        // then
        assertThat(pageDto).isNotNull();
        assertThat(pageDto.getEmployees()).hasSize(3);
    }

    @Test
    @DisplayName("Test find by unsuitable filter functionality")
    void givenFilter_whenFindByFilter_thenEmployeesAreNotFound() {
        // given
        SearchEmployeeFilter filter = SearchEmployeeFilter.builder().build();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> employeesPage = new PageImpl<>(Collections.emptyList());
        Mockito.when(employeeRepository.findAll(ArgumentMatchers.<Specification<Employee>>any(), any(Pageable.class)))
                .thenReturn(employeesPage);
        // when
        PageDto<ReadEmployeeDto> pageDto = serviceUnderTest.findBy(filter, pageable);
        // then
        assertThat(pageDto.getEmployees()).isEmpty();
    }

    @Test
    @DisplayName("Test save employee functionality")
    void givenEmployee_whenSaveEmployee_thenRepositoryIsCalled() {
        // given
        CreateEmployeeDto createDto = DataUtils.getJuanRodriguezCreateDto();
        Employee transientEntity = DataUtils.getJuanRodriguezTransient();
        Employee persistedEntity = DataUtils.getJuanRodriguezPersisted();
        Mockito.when(employeeMapper.createDtoToEntity(createDto)).thenReturn(transientEntity);
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(persistedEntity);
        // when
        UUID id = serviceUnderTest.create(createDto);
        // then
        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("Test update functionality")
    void givenPatchEmployeeDto_whenUpdate_thenRepositoryIsCalledAndUpdatedEmployeeIsReturned() {
        // given
        PatchEmployeeDto patchDto = DataUtils.getJuanRodriguezPatchDto();
        Employee transientEntity = DataUtils.getJuanRodriguezTransient();
        Employee persistedEntity = DataUtils.getJuanRodriguezPersisted();
        Employee updatedPersistedEntity = DataUtils.getJuanRodriguezUpdatedPersisted();
        ReadEmployeeDto updatedReadDto = DataUtils.getJuanRodriguezUpdatedReadDto();
        Mockito.when(employeeMapper.updateEntity(any(PatchEmployeeDto.class), any(Employee.class)))
                        .thenReturn(transientEntity);
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(persistedEntity));
        Mockito.when(employeeRepository.saveAndFlush(any(Employee.class)))
                .thenReturn(updatedPersistedEntity);
        Mockito.when(employeeMapper.entityToReadDto(any(Employee.class)))
                .thenReturn(updatedReadDto);
        // when
        ReadEmployeeDto resultReadDto = serviceUnderTest.update(persistedEntity.getId(), patchDto);

        // then
        assertThat(resultReadDto).isNotNull();
        assertThat(resultReadDto.id()).isEqualTo(persistedEntity.getId());
        assertThat(resultReadDto.patronymic()).isNull();
        assertThat(resultReadDto.salary()).isEqualTo(updatedPersistedEntity.getSalary());
        verify(employeeRepository, times(1)).findById(any(UUID.class));
        verify(employeeRepository, times(1)).saveAndFlush(any(Employee.class));
    }
    @Test
    @DisplayName("Test update with incorrect id functionality")
    void givenPatchEmployeeDtoWithIncorrectId_whenUpdate_thenNothingHappened() {
        // given
        PatchEmployeeDto patchDto = DataUtils.getJuanRodriguezPatchDto();
        UUID incorrectId = UUID.randomUUID();
        Mockito.when(employeeRepository.findById(any(UUID.class)))
                .thenThrow(NotFoundException.class);
        // when - then
        assertThrows(NotFoundException.class, () -> serviceUnderTest.update(incorrectId, patchDto));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test delete by id functionality")
    void givenId_whenDeleteById_thenDeleteMethodCalls() {
        // given
        UUID id = UUID.randomUUID();
        Mockito.doNothing().when(employeeRepository).deleteById(any(UUID.class));
        // when
        serviceUnderTest.deleteById(id);
        // then
        verify(employeeRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Test delete by filter functionality")
    void givenFilter_whenDeleteByFilter_thenDeleteMethodCalls() {
        // given
        DeleteEmployeeFilter filter = DeleteEmployeeFilter.builder().build();
        Mockito.doReturn(0L).when(employeeRepository).delete(ArgumentMatchers.<Specification<Employee>>any());
        // when
        serviceUnderTest.deleteBy(filter);
        // then
        verify(employeeRepository, times(1)).delete(ArgumentMatchers.<Specification<Employee>>any());
    }
}