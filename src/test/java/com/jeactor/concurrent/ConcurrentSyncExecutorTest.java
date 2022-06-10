package com.jeactor.concurrent;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.jeactor.AbstractJeactorUnitTest;
import org.junit.jupiter.api.Test;

/** Unit test of ConcurrentSyncExecutor. */
public class ConcurrentSyncExecutorTest extends AbstractJeactorUnitTest {
    /** Creates default unit test instance. */
    public ConcurrentSyncExecutorTest() {}

    /** The method tests that execute() operates in the calling thread. */
    @Test
    public void testExecuteOperatesInCallingThread() {
        final Lock lock = new ReentrantLock();
        lock.lock(); // no need to unlock() because each thread creates a new lock instance
        
        final List<String> actual = new ArrayList<>();
        new ConcurrentSyncExecutor().execute(()->{
            lock.lock();
            actual.add("$");
        });

        final List<String> expected = List.of("$");
        assertIterableEquals(expected, actual);
    }
}
