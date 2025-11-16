package su.aleksokol3.employeeservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must be an integer.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their respective
 *     wrappers</li>
 * </ul>
 */
@Constraint(validatedBy = AgeValidator.class)
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    String message() default "{age.range}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
