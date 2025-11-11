package su.aleksokol3.employeeservice.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankOrNullValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankOrNull {
    String message() default "notBlankOrNull";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
