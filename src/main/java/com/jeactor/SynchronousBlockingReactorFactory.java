package com.jeactor;

import com.jeactor.concurrent.SyncExecutor;
import com.jeactor.demultiplexor.PriorityBlockingDemultiplexor;

/**
 * Thread-safe factory that produce a singleton synchronous reactor.
 */
public class SynchronousBlockingReactorFactory implements ReactorFactory {
    private static ConcurrentReactor reactor = null;

    /**
     * Creates default factory instance.
     */
    public SynchronousBlockingReactorFactory() {}

    /**
     * The method returns a singleton synchronous reactor.
     * 
     * @return a singleton synchronous reactor
     */
    public synchronized Reactor get() {
        if (null == reactor)
            reactor = new ConcurrentReactor(new SyncExecutor(), new PriorityBlockingDemultiplexor());
        return reactor;
    }
}
