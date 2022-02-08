package com.jeactor.concurrent;

import java.util.concurrent.Executor;

/**
 * Stateless executer that executes task synchronously in the caller thread.
 */
public final class SyncExecutor implements Executor { // no internal memory used for state so thread safe
    /**
     * Creates default synchronous executor.
     */
    public SyncExecutor() {}

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
