package org.jeactor;

import org.jeactor.core.Priority;
import org.jeactor.core.PriorityConsumer;

/**
 * Represents a no-operation(do nothing) consumer that has a priority.
 * 
 * @param <T> consumed data type
 */
public class NopPriorityConsumer<T> extends PriorityConsumer<T> {
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
