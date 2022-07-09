package org.jeactor;

import java.util.concurrent.Executor;

/** Represents a reactor that expose visibility data. */
public interface ObservableReactor {
    /**
     * Returns the class of the executor used by the reactor.
     * 
     * @return a class of the executor used by the reactor
     */
    Class<? extends Executor> getExecutorClass();
}
