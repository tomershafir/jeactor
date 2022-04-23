package com.jeactor;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Abstract unit test class.
 * 
 * <p> All unit test classes should extend this class.
 */
@ExtendWith(MockitoExtension.class)
public abstract class UnitTest {
    /**
     * Creates default unit test instance.
     */
    protected UnitTest() {}

    /**
     * Hook method that is executed after each test case execution in the unit test.
     * 
     * <p> The logic is executed first before any default behaviour.
     */
    protected void afterEach() {}

    /**
     * Hook method that is executed before each test case execution in the unit test.
     * 
     * <p> The logic is executed first before any default behaviour.
     */
    protected void beforeEach() {}

     /**
     * Hook method that is executed after all test cases' execution in the unit test.
     * 
     * <p> The logic is executed first before any default behaviour.
     */
    protected void afterAll() {}

    /**
     * Hook method that is executed before all test cases' execution in the unit test.
     * 
     * <p> The logic is executed first before any default behaviour.
     */
    protected void beforeAll() {}

    /**
     * The method returns an array that contains all of the Mockito mocks that are used in the unit test.
     * 
     * @return an object array that contains all of the Mockito mocks that are used in the unit test
     */
    protected Object[] getMocks() {
        return null;
    }

    /**
     * The method that is executed after each test case execution in the unit test.
     */
    @AfterEach
    private final void execAfterEach() {
        // must be called first (checkout afterEach() API documentation)
        afterEach();

        final Object[] mocks = getMocks();
        if (null != mocks && mocks.length > 0)
            verifyNoMoreInteractions(mocks);
    }

    /**
     * The method that is executed before each test case execution in the unit test.
     */
    @BeforeEach
    private final void execBeforeEach() {
        // must be called first (checkout beforeEach() API documentation)
        beforeEach();

        final Object[] mocks = getMocks();
        if (null != mocks && mocks.length > 0)
            reset(mocks);
    }

     /**
     * The method that is executed after all test cases' execution in the unit test.
     */
    @AfterAll
    private final void execAfterAll() {
        // must be called first (checkout afterAll() API documentation)
        afterAll();
    }

    /**
     * The method that is executed before all test cases' execution in the unit test.
     */
    @BeforeAll
    private final void execBeforeAll() {
        // must be called first (checkout beforeAll() API documentation)
        beforeAll();
    }
}
