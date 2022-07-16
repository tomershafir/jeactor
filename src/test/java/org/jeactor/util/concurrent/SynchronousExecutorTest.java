package org.jeactor.util.concurrent;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jeactor.AbstractJeactorUnitTest;
import org.junit.jupiter.api.Test;

/** Unit test of SynchronousExecutor. */
public class SynchronousExecutorTest extends AbstractJeactorUnitTest {
    /** Tests that execute() operates in the calling thread. */
    @Test
    public void testExecuteOperatesInCallingThread() {
        final Lock lock = new ReentrantLock();
        lock.lock(); // no need to unlock() because each thread creates a new lock instance
        
        final List<String> actual = new ArrayList<>();
        new SynchronousExecutor().execute(()->{
            lock.lock();
            actual.add("$");
        });

        final List<String> expected = List.of("$");
        assertIterableEquals(expected, actual);
    }
}
