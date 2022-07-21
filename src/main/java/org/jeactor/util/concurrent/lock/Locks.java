package org.jeactor.util.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public class Locks {
    private Locks() {}

    /**
     * Executes the accepted runnable using the accepted lock.
     * 
     * @param lock a lock to guard execution with
     * @param runnable a runnable to execute
     */
    public static void exec(final Lock lock, final Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Executes the accepted supplier using the accepted lock and returns the supplied object.
     * 
     * @param <T> type to supply
     * @param lock a lock to guard execution with
     * @param supplier a supplier to execute
     * @return the supplied object
     */
    public static <T> T exec(final Lock lock, final Supplier<T> supplier) {
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}
