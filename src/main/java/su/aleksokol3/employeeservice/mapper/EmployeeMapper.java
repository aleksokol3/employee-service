package su.aleksokol3.employeeservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.entity.Employee;

/**
 * Mapper to convert employee entity to dto and vice-versa
 */
@Mapper(config = BaseMapperConfig.class, uses = JsonNullableMapper.class)
public interface EmployeeMapper {

    ReadEmployeeDto entityToReadDto(Employee employee);

    @IgnoreIdAuditVersionMappings
    Employee createDtoToEntity(CreateEmployeeDto dto);

    @IgnoreIdAuditVersionMappings
    Employee updateEntity(PatchEmployeeDto dto, @MappingTarget Employee entity);
}
