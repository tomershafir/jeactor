package com.jeactor.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.jeactor.AbstractJeactorUnitTest;
import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;

/** Unit test of Reactors class. */
public class ReactorsTest extends AbstractJeactorUnitTest {
    /** Creates default unit test instance. */
    public ReactorsTest() {}

    /** The methods tests that new synchronous concurrent reactor is created correctly. */
    @Test
    public void testNewSyncConcurrentReactor() {
        assertEquals(ConcurrentSyncExecutor.class, Reactors.newSyncConcurrentReactor().getExecutorClass());
    }

    /** The methods tests that new cached thread pool concurrent reactor is created correctly. */
    @Test
    public void testNewCachedThreadPoolConcurrentReactor() {
        assertEquals(ThreadPoolExecutor.class, Reactors.newCachedThreadPoolConcurrentReactor().getExecutorClass());
    }

    /** The methods tests that newCachedThreadPoolConcurrentReactor() throws validation exception when null thread factory is passed. */
    @Test
    public void testNewCachedThreadPoolConcurrentReactorWithNullThreadFactoryThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newCachedThreadPoolConcurrentReactor(null);});
    }

    /** The methods tests that new cached thread pool concurrent reactor is created correctly. */
    @Test
    public void testNewCachedThreadPoolConcurrentReactorWithThreadFactory() {
        assertEquals(ThreadPoolExecutor.class, Reactors.newCachedThreadPoolConcurrentReactor(Executors.defaultThreadFactory()).getExecutorClass());
    }

    /** The methods tests that new single worker concurrent reactor is created correctly. */
    @Test
    public void testNewSingleWorkerConcurrentReactor() {
        assertTrue(ExecutorService.class.isAssignableFrom(Reactors.newSingleWorkerConcurrentReactor().getExecutorClass()));
    }

    /** The methods tests that newSingleWorkerConcurrentReactor() throws validation exception when null thread factory is passed. */
    @Test
    public void testNewSingleWorkerConcurrentReactorWithNullThreadFactoryThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newSingleWorkerConcurrentReactor(null);});
    }

    /** The methods tests that new single worker concurrent reactor is created correctly. */
    @Test
    public void testNewSingleWorkerConcurrentReactorWithThreadFactory() {
        assertTrue(ExecutorService.class.isAssignableFrom(Reactors.newSingleWorkerConcurrentReactor(Executors.defaultThreadFactory()).getExecutorClass()));
    }

    /** The methods tests that newConcurrentReactor() throws validation exception when null executor is passed. */
    @Test
    public void testNewConcurrentReactorWithNullExecutorThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newConcurrentReactor(null);});
    }

    /** The methods tests that new concurrent reactor is created correctly. */
    @Test
    public void testNewConcurrentReactor() {
        final Executor executor = new NopExecutor();
        assertEquals(NopExecutor.class, Reactors.newConcurrentReactor(executor).getExecutorClass());
    }

    /** The methods tests that newFixedThreadPoolConcurrentReactor() throws validation exception with 0 passed size. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactorWith0SizeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newFixedThreadPoolConcurrentReactor(0);});
    }

    /** The methods tests that newFixedThreadPoolConcurrentReactor() throws validation exception with negative passed size. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactorWithNegativeSizeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newFixedThreadPoolConcurrentReactor(-1);});
    }

    /** The methods tests that new fixed thread pool concurrent reactor is created correctly. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactor() {
        final int noThreads = 10;
        assertEquals(ThreadPoolExecutor.class, Reactors.newFixedThreadPoolConcurrentReactor(noThreads).getExecutorClass());
    }

    /** The methods tests that newFixedThreadPoolConcurrentReactor() with null thread factory throws validation exception. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactorWithNullThreadFactoryThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newFixedThreadPoolConcurrentReactor(10, null);});
    }

    /** The methods tests that newFixedThreadPoolConcurrentReactor() with thread factory throws validation exception with 0 passed size. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactorWithThreadFactoryWith0SizeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newFixedThreadPoolConcurrentReactor(0, Executors.defaultThreadFactory());});
    }

    /** The methods tests that newFixedThreadPoolConcurrentReactor() with thread factory throws validation exception with negative passed size. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactorWithThreadFactoryWithNegativeSizeThrowsValidationException() {
        assertThrows(ValidationException.class, ()->{Reactors.newFixedThreadPoolConcurrentReactor(-1, Executors.defaultThreadFactory());});
    }

    /** The methods tests that new fixed thread pool concurrent reactor is created correctly. */
    @Test
    public void testNewFixedThreadPoolConcurrentReactorWithThreadFactory() {
        final int noThreads = 10;
        assertEquals(ThreadPoolExecutor.class, Reactors.newFixedThreadPoolConcurrentReactor(noThreads, Executors.defaultThreadFactory()).getExecutorClass());
    }
}
