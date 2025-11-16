package su.aleksokol3.employeeservice.model.api.mapper;

import org.mapstruct.*;

/**
 * Base configs for other mappers
 */
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
//        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
//        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
//        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
//        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
//        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
  //      builder = @Builder(disableBuilder = true)
)
public interface BaseMapperConfig {       // задать 5-6 свойств в MapperConfig'е
}
