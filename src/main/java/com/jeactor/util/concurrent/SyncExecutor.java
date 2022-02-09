package com.jeactor.util.concurrent;

/**
 * An executer that executes task synchronously in the caller thread.
 */
public class SyncExecutor implements ConcurrentExecutor { // no internal memory used for state so thread safe // intemediate service layer
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
