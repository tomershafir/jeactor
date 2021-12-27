package com.tomer.reactor;

import java.util.concurrent.Executor;

/**
 * An executer that executes task synchronously in the caller thread.
 */
final class SynchronousExecutor implements Executor {
    /**
     * No-args empty constructor.
     */
    SynchronousExecutor(){}

    /**
     * The method executes synchronously the sumbitted task.
     * 
     * @param task a runnable task to execute 
     */
    @Override
    public void execute(final Runnable task){
        task.run();
    }
}
