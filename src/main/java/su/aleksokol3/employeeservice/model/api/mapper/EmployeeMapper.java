package su.aleksokol3.employeeservice.model.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.entity.Employee;

@Mapper(config = BaseMapperConfig.class, uses = JsonNullableMapper.class)
public interface EmployeeMapper {

    ReadEmployeeDto entityToReadDto(Employee employee);

    Employee createDtoToEntity(CreateEmployeeDto dto);

    void updateEntity(PatchEmployeeDto dto, @MappingTarget Employee entity);
}
