package su.aleksokol3.employeeservice.validation;

public class SalaryValidator extends RangeValidator<Salary> {
    public SalaryValidator() {
        setClazz(Salary.class);
    }
}
