package su.aleksokol3.employeeservice.validation;

public class HiringDateValidator extends RangeValidator<HiringDate> {
    public HiringDateValidator() {
        setClazz(HiringDate.class);
    }
}
