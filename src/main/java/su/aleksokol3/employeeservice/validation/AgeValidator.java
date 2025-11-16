package su.aleksokol3.employeeservice.validation;

/**
 * Check that the age is in range between two fields: 'ageFrom' and 'ageTo',
 * or if one of the fields is not present:
 * greater or equal than 'ageFrom' or less or equal than 'ageTo'.
 */
public class AgeValidator extends RangeValidator<Age> {

    AgeValidator() {
        setClazz(Age.class);
    }
}
