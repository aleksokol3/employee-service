package su.aleksokol3.employeeservice.model.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import su.aleksokol3.employeeservice.model.api.dto.employee.CreateEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.PatchEmployeeDto;
import su.aleksokol3.employeeservice.model.api.dto.employee.ReadEmployeeDto;
import su.aleksokol3.employeeservice.model.entity.Employee;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    ReadEmployeeDto entityToReadDto(Employee employee);

    Employee createDtoToEntity(CreateEmployeeDto dto);

    Employee patchDtoToEntity(PatchEmployeeDto dto);
}
