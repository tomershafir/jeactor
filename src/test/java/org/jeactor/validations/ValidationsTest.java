package org.jeactor.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.jeactor.validation.Validations;
import jakarta.validation.ValidationException;

/** Unit test of Validations */
public class ValidationsTest {
    /** Creates default unit test instance. */
    public ValidationsTest() {}

    /** The method tests that validateNotNull() with null input throws ValidationException. */
    @Test
    public void testValidateNotNullWithNullObjectsThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validateNotNull((Object[]) null));
    }

    /** The method tests that validateNotNull() with empty input does not throw validation exception. */
    @Test
    public void testValidateNotNullWithEmptyObjectsDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validateNotNull(new Object[0]));
    }

    /** The method tests that validateNotNull() with a non null input with null element throws ValidationException. */
    @Test
    public void testValidateNotNullWithNullElementThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validateNotNull(new Object[]{null}));
    }

    /** The method tests that validateNotNull() with a non null input with non null elements does not throw validation exception. */
    @Test
    public void testValidateNotNullWithNonNullElementsDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validateNotNull(new Object[]{new Object()}));
    }

    /** The method tests that validatePositive() with null input throws ValidationException. */
    @Test
    public void testValidatePositiveWithNullObjectsThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive((Integer[]) null));
    }

    /** The method tests that validatePositive() with empty input does not throw validation exception. */
    @Test
    public void testValidatePositiveWithEmptyObjectsDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validatePositive(new Integer[0]));
    }

    /** The method tests that validatePositive() with a non null input with null element throws ValidationException. */
    @Test
    public void testValidatePositiveWithNullElementThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive(new Integer[]{null}));
    }

    /** The method tests that validatePositive() with a non null input with negative integer throws ValidationException. */
    @Test
    public void testValidatePositiveWithNegativeIntThrowsValidationException() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive(new Integer[]{-1}));
    }

    /** The method tests that validatePositive() with a non null input with zero throws ValidationException. */
    @Test
    public void testValidatePositiveWith0ThrowsValidationExcpetion() {
        assertThrows(ValidationException.class, ()->Validations.validatePositive(new Integer[]{0}));
    }

    /** The method tests that validatePositive() with non null input with positive integer does not throw validation exception. */
    @Test
    public void testValidatePositiveWithPositiveIntDoesNotThrowValidationException() {
        assertDoesNotThrow(()->Validations.validatePositive(new Integer[1]));
    }
}
