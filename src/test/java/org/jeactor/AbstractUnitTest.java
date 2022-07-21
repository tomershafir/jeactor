package org.jeactor;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.mockito.Mockito;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.listeners.MockCreationListener;
import org.mockito.listeners.MockitoListener;
import org.mockito.mock.MockCreationSettings;

/**
 * Abstract unit test class.
 * 
 * <p> All unit test classes should extend this class.
 */
@ExtendWith(MockitoExtension.class)
public abstract class AbstractUnitTest {
    private AutoCloseable closeableMockitoAnnotationsResource;
    private Set<Object> mocks;
    private MockitoListener mockCreationListener;
    
    /** Creates default unit test instance. */
    protected AbstractUnitTest() {}

    /**
     * Executed after each test case execution in the unit test. May be overriden by concrete unit tests.
     * 
     * <p> The logic is executed first before any default behaviour.
     */
    protected void afterEach() {}

    /**
     * Executed before each test case execution in the unit test. May be overriden by concrete unit tests.
     * 
     * <p> The logic is executed first before any default behaviour.
     */
    protected void beforeEach() {}

    /** Executed before each test case execution in the unit test. */
    @BeforeEach
    public final void execBeforeEach() {
        // must be called first (checkout beforeEach() API documentation)
        beforeEach();

        closeableMockitoAnnotationsResource = MockitoAnnotations.openMocks(this);
        
        mocks = new HashSet<>();
        mockCreationListener = new MockCreationListener() {
            @Override
            public void onMockCreated(final Object mock, final MockCreationSettings settings) {
                mocks.add(mock);
            }
        };
        Mockito.framework().addListener(mockCreationListener);
    }

    /** Executed after each test case execution in the unit test. */
    @AfterEach
    public final void execAfterEach() throws Exception {
        // must be called first (checkout afterEach() API documentation)
        afterEach();

        if(null != closeableMockitoAnnotationsResource)
            closeableMockitoAnnotationsResource.close();
        
        Mockito.framework().removeListener(mockCreationListener);

        if (mocks.size() > 0)
            verifyNoMoreInteractions(mocks);
    }

    /**
     * Executes tests on autoclosable objects and safely releases used resources.
     * 
     * @param <T> an autoclosable type
     * @param factory a supplier that returns an instance to be injected to the test
     * @param test a test to execute with the factory produced instance
     * @throws Exception if en error occurs during resources closure
     */
    protected <T extends AutoCloseable> void testWithResources(final Supplier<T> factory, final Consumer<T> test) throws Exception {
        T t = null;
        try {
            t = factory.get();
            test.accept(t);
        } finally {
            if (null != t)
                t.close();
        }
    }
}
