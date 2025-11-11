package su.aleksokol3.employeeservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class RangeValidator<T extends Annotation> implements ConstraintValidator<T, Object> {
    private Class<T> clazz;

    void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return isValidGeneric(value);
    }

    private <V extends Comparable<V>> boolean isValidGeneric(Object value) {
        try {
            Class<?> filterClazz = value.getClass();
            Annotation[] annotations = filterClazz.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == clazz) {
                    String name = clazz.getSimpleName();
                    name = name.substring(0,1).toLowerCase() + name.substring(1);
                    Field fromField = filterClazz.getDeclaredField(name + "From");
                    fromField.setAccessible(true);
                    V from = (V) fromField.get(value);
                    Field toField = filterClazz.getDeclaredField(name + "To");
                    toField.setAccessible(true);
                    V to = (V) toField.get(value);
                    if (from == null && to == null) {
                        return true;
                    }
                    if (from == null || to == null) {
                        return false;
                    }
                    return from.compareTo(to) <= 0;
                }
            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
