package su.aleksokol3.employeeservice.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * Mapper to unwrap JsonNullable to entity and vice-versa.
 */
@Mapper(config = BaseMapperConfig.class)
public abstract class JsonNullableMapper {

    /**
     * Unwrap entity from JsonNullable
     * @param jsonNullable {@link JsonNullable}, containing entity
     * @return {@link T} entity
     * @param <T> Type of entity
     */
    public <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    /**
     * Checks if JsonNullable contains entity
     * @param nullable {@link JsonNullable} that checks
     * @return {@code true} if nullable contains entity
     * @param <T> Type of entity
     */
    @Condition
    public <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}
