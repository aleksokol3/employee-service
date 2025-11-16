package su.aleksokol3.employeeservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Checks that {@link CharSequence} is not blank or null.
 */
public class NotBlankOrNullValidator implements ConstraintValidator<NotBlankOrNull, CharSequence> {

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        return charSequence == null || !charSequence.toString().trim().isEmpty();
    }
}
