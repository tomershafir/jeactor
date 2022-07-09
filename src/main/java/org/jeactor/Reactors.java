package org.jeactor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.jeactor.concurrent.SynchronousExecutor;
import org.jeactor.concurrent.ThreadSafe;
import org.jeactor.validation.Validations;
import jakarta.validation.ValidationException;

/**
 * Reactor factory utility. 
 * 
 * <p> Includes factory methods and more.
 * 
 * <p> Note: in a multithreaded environment a client should make sure that the injected dependencies are thread safe.
 */
@ThreadSafe
public final class Reactors { 
    private Reactors() {}

    /**
     * Returns a new synchronous thread-safe reactor.
     * 
     * @return a new synchronous thread-safe reactor
     */
    public static Reactor newSyncConcurrentReactor() {
        return new ReactorImpl(new SynchronousExecutor());
    }

    /**
     * Returns a new cached thread pool thread-safe reactor.
     * 
     * @return a new cached thread pool thread-safe reactor
     */
    public static Reactor newCachedThreadPoolConcurrentReactor() {
        return new ReactorImpl(Executors.newCachedThreadPool());
    }

    /**
     * Returns a new cached thread pool thread-safe reactor.
     * 
     * @param threadFactory a thread factory insance to be used for thread creation
     * @return a new cached thread pool thread-safe reactor
     * @throws ValidationException when null argument is supplied
     */
    public static Reactor newCachedThreadPoolConcurrentReactor(final ThreadFactory threadFactory) throws ValidationException {
        Validations.validateNotNull(threadFactory);
        return new ReactorImpl(Executors.newCachedThreadPool(threadFactory));
    }

    /**
     * Returns a new fixed thread pool thread-safe reactor.
     * 
     * @param noThreads an integer pool size 
     * @return a new fixed thread pool thread-safe reactor
     * @throws ValidationException when supplied noThreads is negative or 0
     */
    public static Reactor newFixedThreadPoolConcurrentReactor(final int noThreads) throws ValidationException {
        Validations.validatePositive(noThreads);
        return new ReactorImpl(Executors.newFixedThreadPool(noThreads));
    }

    /**
     * Returns a new fixed thread pool thread-safe reactor.
     * 
     * @param noThreads an integer pool size 
     * @param threadFactory a thread factory insance to be used for thread creation
     * @return a new fixed thread pool thread-safe reactor
     * @throws ValidationException when null factory is supplied or supplied noThreads is negative or 0
     */
    public static Reactor newFixedThreadPoolConcurrentReactor(final int noThreads, final ThreadFactory threadFactory) throws ValidationException {
        Validations.validateNotNull(threadFactory);
        Validations.validatePositive(noThreads);
        return new ReactorImpl(Executors.newFixedThreadPool(noThreads, threadFactory));
    }

    /**
     * Returns a new single worker thread-safe reactor.
     * 
     * @return a new single worker thread-safe reactor
     */
    public static Reactor newSingleWorkerConcurrentReactor() {
        return new ReactorImpl(Executors.newSingleThreadExecutor());
    }

    /**
     * Returns a new single worker thread-safe reactor.
     * 
     * @param threadFactory a thread factory insance to be used for thread creation
     * @return a new single worker thread-safe reactor
     * @throws ValidationException when null argument is supplied
     */
    public static Reactor newSingleWorkerConcurrentReactor(final ThreadFactory threadFactory) throws ValidationException {
        Validations.validateNotNull(threadFactory);
        return new ReactorImpl(Executors.newSingleThreadExecutor(threadFactory));
    }

    /**
     * Returns a new thread-safe reactor with the accepted executor.
     * 
     * @param executor an executor to be used by the reactor
     * @return a new synchronous thread-safe reactor
     * @throws ValidationException when a null argument is accepted
     */
    public static Reactor newConcurrentReactor(final Executor executor) throws ValidationException {
        Validations.validateNotNull(executor);
        return new ReactorImpl(executor);
    }
}
