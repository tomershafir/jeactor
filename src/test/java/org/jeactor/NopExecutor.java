package org.jeactor;

import java.util.concurrent.Executor;

/** Represents a no-operation(do nothing) executor. */
public class NopExecutor implements Executor {
    /** Nop run(). */
    @Override
    public void execute(final Runnable r) {}
}