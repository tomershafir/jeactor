package org.jeactor.concurrent;

import java.util.concurrent.Executor;

/** Executer that executes task synchronously in the caller thread. */
@ThreadSafe
public class ConcurrentSyncExecutor implements Executor { // no in memory state so thread safe 
    /** Creates default synchronous executor. */
    public ConcurrentSyncExecutor() {}

    /**
     * Executes synchronously the sumbitted task.
     * 
     * @param task a runnable task to execute 
     */
    @Override
    public void execute(final Runnable task) {
        task.run();
    }
}
