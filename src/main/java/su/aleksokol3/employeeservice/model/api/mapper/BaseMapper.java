package su.aleksokol3.employeeservice.model.api.mapper;

import org.mapstruct.Mapper;
import su.aleksokol3.employeeservice.model.entity.BaseEntity;

import java.io.Serializable;

@Mapper(config = BaseMapperConfig.class)
public abstract class BaseMapper {
    <T extends Serializable> T entityToId(BaseEntity<T> entity) {
        return entity.getId();
    }
}
