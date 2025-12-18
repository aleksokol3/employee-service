package su.aleksokol3.employeeservice.mapper;

import org.mapstruct.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "modifiedAt", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "modifiedBy", ignore = true)
@Mapping(target = "version", ignore = true)
@Mapping(target = "id", ignore = true)
public @interface IgnoreIdAuditVersionMappings {
}
