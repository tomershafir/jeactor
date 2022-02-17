package com.jeactor.concurrent;

import java.util.concurrent.Executor;

/**
 * A thread-safe executer that executes task synchronously in the caller thread.
 */
public class ConcurrentSyncExecutor implements Executor { // no internal memory used for state so thread safe // intemediate service layer
    /**
     * Creates default synchronous executor.
     */
    public ConcurrentSyncExecutor() {}

    /**
     * The method executes synchronously the sumbitted task.
     * 
     * @param task a runnable task to execute 
     */
    @Override
    public void execute(final Runnable task) {
        task.run();
    }
}
