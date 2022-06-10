package com.jeactor;

import java.util.concurrent.Executor;

/**
 * Abstract jeactor unit test class.
 * 
 * <p> All jeactor's unit test classes should extend this class.
 */
public abstract class AbstractJeactorUnitTest extends AbstractUnitTest {
    /** Creates default jeactor unit test instance. */
    protected AbstractJeactorUnitTest() {}

    /**
     * Represents a no-operation(do nothing) consumer that has a priority.
     * 
     * @param <T> consumed data type
     */
    protected static class NopPriorityConsumer<T> extends PriorityConsumer<T> {
        /** Creates a dummy consumer with NORMAL priority. */
        public NopPriorityConsumer() {
            super();
        }

        /**
         * Creates a dummy consumer with the accepted priority, if it is null than the default is NORMAL.
         * 
         * @param consumerPriority a priority of the consumer
         */
        public NopPriorityConsumer(final Priority consumerPriority) {
            super(consumerPriority);
        }

        /**
         * The method consume data and do nothing.
         *
         * @param data of type T
         */
        @Override
        public void accept(final T data) {}
    }

    /** Represents a no-operation(do nothing) dummy executor. */
    protected static class NopExecutor implements Executor {
        /** Creates a no-operation(do nothing) dummy executor. */
        public NopExecutor() {}
        
        /** Dummy run(). */
        @Override
        public void execute(final Runnable r) {}
    }
}
