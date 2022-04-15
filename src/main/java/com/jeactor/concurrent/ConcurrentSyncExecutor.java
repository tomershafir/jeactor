package com.jeactor.concurrent;

import java.util.concurrent.Executor;

/**
 * A thread-safe executer that executes task synchronously in the caller thread.
 */
class ConcurrentSyncExecutor implements Executor { // no in memory state so thread safe 
    /**
     * Creates default synchronous executor.
     */
    ConcurrentSyncExecutor() {}

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
