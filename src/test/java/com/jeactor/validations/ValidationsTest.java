package com.jeactor.validations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.jeactor.validation.Validations;
import jakarta.validation.ValidationException;

/** Unit test of Validations */
public class ValidationsTest {
    /** Creates default unit test instance. */
    public ValidationsTest() {}

    /** The method tests that validateNotNull() with null input throws ValidationException. */
    public void testValidateNotNullWithNullObjectsThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Validations.validateNotNull((Object[]) null);});
    }

    /** The method tests that validateNotNull() with empty input does nothing. */
    public void testValidateNotNullWithEmptyObjects() {
        Validations.validateNotNull(new Object[0]);
    }

    /** The method tests that validateNotNull() with a non null input with null element throws ValidationException. */
    public void testValidateNotNullWithNullElementThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Validations.validateNotNull(new Object[]{null});});
    }

    /** The method tests that validateNotNull() with a non null input with non null elements does nothing. */
    public void testValidateNotNullWithNonNullElements() {
        Validations.validateNotNull(new Object[]{new Object()});
    }

    /** The method tests that validatePositive() with null input throws ValidationException. */
    public void testValidatePositiveWithNullObjectsThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Validations.validatePositive((Integer[]) null);});
    }

    /** The method tests that validatePositive() with empty input does nothing. */
    public void testValidatePositiveWithEmptyObjects() {
        Validations.validatePositive(new Integer[0]);
    }

    /** The method tests that validatePositive() with a non null input with null element throws ValidationException. */
    public void testValidatePositiveWithNullElementThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Validations.validatePositive(new Integer[]{null});});
    }

    /** The method tests that validatePositive() with a non null input with negative integer throws ValidationException. */
    public void testValidatePositiveWithNegativeIntThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Validations.validatePositive(new Integer[]{-1});});
    }

    /** The method tests that validatePositive() with a non null input with zero throws ValidationException. */
    public void testValidatePositiveWith0ThrowsValidationExcpetion() {
        assertThrows(ValidationException.class, ()->{Validations.validatePositive(new Integer[]{0});});
    }

    /** The method tests that validatePositive() with non null input with positive integer does nothing. */
    public void testValidatePositiveWithPositiveInt() {
        Validations.validatePositive(new Integer[1]);
    }
}
