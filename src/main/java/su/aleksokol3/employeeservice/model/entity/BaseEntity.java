package su.aleksokol3.employeeservice.model.entity;

import java.io.Serializable;

/**
 *
 * @param <T> Type of entity. Should be {@link Serializable}
 */
public interface BaseEntity<T extends Serializable> {
    T getId();
    void setId(T id);
}
