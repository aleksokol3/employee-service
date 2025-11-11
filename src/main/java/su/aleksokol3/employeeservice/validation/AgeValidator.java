package su.aleksokol3.employeeservice.validation;

public class AgeValidator extends RangeValidator<Age> {

    AgeValidator() {
        setClazz(Age.class);
    }
}
