package org.jeactor.util.concurrent;

import java.util.concurrent.Executor;

/** Executor that executes task synchronously in the caller thread. */
@ThreadSafe // no in-memory state so thread safe
public class SynchronousExecutor implements Executor { 
    /** Creates default synchronous executor. */
    public SynchronousExecutor() {}

    /**
     * Executes the sumbitted task synchronously.
     * 
     * @param task a runnable task to execute 
     */
    @Override
    public void execute(final Runnable task) {
        task.run();
    }
}
