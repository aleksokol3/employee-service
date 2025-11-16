package su.aleksokol3.employeeservice.validation;

/**
 * Check that the salary is in range between two fields: 'salaryFrom' and 'salaryTo',
 * or if one of the fields is not present:
 * greater or equal than 'salaryFrom' or less or equal than 'salaryTo'.
 */
public class SalaryValidator extends RangeValidator<Salary> {
    public SalaryValidator() {
        setClazz(Salary.class);
    }
}
