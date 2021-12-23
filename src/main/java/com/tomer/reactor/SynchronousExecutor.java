package com.tomer.reactor;

import java.util.concurrent.Executor;

/**
 * An executer that executes task synchronously in the caller thread.
 */
public final class SynchronousExecutor implements Executor {
    /**
     * No-args empty constructor.
     */
    public SynchronousExecutor(){}

    /**
     * The method executes synchronously the sumbitted task.
     * 
     * @param task a runnable task to execute 
     */
    @Override
    public void execute(Runnable task){
        task.run();
    }
}
