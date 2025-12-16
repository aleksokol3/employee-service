package su.aleksokol3.employeeservice.util;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class whose implementations construct {@link Specification} of entity based on the given filter
 */
public abstract class SpecificationBuilder<T> {

    /**
     * Creates specification using SQL operator LIKE
     *
     * @param fieldName name of the entity field
     * @param value value of the entity field
     * @return {@link Specification}
     */
    protected Specification<T> like(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) {
                return cb.conjunction();
            } else {
                return cb.like(cb.lower(root.get(fieldName)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }


    /**
     * Creates specification using SQL operator BETWEEN; or '>=' if 'to' field is null; or '<=' if 'from' field is null.
     *
     * @param fieldName name of the entity field
     * @param from value of the field that has a smaller value
     * @param to value of the field that has a greater value
     * @return {@link Specification}
     * @param <V> generic type of value of the entity field, is {@link Comparable}
     */
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

    /**
     * Creates specification using DB similarity function, for example: similarity(e1_0.last_name, 'Johnson').
     * Adds similarity sorting if no sorting is specified in the request query.
     *
     * @param similarityFields list of fields by which a similarity search will be performed
     * @param queryValue value of the query string
     * @param notSorted if is {@code true}, then will be similarity sorting, otherwise sorting specified in the request query
     * @return {@link Specification}
     */
    protected Specification<T> similarity(List<String> similarityFields, String queryValue, boolean notSorted) {
        return (root, query, cb) -> {
            if (queryValue == null || queryValue.isBlank()) {
                return cb.conjunction();
            }

            Double threshold = 0.3;
            List<Expression<Double>> expressionList = new ArrayList<>();
            List<Predicate> predicateList = new ArrayList<>();
            for (String simField : similarityFields) {
                Expression<Double> expression = cb.function("similarity", Double.class, root.get(simField), cb.literal(queryValue));
                expressionList.add(expression);
                predicateList.add(cb.greaterThan(expression, threshold));
            }

            if (notSorted) {
                Expression<Double> maxSimilarity = cb.function("greatest", Double.class, expressionList.toArray(Expression[]::new));
                query.orderBy(cb.desc(maxSimilarity));
            }

            return cb.or(predicateList.toArray(Predicate[]::new));
        };
    }
}
