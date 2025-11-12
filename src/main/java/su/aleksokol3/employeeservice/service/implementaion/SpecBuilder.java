package su.aleksokol3.employeeservice.service.implementaion;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SpecBuilder {

    public static <T> Specification<Employee> buildSpec(T filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Field[] fields = filter.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(filter);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                String fieldName = field.getName();
                Predicate predicate;
                if (fieldName.endsWith("From")) {
                    String columnName = fieldName.substring(0, fieldName.length()-4);
                    Object value2;
                    try {
                        Field to = filter.getClass().getDeclaredField(columnName + "To");
                        to.setAccessible(true);
                        value2 = to.get(filter);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    predicate = value == null ? cb.conjunction() : cb.between(root.get(columnName), (Comparable) value, (Comparable) value2);
                } else if (fieldName.endsWith("To")) {
                    // do nothing
                    predicate = cb.conjunction();
                } else if (field.getType() == String.class) {
                    predicate = value == null ? cb.conjunction() : cb.like(root.get(fieldName), "%" + value + "%");
                } else {
                    predicate = value == null ? cb.conjunction() : cb.equal(root.get(fieldName), value);
                }
                predicates.add(predicate);
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
