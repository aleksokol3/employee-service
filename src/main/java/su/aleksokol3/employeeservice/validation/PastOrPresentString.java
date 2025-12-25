package su.aleksokol3.employeeservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Clock;
import java.time.format.DateTimeParseException;


/**
 * This annotation must use only with {@link LocalDate} annotation.
 * <p>
 * The annotated element must be a date in the past or in the present.
 * <p>
 * Now is defined by the ClockProvider attached to the Validator or ValidatorFactory.
 * The default clockProvider defines the current time according to the virtual machine,
 * applying the current default time zone if needed.
 * <p>
 * Accepts {@link CharSequence}, which must match format of {@link java.time.LocalDate} to be valid.
 * <p>
 * null elements are considered valid.
 */
@Constraint(validatedBy = PastOrPresentString.PastOrPresentValidatorForStringLocalDate.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastOrPresentString {
    String message() default "{past.or.present}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class PastOrPresentValidatorForStringLocalDate implements ConstraintValidator<PastOrPresentString, CharSequence> {
        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            Clock clock = context.getClockProvider().getClock();
            java.time.LocalDate now = java.time.LocalDate.now(clock);
            java.time.LocalDate validatedPastOrPresentDate;
            try {
                validatedPastOrPresentDate = java.time.LocalDate.parse(value);
            } catch (DateTimeParseException ex) {
                return true;
            }
            return !validatedPastOrPresentDate.isAfter(now);
        }
    }
}
