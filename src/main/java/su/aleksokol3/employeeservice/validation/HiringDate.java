package su.aleksokol3.employeeservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must be {@link java.time.LocalDate}.
 */
@Constraint(validatedBy = HiringDateValidator.class)
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HiringDate {
    String message() default "{hiringdate.range}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
