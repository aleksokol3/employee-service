package su.aleksokol3.employeeservice.validation;

/**
 * Check that the hiringDate is in range between two fields: 'hiringDateFrom' and 'hiringDateTo',
 * or if one of the fields is not present:
 * greater or equal than 'hiringDateFrom' or less or equal than 'hiringDateTo'.
 */
public class HiringDateValidator extends RangeValidator<HiringDate> {
    public HiringDateValidator() {
        setClazz(HiringDate.class);
    }
}
