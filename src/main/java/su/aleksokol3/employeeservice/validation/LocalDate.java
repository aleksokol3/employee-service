package su.aleksokol3.employeeservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.format.DateTimeParseException;

/**
 * The annotated element must be a string representation of date.
 * <p>
 * Accepts {@link CharSequence}.
 *
 * @see java.time.LocalDate#parse(CharSequence)
 */
@Constraint(validatedBy = LocalDate.LocalDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDate {
    String message() default "{local.date}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class LocalDateValidator implements ConstraintValidator<LocalDate, CharSequence> {
        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            try {
                java.time.LocalDate.parse(value);
                return true;
            } catch (DateTimeParseException ex) {
                return false;
            }
        }
    }
}
