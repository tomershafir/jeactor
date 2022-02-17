package com.jeactor.concurrent;

import com.jeactor.concurrent.util.demultiplexor.ConcurrentPriorityBlockingDemultiplexor;
import com.jeactor.concurrent.util.registry.ConcurrentEventRegistry;

/**
 * Thread-safe factory that produce a singleton synchronous reactor.
 */
public class ConcurrentSyncReactorFactory implements ConcurrentReactorFactory { // top service layer
    private static ConcurrentReactor reactor = null;

    /**
     * Creates default factory instance.
     */
    public ConcurrentSyncReactorFactory() {}

    /**
     * The method returns a singleton synchronous reactor.
     * 
     * @return a singleton synchronous reactor
     */
    public synchronized ConcurrentProxyReactor get() {
        if (null == reactor)
            reactor = new ConcurrentReactor(new ConcurrentSyncExecutor(), new ConcurrentPriorityBlockingDemultiplexor(), new ConcurrentEventRegistry()); // passed registry service must not be exposed or we have thread synchronization problems
        return reactor;
    }
}
