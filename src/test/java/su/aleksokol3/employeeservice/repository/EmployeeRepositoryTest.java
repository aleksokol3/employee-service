package su.aleksokol3.employeeservice.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import su.aleksokol3.employeeservice.UnitTestBase;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.util.DataUtils;
import su.aleksokol3.employeeservice.util.EmployeeSpecificationBuilder;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@RequiredArgsConstructor
public class EmployeeRepositoryTest extends UnitTestBase {
    private final EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }
    @Test
    @DisplayName("Test create functionality")
    public void givenEmployee_whenSave_thenEmployeeIsCreated() {
        // given
        Employee juanRodriguez = DataUtils.getJuanRodriguezTransient();
        // when
        Employee savedEmployee = employeeRepository.save(juanRodriguez);
        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test find by id functionality")
    public void givenCreatedEmployee_whenFindById_thenEmployeeIsReturned() {
        // given
        Employee juanRodriguezTransient = DataUtils.getJuanRodriguezTransient();
        employeeRepository.save(juanRodriguezTransient);
        // when
        Employee juanRodriguezObtained = employeeRepository.findById(juanRodriguezTransient.getId()).orElse(null);
        // then
        assertThat(juanRodriguezObtained).isNotNull();
        assertThat(juanRodriguezObtained.getLastName()).isEqualTo(juanRodriguezTransient.getLastName());
    }

    @Test
    @DisplayName("Test employee not found functionality")
    public void givenNotCreatedEmployee_whenFindById_thenEmptyOptionalIsReturned() {
        // given
        UUID incorrectId = UUID.randomUUID();
        // when
        Employee obtainedEmployee = employeeRepository.findById(incorrectId).orElse(null);
        // then
        assertThat(obtainedEmployee).isNull();
    }

    @Test
    @DisplayName("Test find by filter functionality")
    public void given4employeesCreated_whenFindByFilter_then2FilteredEmployeesAreReturned() {
        // given
        Employee juanRodriguezTransient = DataUtils.getJuanRodriguezTransient();
        Employee luisHernandezTransient = DataUtils.getLuisHernandezTransient();
        Employee mariaAlonsoTransient = DataUtils.getMariaAlonsoTransient();
        Employee ivanAlonsoTransient = DataUtils.getIvanAlonsoTransient();
        employeeRepository.saveAll(List.of(
                juanRodriguezTransient, luisHernandezTransient, mariaAlonsoTransient, ivanAlonsoTransient));

        SearchEmployeeFilter filter = SearchEmployeeFilter.builder()
                .lastName("Alonso")
                .build();
//        Specification<Employee> spec = SpecBuilder.buildSpec(filter);
        Specification<Employee> spec = new EmployeeSpecificationBuilder().buildSearch(filter, true);
        Pageable pageable = PageRequest.of(0, 5);
        // when
        Page<Employee> byFilter = employeeRepository.findAll(spec, pageable);
        // then
        assertThat(byFilter.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test update functionality")
    public void givenEmployeeToUpdate_whenSave_thenFirstNameIsChanged() {
        // given
        String lastName = "Kuznetsov";
        Employee juanRodriguezTransient = DataUtils.getJuanRodriguezTransient();
        employeeRepository.save(juanRodriguezTransient);
        // when
        Employee juanRodriguezToUpdate = employeeRepository.findById(juanRodriguezTransient.getId()).orElse(null);
        juanRodriguezToUpdate.setLastName(lastName);
        Employee juanRodriguezUpdated = employeeRepository.save(juanRodriguezToUpdate);
        // then
        assertThat(juanRodriguezUpdated).isNotNull();
        assertThat(juanRodriguezUpdated.getLastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("Test delete by id functionality")
    public void givenEmployee_whenDelete_thenEmployeeDeletesFromDB() {
        // given
        Employee juanRodriguezTransient = DataUtils.getJuanRodriguezTransient();
        Employee juanRodriguezCreated = employeeRepository.save(juanRodriguezTransient);
        // when
        employeeRepository.deleteById(juanRodriguezCreated.getId());
        Employee obtainedEmployee = employeeRepository.findById(juanRodriguezCreated.getId()).orElse(null);
        // then
        assertThat(obtainedEmployee).isNull();
    }

    @Test
    @DisplayName("Test delete by incorrect id functionality")
    public void whenDeleteByIncorrectId_thenNothingHappened() {
        // given
        UUID incorrectId = UUID.randomUUID();
        // when
        employeeRepository.deleteById(incorrectId);
        Employee obtainedEmployee = employeeRepository.findById(incorrectId).orElse(null);
        // then
        assertThat(obtainedEmployee).isNull();
    }

    @Test
    @DisplayName("Test delete by filter functionality")
    public void given4employeesCreated_whenDeleteByFilter_then2FilteredEmployeesAreDeletedFromDB() {
        // given
        Employee juanRodriguezTransient = DataUtils.getJuanRodriguezTransient();
        Employee luisHernandezTransient = DataUtils.getLuisHernandezTransient();
        Employee mariaAlonsoTransient = DataUtils.getMariaAlonsoTransient();
        Employee ivanAlonsoTransient = DataUtils.getIvanAlonsoTransient();
        Employee gabrielMarquezTransient = DataUtils.getGabrielMarquezTransient();

        employeeRepository.saveAll(List.of(
                juanRodriguezTransient,
                luisHernandezTransient,
                mariaAlonsoTransient,
                ivanAlonsoTransient,
                gabrielMarquezTransient));

        DeleteEmployeeFilter filter = DeleteEmployeeFilter.builder()
                .lastName("Alonso")
                .build();
//        Specification<Employee> spec = SpecBuilder.buildSpec(filter);
        Specification<Employee> spec = new EmployeeSpecificationBuilder().buildDelete(filter);

        // when
        long deleted = employeeRepository.delete(spec);
        SearchEmployeeFilter findAllFilter = SearchEmployeeFilter.builder().build();
        Specification<Employee> specAll = new EmployeeSpecificationBuilder().buildSearch(findAllFilter, true);
//        Specification<Employee> specAll = SpecBuilder.buildSpec(findAllFilter);
        Page<Employee> byFilter = employeeRepository.findAll(specAll, PageRequest.of(0, 10));
        // then
        assertThat(deleted).isEqualTo(2);
        assertThat(byFilter).isNotNull();
        assertThat(byFilter.getTotalElements()).isEqualTo(3);
    }

}
