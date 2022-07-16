package org.jeactor.util.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jeactor.util.validation.Validations;
import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;

/** Unit test of Validations. */
public class ValidationsTest {
    /** Tests that validateNotNull() with null input throws ValidationException. */
    @Test
    public void testValidateNotNullWithNullObjectsThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validateNotNull((Object[]) null));
    }

    /** Tests that validateNotNull() with empty input does not throw validation exception. */
    @Test
    public void testValidateNotNullWithEmptyObjectsDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validateNotNull(new Object[0]));
    }

    /** Tests that validateNotNull() with a non null input with null element throws ValidationException. */
    @Test
    public void testValidateNotNullWithNullElementThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validateNotNull(new Object[]{null}));
    }

    /** Tests that validateNotNull() with a non null input with non null elements does not throw validation exception. */
    @Test
    public void testValidateNotNullWithNonNullElementsDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validateNotNull(new Object[]{new Object()}));
    }

    /** Tests that validatePositive() with null input throws ValidationException. */
    @Test
    public void testValidatePositiveWithNullObjectsThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive((Integer[]) null));
    }

    /** Tests that validatePositive() with empty input does not throw validation exception. */
    @Test
    public void testValidatePositiveWithEmptyObjectsDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validatePositive(new Integer[0]));
    }

    /** Tests that validatePositive() with a non null input with null element throws ValidationException. */
    @Test
    public void testValidatePositiveWithNullElementThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive(new Integer[]{null}));
    }

    /** Tests that validatePositive() with a non null input with negative integer throws ValidationException. */
    @Test
    public void testValidatePositiveWithNegativeIntThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive(new Integer[]{-1}));
    }

    /** Tests that validatePositive() with a non null input with zero throws ValidationException. */
    @Test
    public void testValidatePositiveWith0ThrowsValidationExcpetion() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive(new Integer[]{0}));
    }

    /** Tests that validatePositive() with non null input with positive integer does not throw validation exception. */
    @Test
    public void testValidatePositiveWithPositiveIntDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validatePositive(new Integer[]{1}));
    }
}
