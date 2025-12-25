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
 * This annotation must use only with {@link BigDecimal} annotation.
 * <p>
 * The annotated element must be a number whose value must be higher or
 * equal to the specified minimum.
 * <p>
 * Accepts {@link CharSequence}, which must match format of {@link java.math.BigDecimal} to be valid.
 * <p>
 * null elements are considered valid.
 */
@Constraint(validatedBy = MinString.MinStringToBigDecimalValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinString {
    String message() default "{min}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    long value();

    class MinStringToBigDecimalValidator implements ConstraintValidator<MinString, CharSequence> {
        private long minValue;
        @Override
        public void initialize(MinString min) {
            this.minValue = min.value();
        }
        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }
            try {
                java.math.BigDecimal bdValue = new java.math.BigDecimal(value.toString());
                return bdValue.compareTo(java.math.BigDecimal.valueOf(minValue)) >= 0;
            } catch (NumberFormatException ex) {
                return true;
            }
        }
    }
}
