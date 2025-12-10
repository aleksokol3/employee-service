package su.aleksokol3.employeeservice.util;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class SpecificationBuilder<T> {
    private final List<Specification<T>> specificationList = new ArrayList<>();

    public Specification<T> build() {
        Specification<T> result = Specification.unrestricted();
        for (Specification<T> tSpecification : specificationList) {
            result = result.and(tSpecification);
        }
        return result;
    }

    protected SpecificationBuilder<T> and(Specification<T> spec) {
        specificationList.add(spec);
        return this;
    }

//    protected <V> Specification<T> equals(String fieldName, V value) {
//        return (root, query, cb) -> value != null ? cb.equal(root.get(fieldName), value) : cb.conjunction();
//    }

    protected Specification<T> like(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.trim().isEmpty()) {
                return cb.conjunction();
            } else {
                return cb.like(cb.lower(root.get(fieldName)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    protected <V extends Comparable<? super V>> Specification<T> between(String fieldName, V from, V to) {
        return (root, query, cb) -> {
            if (from == null && to == null) {
                return cb.conjunction();
            }
            if (from != null && to != null) {
                return cb.between(root.get(fieldName), from, to);
            }
            if (from != null) {
                return cb.greaterThanOrEqualTo(root.get(fieldName), from);
            }
            return cb.lessThanOrEqualTo(root.get(fieldName), to);
        };
    }

//    protected Specification<T> isTrue(String fieldName, Boolean value) {
//        return (root, query, cb) -> {
//            if (value == null) {
//                return cb.conjunction();
//            }
//            return value ? cb.isTrue(root.get(fieldName)) : cb.isFalse(root.get(fieldName));
//        };
//    }
//
//    protected <V> Specification<T> in(String fieldName, Collection<V> values) {
//        return (root, query, cb) -> {
//            if (values == null || values.isEmpty()) {
//                return cb.conjunction();
//            }
//            return root.get(fieldName).in(values);
//        };
//    }
//
//    protected <V> Specification<T> joinEquals(String joinField, String fieldName, V value) {
//        return (root, query, cb) -> {
//            if (value == null) {
//                return cb.conjunction();
//            }
//            Join<T, ?> join = root.join(joinField, JoinType.INNER);
//            return cb.equal(join.get(fieldName), value);
//        };
//    }
}
