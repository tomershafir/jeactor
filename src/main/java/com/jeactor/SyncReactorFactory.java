package com.jeactor;

import com.jeactor.util.concurrent.SyncExecutor;
import com.jeactor.util.demultiplexor.PriorityBlockingDemultiplexor;
import com.jeactor.util.registry.EventRegistry;

/**
 * Thread-safe factory that produce a singleton synchronous reactor.
 */
public class SyncReactorFactory implements ReactorFactory { // top service layer
    private static ConcurrentReactor reactor = null;

    /**
     * Creates default factory instance.
     */
    public SyncReactorFactory() {}

    /**
     * The method returns a singleton synchronous reactor.
     * 
     * @return a singleton synchronous reactor
     */
    public synchronized ProxyReactor get() {
        if (null == reactor)
            reactor = new ConcurrentReactor(new SyncExecutor(), new PriorityBlockingDemultiplexor(), new EventRegistry()); // passed registry service must not be exposed or we have thread synchronization problems
        return reactor;
    }
}
