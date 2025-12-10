package su.aleksokol3.employeeservice.mapper;

import org.mapstruct.Mapper;
import su.aleksokol3.employeeservice.model.entity.BaseEntity;

import java.io.Serializable;

/**
 * Mapper to convert entity to its id
 */
@Mapper(config = BaseMapperConfig.class)
public abstract class BaseMapper {

    /**
     *
     * @param entity {@link BaseEntity}
     * @return id of entity
     * @param <T> Type of entity, should be {@link Serializable}
     */
    public <T extends Serializable> T entityToId(BaseEntity<T> entity) {
        return entity.getId();
    }
}
