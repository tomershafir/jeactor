package com.jeactor;

import java.util.concurrent.Executor;

/** Represents a reactor that expose some internal data for visibility, with compromising encapsulation and concurrency. */
public interface ObservableReactor {
    /**
     * The method returns the class of the executor used by the reactor.
     * 
     * @return a class of the executor used by the reactor
     */
    Class<? extends Executor> getExecutorClass();
}
