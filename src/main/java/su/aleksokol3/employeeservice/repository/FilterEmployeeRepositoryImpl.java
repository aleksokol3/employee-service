package su.aleksokol3.employeeservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import su.aleksokol3.employeeservice.model.api.filter.EmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterEmployeeRepositoryImpl implements FilterEmployeeRepository {

    private final EntityManager entityManager;

    @Override
    public List<Employee> findByFilter(EmployeeFilter filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
        Root<Employee> employee = criteria.from(Employee.class);
        List<Predicate> predicates = createPredicates(filter, cb, employee);
        criteria.select(employee);
//        if (!predicates.isEmpty()) {
//            criteria.where(predicates.toArray(Predicate[]::new));
//        }
        criteria.where(predicates.toArray(Predicate[]::new));
        int offset = 0;
        int limit = 10;
        if (pageable != null) {
            offset = pageable.getPageNumber() * pageable.getPageSize();
            limit = pageable.getPageSize();
        }

        return entityManager.createQuery(criteria)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public void deleteByFilter(EmployeeFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Employee> criteria = cb.createCriteriaDelete(Employee.class);
        Root<Employee> employee = criteria.from(Employee.class);
        List<Predicate> predicates = createPredicates(filter, cb, employee);
        criteria.where(predicates.toArray(Predicate[]::new));

        entityManager.createQuery(criteria).executeUpdate();
    }

    private List<Predicate> createPredicates(EmployeeFilter filter, CriteriaBuilder cb, Root<Employee> employee) {

        List<Predicate> predicates = new ArrayList<>();
        if (filter.firstName() != null) {
            predicates.add(cb.like(employee.get("firstName"), filter.firstName()));
        }
        if (filter.patronymic() != null) {
            predicates.add(cb.like(employee.get("patronymic"), filter.patronymic()));
        }
        if (filter.lastName() != null) {
            predicates.add(cb.like(employee.get("lastName"), filter.lastName()));
        }
        if (filter.age() != null) {
            predicates.add(cb.equal(employee.get("age"), filter.age()));
        }
        if (filter.salary() != null) {
            predicates.add(cb.equal(employee.get("salary"), filter.salary()));
        }
        if (filter.hiringDate() != null) {
            predicates.add(cb.equal(employee.get("hiringDate"), filter.hiringDate()));
        }
        return predicates;
    }
}
