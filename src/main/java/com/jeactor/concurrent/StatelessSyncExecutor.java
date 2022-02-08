package com.jeactor.concurrent;

import java.util.concurrent.Executor;

/**
 * Stateless executer that executes task synchronously in the caller thread.
 */
public final class StatelessSyncExecutor implements Executor {
    /**
     * Creates default synchronous executor.
     */
    public StatelessSyncExecutor() {}

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
