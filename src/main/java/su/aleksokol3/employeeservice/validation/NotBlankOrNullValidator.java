package su.aleksokol3.employeeservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankOrNullValidator implements ConstraintValidator<NotBlankOrNull, CharSequence> {

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        return charSequence == null || !charSequence.toString().trim().isEmpty();
    }
}
