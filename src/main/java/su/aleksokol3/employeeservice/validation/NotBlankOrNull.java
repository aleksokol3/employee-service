package su.aleksokol3.employeeservice.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must not be {@code null} and must contain at least one non-whitespace character,
 * or must be {@code null}.
 * Accepts {@code CharSequence}.
 *
 * @see Character#isWhitespace(char)
 */
@Constraint(validatedBy = NotBlankOrNullValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankOrNull {
    String message() default "{not.blank.or.null}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
