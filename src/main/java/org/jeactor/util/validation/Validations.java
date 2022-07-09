package org.jeactor.util.validation;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import jakarta.validation.ValidationException;

/** Validation utility. */
public final class Validations {
    private Validations() {}

    /**
     * Validates that the accepted objects are not null.
     * 
     * @param objects object varargs to validate
     * @throws ValidationException if objects is null or one of it's members is null. If objects is empty, an exception won't be thrown
     */
    @SafeVarargs
    public static void validateNotNull(Object... objects) throws ValidationException {
        if (null == objects || 
                (objects.length > 0 && !(Stream.of(objects).filter((o)->null == o).collect(Collectors.toSet()).isEmpty())))
            throw new ValidationException("null is illegal");
    }

    /**
     * Validates that the accepted objects are positive integers.
     * 
     * @param objects object varargs to validate
     * @throws ValidationException if objects is null or one of it's members is null, negative integer or 0. If objects is empty, an exception won't be thrown
     */
    @SafeVarargs
    public static void validatePositive(Integer... integers) throws ValidationException {
        validateNotNull((Object[]) integers);
        if (integers.length > 0 && !(Stream.of(integers).filter((i)->0 >= i).collect(Collectors.toSet()).isEmpty()))
            throw new ValidationException("integers must be positive");
    }
}
