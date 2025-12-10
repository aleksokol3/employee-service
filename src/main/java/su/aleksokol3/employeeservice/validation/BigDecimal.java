package su.aleksokol3.employeeservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must be a string representation of BigDecimal.
 * <p>
 * Accepts {@link CharSequence}.
 *
 * @see java.math.BigDecimal#BigDecimal(String)
 */
@Constraint(validatedBy = BigDecimal.BigDecimalValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimal {
    String message() default "{big.decimal}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    class BigDecimalValidator implements ConstraintValidator<BigDecimal, CharSequence> {
        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            try {
                new java.math.BigDecimal(value.toString());
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
}
