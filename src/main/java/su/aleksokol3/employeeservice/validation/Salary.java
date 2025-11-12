package su.aleksokol3.employeeservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SalaryValidator.class)
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Salary {
    String message() default "{salary.range}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
