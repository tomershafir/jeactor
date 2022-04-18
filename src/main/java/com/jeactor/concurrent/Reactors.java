package com.jeactor.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import com.jeactor.concurrent.demux.ConcurrentPriorityBlockingDemux;
import com.jeactor.registry.PriorityEventRegistry;

/**
 * Utility class for reactors which is thread-safe. 
 * 
 * <p> Includes factory methods and more.
 * 
 * <p> Note: in a multithreaded environment a client should make sure that the injected dependencies are thread safe.
 */
@ThreadSafe
public final class Reactors { 
    private Reactors() {}

    /**
     * A factory method that returns a new synchronous thread-safe reactor.
     * 
     * @return a new synchronous thread-safe reactor
     */
    public static AbstractConcurrentProxyReactor newSyncConcurrentReactor() {
        return new ConcurrentReactor(new ConcurrentSyncExecutor(), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new cached thread pool thread-safe reactor.
     * 
     * @return a new cached thread pool thread-safe reactor
     */
    public static AbstractConcurrentProxyReactor newCachedThreadPoolConcurrentReactor() {
        return new ConcurrentReactor(Executors.newCachedThreadPool(), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new cached thread pool thread-safe reactor.
     * 
     * @param threadFactory a thread factory insance to be used for thread creation
     * @return a new cached thread pool thread-safe reactor
     * @throws NullPointerException when null argument is supplied
     */
    public static AbstractConcurrentProxyReactor newCachedThreadPoolConcurrentReactor(ThreadFactory threadFactory) {
        return new ConcurrentReactor(Executors.newCachedThreadPool(threadFactory), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new fixed thread pool thread-safe reactor.
     * 
     * @param nThreads an integer pool size 
     * @return a new fixed thread pool thread-safe reactor
     */
    public static AbstractConcurrentProxyReactor newFixedThreadPoolConcurrentReactor(int nThreads) {
        return new ConcurrentReactor(Executors.newFixedThreadPool(nThreads), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new fixed thread pool thread-safe reactor.
     * 
     * @param nThreads an integer pool size 
     * @param threadFactory a thread factory insance to be used for thread creation
     * @return a new fixed thread pool thread-safe reactor
     * @throws NullPointerException when null argument is supplied
     */
    public static AbstractConcurrentProxyReactor newFixedThreadPoolConcurrentReactor(int nThreads, ThreadFactory threadFactory) {
        return new ConcurrentReactor(Executors.newFixedThreadPool(nThreads, threadFactory), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new single worker thread-safe reactor.
     * 
     * @return a new single worker thread-safe reactor
     */
    public static AbstractConcurrentProxyReactor newSingleWorkerConcurrentReactor() {
        return new ConcurrentReactor(Executors.newSingleThreadExecutor(), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new single worker thread-safe reactor.
     * 
     * @param threadFactory a thread factory insance to be used for thread creation
     * @return a new single worker thread-safe reactor
     * @throws NullPointerException when null argument is supplied
     */
    public static AbstractConcurrentProxyReactor newSingleWorkerConcurrentReactor(ThreadFactory threadFactory) {
        return new ConcurrentReactor(Executors.newSingleThreadExecutor(threadFactory), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }

    /**
     * A factory method that returns a new thread-safe reactor with the accepted executor.
     * 
     * @param executor an executor to be used by the reactor
     * @return a new synchronous thread-safe reactor
     * @throws NullPointerException when a null argument is accepted
     */
    public static AbstractConcurrentProxyReactor newConcurrentReactor(Executor executor) {
        return new ConcurrentReactor(new ConcurrentSyncExecutor(), new ConcurrentPriorityBlockingDemux(), new PriorityEventRegistry());
    }
}
