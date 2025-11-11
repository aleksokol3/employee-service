package su.aleksokol3.employeeservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import su.aleksokol3.employeeservice.model.api.filter.DeleteEmployeeFilter;
import su.aleksokol3.employeeservice.model.api.filter.SearchEmployeeFilter;
import su.aleksokol3.employeeservice.model.entity.Employee;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterEmployeeRepositoryImpl implements FilterEmployeeRepository {

    private final EntityManager entityManager;

    @Override
    public List<Employee> findByFilter(SearchEmployeeFilter filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
        Root<Employee> employee = criteria.from(Employee.class);
        List<Predicate> predicates = createPredicatesSearch(filter, cb, employee);
        criteria.select(employee);
//        if (!predicates.isEmpty()) {
//            criteria.where(predicates.toArray(Predicate[]::new));
//        }
        criteria.where(predicates.toArray(Predicate[]::new));
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int limit = pageable.getPageSize();

        return entityManager.createQuery(criteria)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public void deleteByFilter(DeleteEmployeeFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Employee> criteria = cb.createCriteriaDelete(Employee.class);
        Root<Employee> employee = criteria.from(Employee.class);
        List<Predicate> predicates = createPredicatesDelete(filter, cb, employee);
        if (predicates.isEmpty()) return;                       // cannot delete all entities!
        criteria.where(predicates.toArray(Predicate[]::new));

        entityManager.createQuery(criteria).executeUpdate();
    }

    private List<Predicate> createPredicatesSearch(SearchEmployeeFilter filter, CriteriaBuilder cb, Root<Employee> employee) {

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
        if (filter.ageFrom() != null) {
            predicates.add(cb.between(employee.get("age"), filter.ageFrom(), filter.ageTo()));
        }
        if (filter.salaryFrom() != null) {
            predicates.add(cb.between(employee.get("salary"), filter.salaryFrom(), filter.salaryTo()));
        }
        if (filter.hiringDateFrom() != null) {
            predicates.add(cb.between(employee.get("hiringDate"), filter.hiringDateFrom(), filter.hiringDateTo()));
        }
        return predicates;
    }

    private List<Predicate> createPredicatesDelete(DeleteEmployeeFilter filter, CriteriaBuilder cb, Root<Employee> employee) {

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
        if (filter.ageFrom() != null) {
            predicates.add(cb.between(employee.get("age"), filter.ageFrom(), filter.ageTo()));
        }
        if (filter.hiringDateFrom() != null) {
            predicates.add(cb.between(employee.get("hiringDate"), filter.hiringDateFrom(), filter.hiringDateTo()));
        }
        return predicates;
    }
}
