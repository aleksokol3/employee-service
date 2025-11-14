package su.aleksokol3.employeeservice.model.api.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.entity.Employee;
import su.aleksokol3.employeeservice.util.DataUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {EmployeeMapperImpl.class, JsonNullableMapperImpl.class})
@RequiredArgsConstructor
class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper;

    @Test
    void entityToReadDto() {
        // given
        Employee entity = DataUtils.getJuanRodriguezPersisted();
        // when
        ReadEmployeeDto dto = employeeMapper.entityToReadDto(entity);
        // then
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(entity.getId());
        assertThat(dto.hiringDate()).isEqualTo(entity.getHiringDate());
    }

    @Test
    void createDtoToEntity() {
        // given
        CreateEmployeeDto dto = DataUtils.getJuanRodriguezCreateDto();
        // when
        Employee entity = employeeMapper.createDtoToEntity(dto);
        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getLastName()).isEqualTo(dto.lastName());
        assertThat(entity.getHiringDate()).isEqualTo(dto.hiringDate());
    }

    @Test
    void updateEntity() {
        // given
        PatchEmployeeDto dto = DataUtils.getJuanRodriguezPatchDto();
        Employee entity = DataUtils.getJuanRodriguezPersisted();
        // when
        employeeMapper.updateEntity(dto, entity);
        // then
        assertThat(entity.getSalary()).isEqualTo(dto.salary().get());
    }
}