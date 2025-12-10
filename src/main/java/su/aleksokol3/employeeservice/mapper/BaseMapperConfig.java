package su.aleksokol3.employeeservice.mapper;

import org.mapstruct.*;

/**
 * Base configs for other mappers
 */
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true
        )
)
public interface BaseMapperConfig {
}
